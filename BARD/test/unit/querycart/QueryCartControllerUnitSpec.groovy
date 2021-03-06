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

package querycart
import bardqueryapi.BardUtilitiesService
import bardqueryapi.ETagsService
import bardqueryapi.InetAddressUtil
import com.metasieve.shoppingcart.Shoppable
import com.metasieve.shoppingcart.ShoppingCartService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification
import spock.lang.Unroll

import javax.servlet.http.HttpServletResponse

@TestMixin(GrailsUnitTestMixin)
@TestFor(QueryCartController)
@Mock([Shoppable, QueryItem, CartAssay, CartProject])
@Unroll
class QueryCartControllerUnitSpec extends Specification {
    ShoppingCartService shoppingCartService
    QueryCartService queryCartService
    CartAssay cartAssay
    BardUtilitiesService bardUtilitiesService
    ETagsService eTagsService

    static final Long ID_IN_CART = 1
    static final Long EXTERNAL_ID = 2
    static final QueryItemType TYPE_IN_CART = QueryItemType.AssayDefinition
    static final String MOCK_SUMMARY_CONTENT = 'mock summary content'
    static final String MOCK_DETAILS_CONTENT = 'mock summary content'

    void setup() {
        controller.metaClass.mixin(InetAddressUtil)

        bardUtilitiesService = Mock(BardUtilitiesService)
        controller.bardUtilitiesService = bardUtilitiesService
        this.eTagsService = Mock(ETagsService)
        this.controller.eTagsService = this.eTagsService
        this.shoppingCartService = Mock(ShoppingCartService)
        controller.shoppingCartService = this.shoppingCartService
        this.queryCartService = Mock(QueryCartService)
        controller.queryCartService = this.queryCartService

        cartAssay = new CartAssay("foo", ID_IN_CART, EXTERNAL_ID)

        assert cartAssay.save()

        views['/bardWebInterface/_queryCartIndicator.gsp'] = MOCK_SUMMARY_CONTENT
        views['/bardWebInterface/_sarCartContent.gsp'] = MOCK_DETAILS_CONTENT
    }

    void tearDown() {
        // Tear down logic here
    }

    void "test successful addItem for #label"() {
        given:
        params.type = type as String
        params.id = id as String
        params.name = name
        params.smiles = smiles


        when:
        QueryItem item =  Mock(QueryItem)
        1 * queryCartService.getQueryItem(_, _, _, _) >> item
        1 * queryCartService.addToShoppingCart(_ as QueryItem)

        controller.addItem()

        then:
        assert response.status == HttpServletResponse.SC_OK

        where:
        label                        | type                          | id         | name   | smiles
        "add new assay w/summary"    | QueryItemType.AssayDefinition | 2          | 'Test' | null
        "add new compound w/summary" | QueryItemType.Compound        | 3          | 'Test' | 'C'
        "add new project w/summary"  | QueryItemType.Project         | 4          | 'Test' | null
        "add new assay w/details"    | QueryItemType.AssayDefinition | 5          | 'Test' | null
        "add new compound w/details" | QueryItemType.Compound        | 6          | 'Test' | 'C'
        "add new project w/details"  | QueryItemType.Project         | 7          | 'Test' | null
        "add assay already in cart"  | TYPE_IN_CART                  | ID_IN_CART | 'foo'  | null
    }


    void "test unsuccessful addItem for #label"() {
        given:
        params.type = 'foo'
        params.id = 'bar' as String
        params.name = 'foo'
        params.smiles = 'ccc'

        when:

        controller.addItem()

        then:
        queryCartService.addToShoppingCart(_) >> {null}

        assert response.status == HttpServletResponse.SC_BAD_REQUEST

    }

