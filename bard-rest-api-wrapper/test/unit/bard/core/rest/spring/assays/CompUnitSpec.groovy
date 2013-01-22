package bard.core.rest.spring.assays

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CompUnitSpec extends Specification {
    @Shared
    ObjectMapper objectMapper = new ObjectMapper()

    public static final String COMP = '''
   {
        "entityId": null,
        "entity": "assay",
        "source": "cap-context",
        "id": 7186,
        "display": ".05 um",
        "contextRef": "Context for percent activity",
        "key": "screening concentration",
        "value": null,
        "extValueId": null,
        "url": null,
        "displayOrder": 0,
        "related": "measureRefs:22510"
    }
       '''

    void "test serialization to Comp"() {
        when:
        final Comp comp = objectMapper.readValue(COMP, Comp.class)
        then:
        assert comp.display==".05 um"
        assert comp.entity=="assay"
        assert !comp.entityId
        assert comp.source== "cap-context"
        assert comp.id==7186
        assert comp.contextRef=="Context for percent activity"
        assert comp.key== "screening concentration"
        assert !comp.value
        assert !comp.extValueId
        assert !comp.url
        assert comp.displayOrder==0
        assert comp.related=="measureRefs:22510"
    }


}

