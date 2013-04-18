package bard.db.registration.additemwizard

import bard.db.dictionary.Element
import bard.db.dictionary.OntologyDataAccessService
import bard.db.dictionary.UnitConversion
import bard.db.dictionary.UnitTree
import bard.db.registration.AssayContext
import bard.db.registration.AssayContextService
import bard.db.registration.AttributeType
import org.apache.commons.lang.StringUtils

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * ajaxflow Controller
 *
 * @author ycruz
 * @package AjaxFlow
 */

class AttributeCommand implements Serializable {

    Long elementId
    String path
    String assayContextIdValue
    String attributeId
    String attributeLabel

    static constraints = {
        attributeId(nullable: false, blank: false)
    }

    @Override
    public String toString() {
        return "AttributeCommand{" +
                "elementId=" + elementId +
                ", path='" + path + '\'' +
                ", assayContextIdValue='" + assayContextIdValue + '\'' +
                ", attributeId='" + attributeId + '\'' +
                ", attributeLabel='" + attributeLabel + '\'' +
                '}';
    }
}

class ValueTypeCommand implements Serializable {

    String valueTypeOption

    static constraints = {
        valueTypeOption(nullable: false, blank: false)
    }
}

class FixedValueCommand implements Serializable {

    Long valueId
    String valueLabel
    String attributeElementId
    String extValueId
    String valueQualifier
    Long valueUnitId
    String valueUnitLabel
    String numericValue
    String textValue
    boolean isNumericValue

    boolean validateValue() {
        // delegate to validations performed by constraints
        validate()

        isNumericValue = false;

        if (!StringUtils.isBlank(textValue)) {
            // a text value was provided
        } else if (extValueId) {
            // a value for extValueId is valid
        } else if (valueId) {
            // valid value
        } else if (StringUtils.isNotBlank(numericValue)) {
            // Handles Numeric values: negatives, and comma formatted values. Also handles a single decimal point
            Pattern pattern = Pattern.compile("^(\\d|-)?(\\d|,)*\\.?\\d*\$")
            Matcher matcher = pattern.matcher(numericValue)
            if (matcher.matches())
                isNumericValue = true;
            else
                errors.reject("fixedValue.nomatch.numericValue", "Your entry does not match a numeric value")
        } else {
            errors.reject("fixedValue.missing.value", "Either numeric value or string value must be provided")
        }

        return !hasErrors();
    }

    @Override
    public String toString() {
        return "FixedValueCommand{" +
                "valueId=" + valueId +
                ", valueLabel='" + valueLabel + '\'' +
                ", attributeElementId='" + attributeElementId + '\'' +
                ", extValueId='" + extValueId + '\'' +
                ", valueQualifier='" + valueQualifier + '\'' +
                ", valueUnitId=" + valueUnitId +
                ", valueUnitLabel='" + valueUnitLabel + '\'' +
                ", numericValue='" + numericValue + '\'' +
                ", textValue='" + textValue + '\'' +
                ", isNumericValue=" + isNumericValue +
                '}';
    }
}

class ListValueCommand implements Serializable {

    Long valueId
    String valueLabel
    String attributeElementId
    String extValueId
    String valueQualifier
    Long valueUnitId
    String valueUnitLabel
    String numericValue
    String textValue
    boolean isNumericValue

    boolean validateList(List<ListValueCommand> listOfValues) {
        if (listOfValues.empty) {
            println "List is empty"
            errors.reject("listValue.missing.value", "At least one numeric value or one string value must be added to the list to proceed")
        }
        return !hasErrors();
    }

    boolean validateValue() {
        // delegate to validations performed by constraints
        validate()

        if (StringUtils.isNotBlank(textValue)) {
            // a text value was provided
        } else if (extValueId) {
            // a value for extValueId is valid
        } else if (valueId) {
            isNumericValue = false;
        } else if (StringUtils.isNotBlank(numericValue)) {
            // Handles Numeric values: negatives, and comma formatted values. Also handles a single decimal point
            Pattern pattern = Pattern.compile("^(\\d|-)?(\\d|,)*\\.?\\d*\$")
            Matcher matcher = pattern.matcher(numericValue)
            if (matcher.matches())
                isNumericValue = true;
            else
                errors.reject("fixedValue.nomatch.numericValue", "Your entry does not match a numeric value")
        } else {
            errors.reject("fixedValue.missing.value", "Either numeric value or string value must be provided")
        }

        return !hasErrors();
    }
}

