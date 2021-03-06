/* Copyright (c) 2014, The Broad Institute
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of The Broad Institute nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL The Broad Institute BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package bard.validation.ext;

import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.scripps.fl.entrez.internal.HttpClientBaseSingleton;

public class ExternalOntologyATCC extends AbstractExternalOntology {

    public static class ATCCCreator implements ExternalOntologyCreator {
        @Override
        public ExternalOntologyAPI create(URI uri, Properties props) throws ExternalOntologyException {
            String host = uri.getHost();
            if (host.endsWith(".atcc.org"))
                return new ExternalOntologyATCC();
            return null;
        }
    }

    /**
     * Designed to be run against http://www.atcc.org/Search_Results.aspx. Look
     * for anchors where the href is for the single product page. The contents
     * of the anchor should be the display value. Avoids duplicates where the
     * contents of the tag equal the Id.
     */
    class ATCCMultiProductHandler extends DefaultHandler {
        StringBuilder displayTag;
        Set<String> ids = new HashSet<String>();
        ExternalItem item;
        List<ExternalItem> items = new ArrayList<ExternalItem>();
        Pattern pattern = Pattern.compile("/Products/All/(.*)\\.aspx$");

        public void characters(char[] ch, int start, int length) {
            if (displayTag != null)
                displayTag.append(new String(ch, start, length).trim());
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("a".equalsIgnoreCase(qName) && displayTag != null) {
                if (!ids.contains(item.getId())) {
                    ids.add(item.getId());
                    item.setDisplay(displayTag.toString().trim());
                    items.add(item);
                }
                displayTag = null;
            }
        }

        public List<ExternalItem> getItems() {
            return items;
        }

        public void startElement(String uri, String localName, String name, Attributes attributes) {
            if ("a".equalsIgnoreCase(name)) {
                String href = attributes.getValue("href");
                href = href == null ? "" : href;
                Matcher matcher = pattern.matcher(href);
                if (matcher.find()) {
                    String id = matcher.group(1);
                    item = new ExternalItemImpl(id, "");
                    displayTag = new StringBuilder();
                }
            }
        }
    }

    /**
     * Designed to be run against the http://www.atcc.org/Products/All/ page. If
     * the <title/> returned is "Page Not Found" then it isn't a valid ATTC
     * product, otherwise it is the product Id.
     * <p/>
     * The contents of the //h1[class="product_name"] element should then be the
     * description
     */
    class ATCCSingleProductHandler extends DefaultHandler {
        String display;
        String id;
        boolean notFound = false;
        String productTagName = "";
        StringBuilder productTag;
        StringBuilder titleTag;

        public void characters(char[] ch, int start, int length) {
            if (titleTag != null)
                titleTag.append(new String(ch, start, length).trim());
            if (productTag != null)
                productTag.append(new String(ch, start, length).trim());
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("title".equalsIgnoreCase(qName)) {
                if ("Page Not Found".equals(titleTag.toString())) {
                    notFound = true;
                    throw new SAXException(new BreakParsingException());
                } else {
                    id = titleTag.toString();
                    titleTag = null;
                }
            } else if (productTagName.equalsIgnoreCase(qName) && productTag != null) {
                display = productTag.toString();
                productTag = null;
                throw new SAXException(new BreakParsingException());
            }
        }

        public String getDisplay() {
            return display;
        }

        public String getId() {
            return id;
        }

        public boolean isNotFound() {
            return notFound;
        }

        public void startElement(String uri, String localName, String name, Attributes attr) {
            if ("title".equalsIgnoreCase(name))
                titleTag = new StringBuilder();
            // <h1 class="product_name">
            String cssClass = attr.getValue("class");
            if (cssClass != null && cssClass.contains("product_name")) {
                productTagName = name;
                productTag = new StringBuilder();
            }
        }
    }

    class BreakParsingException extends RuntimeException {
    }

    private static String ATCC_ITEM_URL_FORMAT = "http://www.atcc.org/Products/All/%s.aspx";

    private static boolean DEBUGGING = false;

    private static Pattern ID_PATTERN = Pattern.compile("^.*ATCC\\u00AE?\\s*([\\w\\d-]+)\\u2122?\\)?$");

    private static final Logger log = LoggerFactory.getLogger(ExternalOntologyATCC.class);

    private static String[] userAgent = new String[]{
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11",
            "Mozilla/5.0 (Windows; U; MSIE 7.0; Windows NT 6.0; en-US)",
            "Mozilla/4.0 (compatible; MSIE 6.1; Windows XP)",
            "Mozilla/5.0 (X11; U; Windows NT 5.1; en-US; rv:1.9.0.7) Gecko/2009021910 Firefox/3.0.7",
            "Mozilla/5.0(Windows; U; Windows NT 5.2; rv:1.9.2) Gecko/20100101 Firefox/3.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5",
            "Mozilla/5.0 (X11; U; Linux i686; pl-PL; rv:1.9.0.2) Gecko/20121223 Ubuntu/9.25 (jaunty) Firefox/3.8",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; ru; rv:1.9.2.3) Gecko/20100401 Firefox/4.0 (.NET CLR 3.5.30729)",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.9 (KHTML, like Gecko) Chrome/6.0.400.0 Safari/533.9"};

    private Random random = new Random();

    @Override
    public String cleanId(String id) {
        id = super.cleanId(id);
        Matcher matcher = ID_PATTERN.matcher(id);
        if (matcher.matches())
            id = matcher.group(1);
        return id;
    }

    @Override
    public String cleanName(String name) {
        return cleanId(name);
    }

    @Override
    public boolean matchesId(String potentialId) {
        return ID_PATTERN.matcher(potentialId).matches();
    }

    @Override
    public ExternalItem findById(String id) throws ExternalOntologyException {
        id = cleanId(id);
        try {
            URL url = new URL(String.format(ATCC_ITEM_URL_FORMAT, id));
            ATCCSingleProductHandler handler = new ATCCSingleProductHandler();
            handle(url, handler);
            if (handler.isNotFound())
                return null;
            else
                return new ExternalItemImpl(id, handler.getDisplay());
        } catch (Exception ex) {
            throw new ExternalOntologyException(ex);
        }
    }

    @Override
    public ExternalItem findByName(String name) throws ExternalOntologyException {
        return findById(name);
    }

    /**
     * Hard limit of 100 records - scraping a web page, obeying its contract.
     */
    @Override
    public List<ExternalItem> findMatching(String term, int limit) throws ExternalOntologyException {
        try {
            if (limit <= 0 || limit > 100)
                limit = 100;
            URL url = new URL(formatMatchingUrl(term, limit));
            ATCCMultiProductHandler handler = new ATCCMultiProductHandler();
            handle(url, handler);
            return handler.getItems();
        } catch (Exception ex) {
            throw new ExternalOntologyException(ex);
        }
    }

    private String formatMatchingUrl(String term, int limit) {
        StringBuilder sb = new StringBuilder("http://www.atcc.org/Search_Results.aspx?dsNav=Ntk:PrimarySearch%7ccells");
        sb.append("%7c3%7c,Ny:True,Rpp:");
        sb.append(limit);
        sb.append(",N:1000552&searchTerms=");
        sb.append(queryGenerator(term));
        sb.append("&redir=1");
        log.info(sb.toString());
        return sb.toString();
    }

    @Override
    public String getExternalURL(String id) {
        return String.format(ATCC_ITEM_URL_FORMAT, cleanId(id));
    }

    protected DefaultHandler handle(URL url, DefaultHandler handler) throws IOException, SAXException, ParserConfigurationException {
        BufferedReader ir = null;
        try {
            log.debug(String.format("GET %s", url));

            HttpGet get = new HttpGet(url.toString()); // get allows redirects by default, post does not.
            // USER-AGENT seems to make it run faster
            get.setHeader("USER-AGENT", userAgent[random.nextInt(userAgent.length - 1)]);

            HttpParams params = new BasicHttpParams();
            // timeout for waiting for data in ms.
            HttpConnectionParams.setSoTimeout(params, 10000);
            get.setParams(params);

            HttpResponse response = HttpClientBaseSingleton.getInstance().getHttpClient().execute(get);
            ir = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            if (DEBUGGING) {
                File file = File.createTempFile(getClass().getName(), ".html");
                log.debug("Copying stream to " + file);
                FileOutputStream fos = new FileOutputStream(file);
                IOUtils.copy(ir, fos, "UTF-8");
                fos.close();
                ir.close();
                Desktop.getDesktop().open(file);
                ir = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            }
            final InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(ir);
            SAXParserImpl.newInstance(null).parse(inputSource, handler);
        } catch (SAXException ex) {
            if (!(ex.getCause() instanceof BreakParsingException))
                throw ex;
        } finally {
            IOUtils.closeQuietly(ir);
        }
        return handler;
    }
}
