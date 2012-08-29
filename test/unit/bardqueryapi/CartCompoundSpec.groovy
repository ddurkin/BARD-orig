package bardqueryapi

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
//@TestMixin(GrailsUnitTestMixin)
@TestFor(BardWebInterfaceController)
class CartCompoundSpec  extends Specification  {

    void setup() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void "test shopping cart compound element"() {
        when:
            CartCompound cartCompound = new CartCompound(smiles: "c1ccccc1")
            assertNotNull(cartCompound)

        then:
            assert cartCompound.smiles=='c1ccccc1'
            assertNull cartCompound.shoppingItem
    }



    void "test constraints on Kurt compound object"() {
        setup:
        mockForConstraintsTests(CartCompound)

        when:
        CartCompound cartCompound = new CartCompound(smiles: smiles)
        cartCompound.validate()

        then:
        cartCompound.hasErrors() == !valid

        where:
        smiles          |   valid
        null            |   false
        ""              |   true
        "Some assay"    |   true
    }


}
