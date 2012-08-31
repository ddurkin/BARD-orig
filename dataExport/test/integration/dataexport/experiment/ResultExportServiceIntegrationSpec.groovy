package dataexport.experiment

import bard.db.enums.ReadyForExtraction
import bard.db.experiment.Result
import dataexport.registration.BardHttpResponse
import exceptions.NotFoundException
import grails.plugin.spock.IntegrationSpec
import groovy.xml.MarkupBuilder
import spock.lang.Unroll

import javax.servlet.http.HttpServletResponse
import org.custommonkey.xmlunit.XMLAssert

@Unroll
class ResultExportServiceIntegrationSpec extends IntegrationSpec {
    ResultExportService resultExportService
    Writer writer
    MarkupBuilder markupBuilder

    void setup() {
        this.writer = new StringWriter()
        this.markupBuilder = new MarkupBuilder(this.writer)

    }


    void tearDown() {
        // Tear down logic here
    }

    void "test update Not Found Status"() {
        given: "Given a non-existing Result"
        when: "We call the result service to update this result"
        this.resultExportService.update(new Long(100000), 0, "Complete")

        then: "An exception is thrown, indicating that the result does not exist"
        thrown(NotFoundException)
    }

    void "test update Result, Exception READY_FOR_EXTRACTION value #status is unknown"() {
        given: "Given a Result with id #id and version #version"
        when: "We call the result service to update this result"
        this.resultExportService.update(resultId, version, status)

        then: "An exception is thrown, indicating that the status value of #status is unknown"
        thrown(Exception)
        where:
        resultId      | version | status
        new Long(532) | 0       | "InComplete"
    }

    void "test Generate Result"() {
        given: "Given a Result with id #id and version #version"
        when: "We call the result service to generate this result"
        this.resultExportService.generateResult(this.markupBuilder, resultId)

        then: "A result is generated"
        XMLAssert.assertXpathEvaluatesTo("1", "count(//result)", this.writer.toString());
        XMLAssert.assertXpathEvaluatesTo("1", "count(//resultContextItems)", this.writer.toString());
        XMLAssert.assertXpathEvaluatesTo("1", "count(//resultContextItem)", this.writer.toString());
        where:
        resultId      | version | status
        new Long(533) | 0       | "InComplete"
    }

    void "test update #label"() {
        given: "Given a Result with id #id and version #version"
        when: "We call the result service to update this result"
        final BardHttpResponse bardHttpResponse = this.resultExportService.update(resultId, version, status)

        then: "An ETag of #expectedETag is returned together with an HTTP Status of #expectedStatusCode"
        assert bardHttpResponse
        assert bardHttpResponse.ETag == expectedETag
        assert bardHttpResponse.httpResponseCode == expectedStatusCode
        assert Result.get(resultId).readyForExtraction == expectedStatus
        where:
        label                                            | expectedStatusCode                         | expectedETag | resultId      | version | status     | expectedStatus
        "Return OK and ETag 1"                           | HttpServletResponse.SC_OK                  | new Long(1)  | new Long(533) | 0       | "Complete" | ReadyForExtraction.Complete
        "Return CONFLICT and ETag 0"                     | HttpServletResponse.SC_CONFLICT            | new Long(0)  | new Long(533) | -1      | "Complete" | ReadyForExtraction.Ready
        "Return PRECONDITION_FAILED and ETag 0"          | HttpServletResponse.SC_PRECONDITION_FAILED | new Long(0)  | new Long(533) | 2       | "Complete" | ReadyForExtraction.Ready
        "Return OK and ETag 0, Already completed Result" | HttpServletResponse.SC_OK                  | new Long(0)  | new Long(532) | 0       | "Complete" | ReadyForExtraction.Complete

    }


}