class RangeValueCommand implements Serializable {

    String attributeElementId
    Long valueUnitId
    String valueUnitLabel
    String minValue
    String maxValue

    static constraints = {
        minValue(nullable: false, blank: false)
        maxValue(nullable: false, blank: false)
    }

    boolean validateValue() {
        // delegate to validations performed by constraints
        validate()

        // Handles Numeric values: negatives, and comma formatted values. Also handles a single decimal point
        Pattern pattern = Pattern.compile("^(\\d|-)?(\\d|,)*\\.?\\d*\$")
        Matcher matcher = pattern.matcher(minValue)
        if (!matcher.matches())
            errors.reject("rangeValue.nomatch.minValue", "Your entry for Min Value does not match a numeric value")

        matcher = pattern.matcher(maxValue)
        if (!matcher.matches())
            errors.reject("rangeValue.nomatch.minValue", "Your entry for Max Value does not match a numeric value")

        return !hasErrors();
    }
}


class AddItemWizardController {
    // the pluginManager is used to check if the Grom
    // plugin is available so we can 'Grom' development
    // notifications to the unified notifications daemon
    // (see http://www.grails.org/plugin/grom)
    def pluginManager

    AssayContextService assayContextService
    OntologyDataAccessService ontologyDataAccessService

    // need to clear the session factory because grails includes the hibernate session in the flow.  I think this is
    // a terrible idea, and I don't fully know why grails does it.  It'd be nice if we could at least disable it.
    // but working around it for now.
    // http://davecurryco.blogspot.com/2010/04/grails-from-crypt-webflows.html
    def transient sessionFactory

    /**
     * index method, redirect to the webflow
     * @void
     */
    def index = {
        // Grom a development message
        if (pluginManager.getGrailsPlugin('grom')) "redirecting into the webflow".grom()

        redirect(action: 'pages')
    }

    def addItemWizard(Long assayId, Long assayContextId, String cardSection) {
        println "addItemWizard -> Assay ID: " + assayId
        render(template: "common/ajaxflow", model: [assayId: assayId, assayContextId: assayContextId, path: cardSection])
    }

