package bard.core.rest

import bard.core.Substance
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class RESTSubstanceServiceUnitSpec extends Specification {
    RESTSubstanceService restSubstanceService
    RESTEntityServiceManager entityServiceManager


    void setup() {
        this.entityServiceManager = Mock(RESTEntityServiceManager)

        this.restSubstanceService = new RESTSubstanceService(this.entityServiceManager, "base")
    }

    void tearDown() {
        // Tear down logic here
    }

    void "getEntityClass()"() {
        when:
        final Class clazz = this.restSubstanceService.getEntityClass()
        then:
        assert clazz.getName() == "bard.core.Substance"

    }

    void "getResourceContext"() {
        when:
        String context = this.restSubstanceService.getResourceContext()
        then:
        context == "/substances"
    }

    void "getEntity #label"() {
        when:
        Substance currentSubstance = this.restSubstanceService.getEntity(substance, null)
        then:
        currentSubstance.getName() == expectedName
        where:
        label                                  | substance             | expectedName
        "With null substance"                  | null                  | null
        "With Existing substance with name"    | new Substance("name") | "name"
        "With Existing substance with no name" | new Substance()       | null

    }

    void "getEntitySearch #label"() {
        when:
        Substance currentSubstance = this.restSubstanceService.getEntitySearch(substance, null)
        then:
        currentSubstance.getName() == expectedName
        where:
        label                                  | substance             | expectedName
        "With null substance"                  | null                  | null
        "With Existing substance with name"    | new Substance("name") | "name"
        "With Existing substance with no name" | new Substance()       | null
    }
}