    void "test add existing assay not in cart"() {
        given:
        CartProject project = new CartProject('Test', 3, 2)
        params.type = project.queryItemType as String
        params.id = project.externalId as String
        params.name = project.name

        when:
        project.save()

        1 * queryCartService.getQueryItem(_, _, _, _) >> project
        1 * queryCartService.addToShoppingCart(project)

        controller.addItem()

        then:
        assert response.status == HttpServletResponse.SC_OK
    }

    void "test failed addItem for #label"() {
        given:
        params.type = type as String
        params.id = id as String
        params.name = 'Test'

        when:
        (_..1) * queryCartService.getQueryItem(_, _, _, _) >> null
        0 * queryCartService.addToShoppingCart(_ as QueryItem)
        controller.addItem()

        then:
        assert response.status == expectedStatus

        where:
        label          | type                  | id    | expectedStatus
        "unknown type" | 'temp'                | 6     | HttpServletResponse.SC_BAD_REQUEST
        "bad id"       | QueryItemType.Project | 'bad' | HttpServletResponse.SC_BAD_REQUEST
        "null type"    | null                  | 7     | HttpServletResponse.SC_BAD_REQUEST
        "null id"      | QueryItemType.Project | null  | HttpServletResponse.SC_BAD_REQUEST
    }

    void "test refreshSummaryView"() {
        when:
        1 * queryCartService.groupUniqueContentsByType(_) >> {[:]}
        1 * queryCartService.totalNumberOfUniqueItemsInCart(_ as Map) >> {0}
        3 * queryCartService.totalNumberOfUniqueItemsInCart(_, _) >> {0}

        controller.refreshSummaryView()

        then:
        assert response.status == HttpServletResponse.SC_OK
        assert response.text == MOCK_SUMMARY_CONTENT
    }

    void "test createCompositeETag #label"() {
        when:
        controller.createCompositeETag()
        then:
        queryCartService.retrieveCartCompoundIdsFromShoppingCart() >> {cids}
        queryCartService.retrieveCartAssayIdsFromShoppingCart() >> {adids}
        queryCartService.retrieveCartProjectIdsFromShoppingCart() >> {pids}
        numberOFTimesExecuted * eTagsService.createCompositeETags(_, _, _) >> {expectedResponse}

        assert expectedResponse == response.text
        assert HttpServletResponse.SC_OK == response.status
        where:
        label          | cids   | adids  | pids   | numberOFTimesExecuted | expectedResponse
        "Only CID"     | [1, 2] | []     | []     | 1                     | "ee"
        "Only PID"     | []     | []     | [1, 2] | 1                     | "ee"
        "Only ADID"    | []     | [1, 2] | []     | 1                     | "ee"
        "Only All Ids" | [1, 2] | [2, 3] | [4, 5] | 1                     | "ee"
        "No Ids"       | []     | []     | []     | 0                     | ""


    }

    void "test updateDetails"() {
        when:
        1 * queryCartService.groupUniqueContentsByType(_) >> {[:]}
        1 * queryCartService.totalNumberOfUniqueItemsInCart(_ as Map) >> {0}

        controller.refreshDetailsView()

        then:
        assert response.status == HttpServletResponse.SC_OK
        assert response.text == MOCK_DETAILS_CONTENT
    }

    void "test successful removeItem for existing QueryItem"() {
        given:
        params.type = TYPE_IN_CART as String
        params.id = ID_IN_CART as String

        when:
        queryCartService.findQueryItemById(_, _) >> cartAssay
        1 * queryCartService.removeFromShoppingCart(cartAssay)

        controller.removeItem()

        then:
        assert response.status == HttpServletResponse.SC_OK
    }

    void "test removeItem for non-existant QueryItem"() {
        given:
        params.type = TYPE_IN_CART as String
        params.id = 9999 as String

        when:
        0 * queryCartService.removeFromShoppingCart(_)

        controller.removeItem()

        then:
        assert response.status == HttpServletResponse.SC_OK
    }