    /**
     * WebFlow definition
     * @void
     */
    def pagesFlow = {
        // start the flow
        onStart {
            // Grom a development message
            if (pluginManager.getGrailsPlugin('grom')) "entering the WebFlow".grom()

            // define variables in the flow scope which is availabe
            // throughout the complete webflow also have a look at
            // the Flow Scopes section on http://www.grails.org/WebFlow
            //
            // The following flow scope variables are used to generate
            // wizard tabs. Also see common/_tabs.gsp for more information
            flow.page = 0
            flow.pages = [
                    [title: 'Attribute', description: 'Define attribute', pageNumber: 1],
                    [title: 'Value Type', description: 'Value type', pageNumber: 2],
                    [title: 'Define Value', description: 'Define value', pageNumber: 3],
                    [title: 'Review & Confirm', description: 'Review and save your entries', pageNumber: 4],
                    [title: 'Done', description: 'Suggestions for Next', pageNumber: 5]
            ]
            flow.cancel = true;
            flow.quickSave = true;

            flow.assayContextId = params.assayContextId
            println "params.assayContextId = " + params.assayContextId

            flow.attribute = null
            flow.attributeExternalUrl = null
            flow.supportsExternalOntologyLookup = false
            flow.valueType = null
            flow.fixedValue = null
            flow.listValue = null
            flow.listOfValues = new ArrayList<ListValueCommand>()
            flow.listIndex = null
            flow.rangeValue = null
            flow.freeValue = null
            flow.fixedValueAddNewItem = false

            flow.itemSaved = false

            println("flow: " + flow)

            success()
        }

        // first wizard page: Asking for attribute
        pageOne {
            render(view: "_page_one")
            onRender {
                // Grom a development message
                if (pluginManager.getGrailsPlugin('grom')) ".rendering the partial: pages/_page_one.gsp".grom()
                flow.page = 1
                println "Wizard page: " + flow.page
                success()
            }
            on("cancel").to "closeWizard"
            on("close").to "closeWizard"
            on("next") { AttributeCommand cmd ->
                if (cmd.hasErrors()) {
                    flow.attribute = cmd
                    return error()
                }
                flow.attribute = cmd

                println "calling closure for AttributeCommand ${cmd.dump()}"
                println "(pageOne - next) flow.attribute.attributeId: " + flow.attribute.attributeId
                Element attributeElement = Element.get(flow.attribute.attributeId)
                println "attributeElement object: ${attributeElement.dump()}"
                flow.attribute.attributeLabel = attributeElement.label
                flow.attributeExternalUrl = attributeElement.externalURL
                if (StringUtils.isNotBlank(attributeElement.externalURL)) {
                    if (ontologyDataAccessService.externalOntologyHasIntegratedSearch(attributeElement.externalURL)) {
                        flow.supportsExternalOntologyLookup = true
                    }
                }

                sessionFactory.currentSession.clear()
                flow.page = 2
                success()

            }.to "pageTwo"
            on("toPageTwo").to "pageTwo"
            on("toPageThree").to "pageThree"
            on("toPageFour").to "pageFour"
            on("toPageFive").to "save"
        }

        // second wizard page: Asking for value type
        pageTwo {
            render(view: "_page_two")
            onRender {
                // Grom a development message
                if (pluginManager.getGrailsPlugin('grom')) ".rendering the partial: pages/_page_two.gsp".grom()
                flow.page = 2
                println "Wizard page: " + flow.page
                success()
            }
            on("cancel").to "closeWizard"
            on("close").to "closeWizard"
            on("next") { ValueTypeCommand cmd ->
                if (cmd.hasErrors()) {
                    flow.valueType = cmd
                    return error()
                }
                flow.valueType = cmd
                println "calling closure for ValueTypeCommand ${cmd.dump()}"
                println "Page 2 Next - flow.valueType.valueTypeOption = " + flow.valueType?.valueTypeOption

            }.to "valueTypeRedirect"
            on("previous") {
                flow.page = 1
                success()
            }.to "pageOne"
            on("toPageOne").to "pageOne"
            on("toPageThree").to "pageThree"
            on("toPageThreeList").to "pageThreeList"
            on("toPageFour").to "pageFour"
            on("toPageFive") {
                flow.page = 5
            }.to "save"
        }

        // Third wizard page (For assay type - Fixed): Asking for value
        pageThree {
            render(view: "_page_three")
            onRender {
                // Grom a development message
                if (pluginManager.getGrailsPlugin('grom')) ".rendering the partial pages/_page_three.gsp".grom()
                flow.page = 3
                println "Wizard page: " + flow.page
                success()
            }
            on("cancel").to "closeWizard"
            on("close").to "closeWizard"
            on("next") { FixedValueCommand cmd ->
                if (!cmd.validateValue()) {
                    println("validation failed")
                    flow.fixedValue = cmd
                    return error()
                }

                flow.fixedValue = cmd
                println "calling closure for {FixedValueCommand ${cmd.dump()}"
                println "isNumericValue = " + cmd.isNumericValue
                if (cmd.extValueId) {
                    // valid state
                } else if(!StringUtils.isBlank(cmd.textValue)) {
                    // valid state
                    flow.fixedValue.textValue = cmd.textValue
                    flow.fixedValue.valueLabel = cmd.textValue
                } else if (!cmd.isNumericValue) {
                    def valueElement = Element.get(flow.fixedValue.valueId)
                    flow.fixedValue.valueLabel = valueElement.label
                } else {
                    // I think this is the path executed when numeric value is provided?
                    /*
                    println "flow.fixedValue.numericValue = ${flow.fixedValue.numericValue}"
                    println "flow.fixedValue.valueUnitId = ${flow.fixedValue.valueUnitId}"
                    println "flow.fixedValue.attributeElementId = ${flow.fixedValue.attributeElementId}"
                    println "flow = ${flow}"
                    */

                    Element attributeElement = Element.get(flow.attribute.attributeId)
                    println "attributeElement = ${attributeElement}"

                    if (flow.fixedValue.valueUnitId != attributeElement.unit?.id) {
                        Element fromUnit = Element.get(flow.fixedValue.valueUnitId)
                        UnitConversion unitConversion = UnitConversion.findByFromUnitAndToUnit(fromUnit, attributeElement.unit)
                        flow.fixedValue.numericValue = unitConversion?.convert(new BigDecimal(flow.fixedValue.numericValue))
                    }
                    flow.fixedValue.valueUnitLabel = attributeElement.unit?.abbreviation
                }
                sessionFactory.currentSession.clear()
                flow.page = 4
                success()

            }.to "pageFour"
            on("previous") {
                flow.page = 2
                success()
            }.to "pageTwo"
            on("toPageOne").to "pageOne"
            on("toPageTwo").to "pageTwo"
            on("toPageFour").to "pageFour"
            on("toPageFive") {
                flow.page = 5
            }.to "save"
        }

        // Third wizard page (For assay type - List): Asking for a list of values
        pageThreeList {
            render(view: "_page_three_list")
            onRender {
                // Grom a development message
                if (pluginManager.getGrailsPlugin('grom')) ".rendering the partial pages/_page_three_list.gsp".grom()
                flow.page = 3
                println "Wizard page: " + flow.page
                success()
            }
            on("cancel").to "closeWizard"
            on("close").to "closeWizard"
            on("next") { ListValueCommand cmd ->
                if (!cmd.validateList(flow.listOfValues)) {
                    flow.listValue = cmd
                    return error()
                }
                flow.listValue = cmd
                println "calling closure for {ListValueCommand ${cmd.dump()}"
                println "isNumericValue = " + cmd.isNumericValue
                flow.page = 4
                success()

            }.to "pageFour"
            on("previous") {
                flow.page = 2
                success()
            }.to "pageTwo"
            on("addValueToList") { ListValueCommand cmd ->
                if (!cmd.validateValue()) {
                    flow.listValue = cmd
                    return error()
                }
                flow.listValue = cmd

            }.to "addValueToList"
            on("removeItemFromList") {
                println "removeItemFromList -> Flow.listIndex: " + flow.listIndex
                def listIndex = params.id
                if ("".equals(listIndex))
                    flow.listIndex = 0
                else
                    flow.listIndex = Integer.parseInt(listIndex)
                println "removeItemFromList -> List index: " + listIndex
            }.to "removeItemFromList"
            on("toPageOne").to "pageOne"
            on("toPageTwo").to "pageTwo"
            on("toPageFour").to "pageFour"
            on("toPageFive") {
                flow.page = 5
            }.to "save"
        }

        // Third wizard page (For assay type - Range): Asking for value
        pageThreeRange {
            render(view: "_page_three_range")
            onRender {
                // Grom a development message
                if (pluginManager.getGrailsPlugin('grom')) ".rendering the partial pages/_page_three_range.gsp".grom()

                flow.page = 3
                success()
            }
            on("cancel").to "closeWizard"
            on("close").to "closeWizard"
            on("next") { RangeValueCommand cmd ->
                if (!cmd.validateValue()) {
                    flow.rangeValue = cmd
                    return error()
                }
                flow.rangeValue = cmd
                println "calling closure for {RangeValueCommand ${cmd.dump()}"

                sessionFactory.currentSession.clear()
                flow.page = 4
                success()

            }.to "pageFour"
            on("previous").to "pageTwo"
            on("toPageOne").to "pageOne"
            on("toPageTwo").to "pageTwo"
            on("toPageFour").to "pageFour"
            on("toPageFive") {
                flow.page = 5
            }.to "save"
        }

        // Third wizard page (For assay type - Free): No value
        pageThreeFree {
            render(view: "_page_three_free")
            onRender {
                // Grom a development message
                if (pluginManager.getGrailsPlugin('grom')) ".rendering the partial pages/_page_three_free.gsp".grom()

                flow.page = 3
                success()
            }
            on("cancel").to "closeWizard"
            on("close").to "closeWizard"
            on("next") {
                flow.page = 4
                success()
            }.to "pageFour"
            on("previous").to "pageTwo"
            on("toPageOne").to "pageOne"
            on("toPageTwo").to "pageTwo"
            on("toPageFour").to "pageFour"
            on("toPageFive") {
                flow.page = 5
            }.to "save"
        }

        // Third wizard page (For assay type - Fixed): Asking for new value item
        pageThreeFixedNewItem {
            render(view: "_page_three_fixed_new_item")
            onRender {
                // Grom a development message
                if (pluginManager.getGrailsPlugin('grom')) ".rendering the partial pages/_page_three_fixed_new_item.gsp".grom()

                flow.page = 3
                success()
            }
            on("cancel").to "closeWizard"
            on("close").to "closeWizard"
            on("next") {
                flow.page = 4
                success()
            }.to "pageFour"
            on("previous") {
                flow.fixedValueAddNewItem = false
            }.to "pageThreeFree"
            on("toPageOne").to "pageOne"
            on("toPageTwo").to "pageTwo"
            on("toPageFour").to "pageFour"
            on("toPageFive") {
                flow.page = 5
            }.to "save"
        }

        // four wizard page: Confirmation page. Allows saving attribute / Value pair
        pageFour {
            render(view: "_page_four")
            onRender {
                // Grom a development message
                if (pluginManager.getGrailsPlugin('grom')) ".rendering the partial pages/_page_four.gsp".grom()
                flow.page = 4
                println "Wizard page: " + flow.page
                success()
            }
            on("cancel").to "closeWizard"
            on("close").to "closeWizard"
            on("save") {
                // put some logic in here
                flow.page = 5
            }.to "save"
            on("previous") {
                flow.page = 2
            }.to "valueTypeRedirect"
            on("toPageOne").to "pageOne"
            on("toPageTwo").to "pageTwo"
            on("toPageThree").to "pageThree"
            on("toPageFive") {
                flow.page = 5
            }.to "save"
        }

        // last wizard page
        finalPage {
            render(view: "_final_page")
            onRender {
                // Grom a development message
                if (pluginManager.getGrailsPlugin('grom')) ".rendering the partial pages/_final_page.gsp".grom()
                flow.page = 5
                println "Wizard page: " + flow.page
                success()
            }
            on("cancel").to "closeWizard"
            on("close").to "closeWizard"
            on("addAnotherItem").to "addAnotherItem"
        }

        valueTypeRedirect {
            action {
                try {
                    // Grom a development message
                    if (pluginManager.getGrailsPlugin('grom')) ".persisting instances to the database...".grom()

                    if (flow.valueType.valueTypeOption.equals(AttributeType.Fixed.toString())) {
                        flow.page = 3
                        toPageThree()
                    } else if (flow.valueType.valueTypeOption.equals(AttributeType.List.toString())) {
                        flow.page = 3
                        toPageThreeList()
                    } else if (flow.valueType.valueTypeOption.equals(AttributeType.Range.toString())) {
                        flow.page = 3
                        toPageThreeRange()
                    } else if (flow.valueType.valueTypeOption.equals(AttributeType.Free.toString())) {
                        flow.page = 3
                        toPageThreeFree()
                    }
                }
                catch (Exception e) {
                    // put your error handling logic in
                    // here
                    sessionFactory.currentSession.clear()
                    println "Exception -> " + e
                    flow.page = 2
                    error()
                }
            }
            on("error").to "error"
            on(Exception).to "error"
            on("toPageThree").to "pageThree"
            on("toPageThreeList").to "pageThreeList"
            on("toPageThreeRange").to "pageThreeRange"
            on("toPageThreeFree").to "pageThreeFree"
        }

        // action to add a value to the list for an attribute
        addValueToList {
            action {
                try {
                    println "On action: addValueToList"
                    flow.listOfValues.add(flow.listValue)
//					AssayContextItem item = assayContextService.createItem(flow.attribute, flow.valueType, flow.fixedValue, flow.listValue)
                    println "# of Items in list: ${flow.listOfValues.size()}"
                    flow.page = 2
                    toPageThreeList()
                } catch (Exception e) {
                    println "Exception caught -> " + e.getMessage()
                    sessionFactory.currentSession.clear()
//                    flow.page = 2
                    error()
                }
            }
            on("error").to "error"
            on(Exception).to "error"
            on("success").to "pageThreeList"
            on("toPageThreeList").to "pageThreeList"
        }

        removeItemFromList {
            action {
                println "removeItemFromList -> flow.listIndex: " + flow.listIndex
                flow.listOfValues.remove(flow.listIndex)
                toPageThreeList()
            }
            on("error").to "error"
            on(Exception).to "error"
            on("success").to "pageThreeList"
            on("toPageThreeList").to "pageThreeList"
        }

        // save action
        save {
            action {
                // here you can validate and save the
                // instances you have created in the
                // ajax flow.
                try {
                    // Grom a development message
                    if (pluginManager.getGrailsPlugin('grom')) ".persisting instances to the database...".grom()

                    AssayContext assayContext = AssayContext.get(flow.assayContextId)
                    def isSaved = false;
                    if (flow.valueType.valueTypeOption.equals(AttributeType.Fixed.toString())) {
                        println "Saving item ... ${flow.fixedValue}"
                        isSaved = assayContextService.saveItem(assayContext, flow.attribute, flow.valueType, flow.fixedValue)
                    } else if (flow.valueType.valueTypeOption.equals(AttributeType.List.toString())) {
                        println "Saving list of items ..."
                        isSaved = assayContextService.saveItems(assayContext, flow.attribute, flow.valueType, flow.listOfValues)
                    } else if (flow.valueType.valueTypeOption.equals(AttributeType.Range.toString())) {
                        println "Saving range items ..."
                        isSaved = assayContextService.saveRangeItem(assayContext, flow.attribute, flow.valueType, flow.rangeValue)
                    } else if (flow.valueType.valueTypeOption.equals(AttributeType.Free.toString())) {
                        println "Saving free item ..."
                        isSaved = assayContextService.saveFreeItem(assayContext, flow.attribute, flow.valueType)
                    }

                    sessionFactory.currentSession.flush()
                    sessionFactory.currentSession.clear()
                    if (isSaved) {
                        println "New item was successfully added to the card"
                        flow.itemSaved = true;
                        success()
                    } else {
                        println "ERROR - unable to add item to the card"
                        flow.page = 4
                        error()
                    }

                } catch (Exception e) {
                    // put your error handling logic in
                    // here
                    sessionFactory.currentSession.clear()
                    log.error("exception saving item", e);
                    flow.page = 4
                    error()
                }
            }
            on("error").to "error"
            on(Exception).to "error"
            on("success").to "finalPage"
        }

        // render errors
        error {
            render(view: "_error")
            onRender {
                // Grom a development message
                if (pluginManager.getGrailsPlugin('grom')) ".rendering the partial pages/_error.gsp".grom()

                // set page to 4 so that the navigation
                // works (it is disabled on the final page)
//                flow.page = 4
            }
            on("cancel").to "closeWizard"
            on("close").to "closeWizard"
            on("next").to "save"
            on("previous").to "pageFour"
            on("toPageOne").to "pageOne"
            on("toPageTwo").to "pageTwo"
            on("toPageThree").to "pageThree"
            on("toPageFour").to "pageFour"
            on("toPageFive").to "save"

        }

        // calls the view with code to close the wizard window
        closeWizard {
            render(view: "_close_wizard")
            onRender {
                // Grom a development message
                if (pluginManager.getGrailsPlugin('grom')) ".rendering the partial pages/_final_page.gsp".grom()

                success()
            }
        }

        // calls the view to restartthe wizard an add another item
        addAnotherItem {
            render(view: "_add_another_item")
            onRender {
                // Grom a development message
                if (pluginManager.getGrailsPlugin('grom')) ".rendering the partial pages/_final_page.gsp".grom()

                success()
            }
        }

    }
}
