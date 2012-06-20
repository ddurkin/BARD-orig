package bardqueryapi

import grails.plugin.spock.IntegrationSpec

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */

class QueryAssayApiServiceIntegrationSpec extends IntegrationSpec {

    QueryAssayApiService queryAssayApiService

    void setup() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testFindAssayByAid() {
        given:
        final String aidUrl = "/bard/rest/v1/assays/1772"
        when:
        final def assay = queryAssayApiService.findAssayByAid(aidUrl)
        then:
        assert assay
        println assay
    }

    void testFindCompoundsByAssay() {
        given:
        final String aidUrl = "/bard/rest/v1/assays/1772"
        when:
        final def compounds = queryAssayApiService.findCompoundsByAssay(aidUrl, null)
        then:
        assert compounds
        compounds.each {compound ->
            println compound
        }
    }

    void testFindProteinTargetsByAssay() {
        given:
        final String aidUrl = "/bard/rest/v1/assays/1772"
        when:
        final def proteinTargetsByAssay = queryAssayApiService.findProteinTargetsByAssay(aidUrl)
        then:
        assert proteinTargetsByAssay
        proteinTargetsByAssay.each {target ->
            println target
        }
    }

    void testFindPublicationsByAssay() {
        given:
        final String publicationUrl = "/bard/rest/v1/assays/1772"
        when:
        final def publicationsByAssay = queryAssayApiService.findPublicationsByAssay(publicationUrl)
        then:
        assert publicationsByAssay
        publicationsByAssay.each {publicationByAssay ->
            println publicationByAssay
        }
    }
//    void testFindAssayByAid() {
//        given:
//        String aid = 493159
//        when:
//        final Response assays = queryAssayApiService.findAssayByAid(aid)
//        final json = assays.json
//        then:
//        assert json
//        println "Aid: ${json.aid}"
//        println "Category : ${json.category}"
//        println "Type: ${json.type}"
//        println "Summary: ${json.summary}"
//        println "Assays: ${json.assays}"
//        println "Classification: ${json.classification}"
//        println "Samples: ${json.samples}"
//        println "Name: ${json.name}"
//        println "Description: ${json.description}"
//        println "Source: ${json.source}"
//        println "Grant #: ${json.grantNo}"
//        println "Deposited: ${json.deposited}"
//        println "Updated: ${json.updated}"
//        println "Targets: ${json.targets}"
//        println "Data : ${json.data}"
//        println "Publications :"
//        json.publications.each{ publication ->
//            println "\t ${publication.resourcePath}"
//        }
//        assays.json.each{ assay ->
//            println assay
//        }
//
//
//    }
}