    void "test invalid removeItem for #label"() {
        given:
        params.type = type as String
        params.id = id as String

        when:
        0 * queryCartService.removeFromShoppingCart(_)

        controller.removeItem()

        then:
        assert response.status == HttpServletResponse.SC_BAD_REQUEST

        where:
        label              | type         | id
        "null id"          | TYPE_IN_CART | null
        "null id and type" | null         | null
        "null type"        | null         | ID_IN_CART
        "bad id"           | TYPE_IN_CART | 'bad'
        "bad type"         | 'bad'        | ID_IN_CART
    }

    void "test removeAll"() {
        when:
        1 * queryCartService.emptyShoppingCart() >> {null}
        controller.removeAll()

        then:
        assert response.status == HttpServletResponse.SC_OK
    }

    void "test isInCart for #label"() {
        given:
        params.id = idToCheck as String
        params.type = typeToCheck as String

        when:
        if (shouldFind) {
            1 * queryCartService.findQueryItemById(_, _) >> cartAssay
            1 * queryCartService.isInShoppingCart(_) >> true
        }
        else {
            0 * queryCartService.isInShoppingCart(_)
        }
        controller.isInCart()

        then:
        assert response.status == HttpServletResponse.SC_OK
        assert response.text == shouldFind.toString()

        where:
        label          | idToCheck  | typeToCheck            | shouldFind
        "Item in cart" | ID_IN_CART | TYPE_IN_CART           | true
        "diff ID"      | 6          | TYPE_IN_CART           | false
        "diff type"    | ID_IN_CART | QueryItemType.Compound | false
    }

    void "test invalid isInCart for #label"() {
        given:
        params.id = id as String
        params.type = type as String

        when:
        0 * queryCartService.isInShoppingCart(_)

        controller.isInCart()

        then:
        assert response.status == HttpServletResponse.SC_BAD_REQUEST

        where:
        label       | id         | type
        "null ID"   | null       | TYPE_IN_CART
        "null type" | ID_IN_CART | null
        "bad type"  | ID_IN_CART | 'bad'
        "bad ID"    | 'bad'      | TYPE_IN_CART
    }


    void "test successful addItems for #label"() {
        given:
        String itemsJSON = "[{'id': ${id}, 'type': ${type}, 'name': ${name}, 'smiles': ${smiles}}]"
        params.items = itemsJSON

        when:
        QueryItem item =  Mock(QueryItem)
        1 * queryCartService.getQueryItem(_, _, _, _) >> item
        1 * queryCartService.addToShoppingCart(_ as QueryItem)

        controller.addItems()

        then:
        assert response.status == HttpServletResponse.SC_OK

        where:
        label                        | type                          | id         | name   | smiles
        "add new assay w/summary"    | QueryItemType.AssayDefinition | 2          | 'Test' | null
        "add new compound w/summary" | QueryItemType.Compound        | 3          | 'Test' | 'C'
        "add new project w/summary"  | QueryItemType.Project         | 4          | 'Test' | null
        "add new assay w/details"    | QueryItemType.AssayDefinition | 5          | 'Test' | null
        "add new compound w/details" | QueryItemType.Compound        | 6          | 'Test' | 'C'
        "add new project w/details"  | QueryItemType.Project         | 7          | 'Test' | null
        "add assay already in cart"  | TYPE_IN_CART                  | ID_IN_CART | 'foo'  | null
    }


    void "test failed addItems for #label"() {
        given:
        String itemsJSON = "[{'id': ${id}, 'type': ${type}, 'name': 'Test', 'smiles': 'C=C'}]"
        params.items = itemsJSON

        when:
        0 * queryCartService.addToShoppingCart(_ as QueryItem)
        controller.addItems()

        then:
        assert response.status == expectedStatus

        where:
        label          | type                  | id    | expectedStatus
        "unknown type" | 'temp'                | 6     | HttpServletResponse.SC_BAD_REQUEST
        "bad id"       | QueryItemType.Project | 'bad' | HttpServletResponse.SC_BAD_REQUEST
    }
}
