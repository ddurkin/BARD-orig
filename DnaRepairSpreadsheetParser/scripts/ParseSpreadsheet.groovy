//import dnarepairspreadsheet.DnaSpreadsheetService
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.util.CellReference
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.util.CellUtil
import org.apache.poi.ss.util.SheetUtil
import org.apache.commons.lang.StringUtils
import bard.db.dictionary.Element
import bard.db.registration.Assay
import bard.db.registration.AssayContext
import org.springframework.transaction.TransactionStatus
import bard.db.registration.AssayContextItem
import bard.db.registration.AttributeType

def out = new File('DnaSpreadsheetParsing' + '_' + System.currentTimeMillis() + '.txt')

List<Attribute> processOrTarget = [new Attribute('$/C', '$/D', AttributeType.Fixed, true, null)]
List<Attribute> assayFormat = [new Attribute('1/E', '$/E', AttributeType.Fixed)]
List<Attribute> assayType = [new Attribute('2/F', '$/F', AttributeType.Fixed)]
List<Attribute> assayComponent = [
        new Attribute('2/G', '$/G', AttributeType.Fixed),
        new Attribute('2/H', '$/H', AttributeType.Fixed),
        new Attribute('2/I', '$/I', AttributeType.Fixed, true, null),
        new Attribute('2/J', '$/J', AttributeType.Fixed, true, null),
        new Attribute('2/K', '$/K', AttributeType.Fixed)]
List<Attribute> assayComponentCustom = [
        new Attribute('2/L', '$/L', AttributeType.Fixed, true, null),
        new Attribute('2/M', '$/M', AttributeType.Fixed)]
List<Attribute> assayDetector = [
        new Attribute('2/N', '$/N', AttributeType.Fixed, true, null),
        new Attribute('2/O', '$/O', AttributeType.Fixed)]
List<Attribute> assayDetection = [
        new Attribute('2/P', '$/P', AttributeType.Fixed),
        new Attribute('2/Q', '$/Q', AttributeType.Fixed)]
List<Attribute> assayReadout = [
        new Attribute('2/R', '$/R', AttributeType.Fixed),
        new Attribute('2/S', '$/S', AttributeType.Fixed),
        new Attribute('2/T', '$/T', AttributeType.Fixed)]
List<Attribute> assayFootprint = [new Attribute('2/U', '$/U', AttributeType.Fixed)]
List<Attribute> assayExcitation = [
        new Attribute('2/V', '$/V', AttributeType.Fixed),
        new Attribute('2/W', '$/W', AttributeType.Fixed)]
List<Attribute> assayAbsorbance = [new Attribute('2/X', '$/X', AttributeType.Fixed)]
List<Attribute> resultActivityThreshold = [new Attribute('2/AA', '$/AA', AttributeType.Fixed, false, '$/Z')]//the qualifier belongs to the Activity-threshold attribute
List<Attribute> assayConcentrationPoint = [new Attribute('2/AH', '$/AH', AttributeType.Free)]
List<Attribute> assayReplicates = [new Attribute('2/AI', '$/AI', AttributeType.Free)]
//List<Attribute> assayFormat2 = [new Attribute('2/AQ', '$/AQ', AttributeType.Fixed)]
//List<Attribute> assayTargetType = [new Attribute('2/AS', '$/AS', AttributeType.Fixed)]
//List<Attribute> assayDetection2 = [new Attribute('2/AT', '$/AT', AttributeType.Fixed)]

List<contextGroup> spreadsheetAssayContextGroups = [new contextGroup(name: 'processOrTarget', attributes: processOrTarget),
        new contextGroup(name: 'assayFormat', attributes: assayFormat),
        new contextGroup(name: 'assayType', attributes: assayType),
        new contextGroup(name: 'assayComponent', attributes: assayComponent),
        new contextGroup(name: 'assayComponentCustom', attributes: assayComponentCustom),
        new contextGroup(name: 'assayDetection', attributes: assayDetection),
        new contextGroup(name: 'assayReadout', attributes: assayReadout),
        new contextGroup(name: 'assayDetector', attributes: assayDetector),
        new contextGroup(name: 'assayFootprint', attributes: assayFootprint),
        new contextGroup(name: 'assayExcitation', attributes: assayExcitation),
        new contextGroup(name: 'assayAbsorbance', attributes: assayAbsorbance),
        new contextGroup(name: 'resultActivityThreshold', attributes: resultActivityThreshold),
        new contextGroup(name: 'assayConcentrationPoint', attributes: assayConcentrationPoint),
        new contextGroup(name: 'assayReplicates', attributes: assayReplicates)]
//        new contextGroup(name: 'assayFormat2', attributes: assayFormat2),
//        new contextGroup(name: 'assayTargetType', attributes: assayTargetType),
//        new contextGroup(name: 'assayDetection2', attributes: assayDetection2)]

Map attributeNameMapping = ['[detector] assay component (type in)': 'assay component',
        '[detector] assay component role': 'assay component role',
        'assay component concentration (type in)': 'assay component concentration',
        'assay component concentration units': 'concentration unit',
        'biological component base name': 'assay component',
        'species': 'species name',
        'assay detection': 'detection method type',
        'assay target type': 'assay type',
        'component name (type in)': 'assay component name',
        '--': '']
//Delete from here
/*        '# concentration points': 'assay component name',
        '# replicates': 'assay component name',
        'assay readout content': 'assay component name',
        'assay readout type': 'assay component name',
        'biochemical': 'assay component name',
        'biorad chemidocxrs imaging system': 'assay component name',
        'cell based: lysed cell': 'assay component name',
        'cell-based: live cell': 'assay component name',
        'cells/ml': 'assay component name',
        'cellular pathway': 'assay component name',
        'chemically labeled protein': 'assay component name',
        'compound profiling': 'assay component name',
        'detection instrument': 'assay component name',
        'fluorescence:other': 'assay component name',
        'fm': 'assay component name',
        'fret/bret': 'assay component name',
        'grams-per-liter': 'assay component name',
        'htrf': 'assay component name',
        'hydrolase': 'assay component name',
        'imaging methods': 'assay component name',
        'in cell analyzer': 'assay component name',
        'kinase': 'assay component name',
        'kodak biomax mr-1': 'assay component name',
        'log10 molar': 'assay component name',
        'luminescence:other': 'assay component name',
        'microscope cover slip  22 m^2': 'assay component name',
        'miscellaneous': 'assay component name',
        'mm': 'assay component name',
        'moles-per-liter': 'assay component name',
        'negative log10 molar': 'assay component name',
        'ng/ml': 'assay component name',
        'nm': 'assay component name',
        'nmr': 'assay component name',
        'nucleic acid binding': 'assay component name',
        'number-per-liter': 'assay component name',
        'number-per-well': 'assay component name',
        'optical densitometry': 'assay component name',
        'oryctolagus cuniculus': 'assay component name',
        'phosphatase': 'assay component name',
        'pm': 'assay component name',
        'protease': 'assay component name',
        'radiometric': 'assay component name',
        'signal direction': 'assay component name',
        'spectramax plus 384 microplate reader': 'assay component name',
        'transcription factor': 'assay component name',
        'typhoon 8600 variable mode imager': 'assay component name',
        'uiu/ml': 'assay component name',
        'um': 'assay component name',
        'µm': 'assay component name']
*/

final Integer START_ROW = 3 //1-based
InputStream inp = new FileInputStream("C:/Users/gwalzer/Desktop/NCGC-DNA repair + comments.xlsx");

//Build assay-context (groups) and populate their attribute from the spreadsheet cell contents.
List<AssayContextDTO> assayContextList = parseSpreadsheetAndBuildAttributeGroups(inp, START_ROW, spreadsheetAssayContextGroups)

////print out to a file the raw grouped attributes (key/value pairs)
//out.withWriterAppend { writer ->
//    assayContextList.each {AssayContextDTO assayContextDTO ->
//        writer.writeLine("\nAID=${assayContextDTO.aid}; ${assayContextDTO.name}")
//        assayContextDTO.attributes.each {Attribute attribute ->
//            writer.writeLine("\t'${attribute.key}'/'${attribute.value}'; typeIn=${attribute.typeIn}; qualifier='${attribute.qualifier}' type=${attribute.attributeType}")
//        }
//    }
//}


List<AssayContextDTO> assayContextListCleaned = cleanAttributeContents(assayContextList, attributeNameMapping)

////Print out the cleaned grouped attributes (key/value pairs)
//out = new File('DnaSpreadsheetParsingCleaned' + '_' + System.currentTimeMillis() + '.txt')
//out.withWriterAppend { writer ->
//    assayContextListCleaned.each {AssayContextDTO assayContextDTO ->
//        writer.writeLine("\nAID=${assayContextDTO.aid}; ${assayContextDTO.name}")
//        assayContextDTO.attributes.each {Attribute attribute ->
//            writer.writeLine("\t'${attribute.key}'/'${attribute.value}'; typeIn=${attribute.typeIn}; qualifier='${attribute.qualifier}' type=${attribute.attributeType}")
//        }
//    }
//}


validateAttributeContentAgainstElementTable(assayContextListCleaned, attributeNameMapping)


validateAssayExistence(assayContextListCleaned)


createAndPersistAssayContexts(assayContextListCleaned)

return false
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
/**
 * Parses the input-stream spreadsheet and extracts all the values from the cells based on the list of attribute-groups.
 * an attribute group represents a group of key/value pairs (attributes) that should all be group together in a single assay or project context.
 *
 * @param inp
 * @param START_ROW
 * @param assayGroup
 * @return
 */
private List<AssayContextDTO> parseSpreadsheetAndBuildAttributeGroups(FileInputStream inp, int START_ROW, ArrayList<contextGroup> assayGroup) {
    Workbook wb = new XSSFWorkbook(inp);
    Sheet sheet = wb.getSheetAt(0);
    Integer aid
    Integer rowCount = 0 //rows in spreadsheet are zero-based
    List<AssayContextDTO> contextDtoList = []

    for (Row row : sheet) {
        if (rowCount < START_ROW - 1) {//skip the header rows
            rowCount++
            continue
        }

        //Get the current AID
        String aidFromCell = getCellContentByRowAndColumnIds(row, 'A')
        //if we encountered a new AID, update the current-aid with the new one. Else, leave the existing one.
        if (aidFromCell) {
            Integer newAid = new Integer(aidFromCell.trim())
            aid = newAid
        }
        assert aid, "Couldn't find AID"

        //Iterate over all assay-groups' contexts
        assayGroup.each {contextGroup contextGroup ->
            //Iterate over all the attribute-pairs in each context
            AssayContextDTO assayContextDTO = new AssayContextDTO()

            contextGroup.attributes.each {Attribute attribute ->
                //Get the attribute's key-content from cell
                String attKeyCellId = attribute.key
                def attrKey = getCellContent(attKeyCellId, row, sheet)
                if (attrKey)
                    assert attrKey instanceof String, "Key must be a string"

                //Get the attribute's qualifier-content from cell
                String attQualifierCellId = attribute.qualifier
                def attrQualifier = attQualifierCellId ? getCellContent(attQualifierCellId, row, sheet) : attQualifierCellId
                if (attrQualifier)
                    assert attrQualifier instanceof String, "Qualifier must be a string"

                //Get the attribute's value-content from cell
                String attValueCell = attribute.value
                def attrValue = getCellContent(attValueCell, row, sheet)

                if (!attrKey || (attribute.attributeType != AttributeType.Free) && !attrValue) return
                Attribute attr = new Attribute(attrKey, attrValue, attribute.attributeType, attribute.typeIn, attrQualifier)
                assayContextDTO.attributes << attr
            }
            if (assayContextDTO.attributes) {
                assayContextDTO.aid = aid
                assayContextDTO.name = contextGroup.name
                contextDtoList << assayContextDTO
            }
        }
    }

    return contextDtoList
}

/**
 * Extracts spreadsheet cell content given a cellId (e.g., '3/C' == row 3, column C (3))
 *
 * @param cellId
 * @param row - the default current-row (from the row-iterator)
 * @param sheet - the current sheet we are working on
 * @return Cell content; type varies based on CELL_CONTENT_TYPE in the spreadsheet
 */
private def getCellContent(String cellId, Row row, Sheet sheet) {
    if (!(cellId && row && sheet)) return null

    Row cellRow = getCellRowFromCellId(cellId, row, sheet)
    def content = getCellContentByRowAndColumnIds(cellRow, cellId.split('/')[1].trim())
    return content
}

/**
 * Get the correct Row based on the row-number in the cell-ID
 * If the cell's row-ID is '$', then we return the current row. Otherwise, get the cell from the current sheet.
 *
 * @param cellId
 * @param currentRow
 * @param currentSheet
 * @return
 */
private Row getCellRowFromCellId(String cellId, Row currentRow, Sheet currentSheet) {
    String rowString = cellId.split('/')[0]
    Row cellRow
    if (rowString == '$') {//replace $ with the current row
        cellRow = currentRow
    } else {
        Integer rowIndex = new Integer(rowString.trim())
        cellRow = currentSheet.getRow(rowIndex - 1)//spreadsheet is 1-based while the POI objects are zero-based
    }
    return cellRow
}

/**
 * Returns the cell content based on the CELL_TYPE
 *
 * @param row
 * @param columnString
 * @return
 */
def getCellContentByRowAndColumnIds(Row row, String columnString) {
    Cell cell = CellUtil.getCell(row, CellReference.convertColStringToIndex(columnString))

    switch (cell.getCellType()) {
        case Cell.CELL_TYPE_BLANK:
            return null;
            break;
        case Cell.CELL_TYPE_STRING:
            return cell.getRichStringCellValue().getString();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                return cell.getNumericCellValue();
            }
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            return cell.getBooleanCellValue();
            break;
        case Cell.CELL_TYPE_FORMULA:
            return cell.getCellFormula() as String;
            break;
        default:
            throw new RuntimeException("Error extracting cell content: ${cell}")
    }
}

/**
 * Clean up all the key/value pairs names to:
 * 1. remove the '| | |' prefix
 * 2. trim
 * 3. move to lower case
 * 4. convert to standard names based on the attributeNameMapping map
 * 5. Convert text field to numerical values where appropriate (e.g, '680 nm' --> 680, and the 'nm' part is discarded)
 */
private List<AssayContextDTO> cleanAttributeContents(List<AssayContextDTO> assayContextList, Map attributeNameMapping) {
    List<AssayContextDTO> assayContextListCleaned = []
    assayContextList.each {AssayContextDTO assayContextDTO ->
        AssayContextDTO assayCtxDTO = new AssayContextDTO()
        assayCtxDTO.name = assayContextDTO.name
        assayCtxDTO.aid = assayContextDTO.aid
        assayContextDTO.attributes.each {Attribute attribute ->
            String ky = StringUtils.split(attribute.key, '|').toList().last().trim().toLowerCase()
            ky = attributeNameMapping.containsKey(ky) ? attributeNameMapping.get(ky) : ky

            def val = attribute.value
            if (attribute.value instanceof String) {
                String valStr = StringUtils.split(attribute.value as String, '|').toList().last().trim().toLowerCase()
                valStr = attributeNameMapping.containsKey(valStr) ? attributeNameMapping.get(valStr) : valStr
                //if val could be number value, replace it ('650 nM' --> 650)
                val = (valStr && valStr.split()[0].isNumber()) ? new BigDecimal(valStr.split()[0]) : valStr
            }

            if ((attribute.attributeType != AttributeType.Free) && !val) return //Unless the attribute-type is Free, skip attributes with empty value
            Attribute attr = new Attribute(attribute)
            attr.key = ky
            attr.value = val
            assayCtxDTO.attributes << attr
        }
        assayContextListCleaned << assayCtxDTO
    }
    assayContextListCleaned
}

/**
 * Make sure that all the attribute's key and value match against valid values in the Element table
 *
 * @param assayContextList
 */
private void validateAttributeContentAgainstElementTable(List<AssayContextDTO> assayContextList, Map attributeNameMapping) {
//Move all the attributes into a sorted-set to search against the database
    SortedSet<String> attributeVocabulary = [] as SortedSet
    assayContextList.each {AssayContextDTO assayContextDTO ->
        assayContextDTO.attributes.each {Attribute attribute ->
            //Add all keys
            attributeVocabulary.add(attribute.key.toLowerCase())
            //Add all the values, except for the ones that are numeric values or a type-in field or a Free-type field
            if (attribute.value &&
                    (attribute.value instanceof String) &&
                    !attribute.typeIn &&
                    (attribute.attributeType != AttributeType.Free)) {
                attributeVocabulary.add(attribute.value.toLowerCase())
            }
        }
    }

    List<Element> foundElements = []
    List<String> missingAttributes = []
    attributeVocabulary.each {String attr ->
//    println("Attribute: '${attr}'")
        //Swap the attribute name with the mapping we have (e.g., '[detector] assay component (type in)' --> 'assay component'
        attr = attributeNameMapping.containsKey(attr) ? attributeNameMapping.get(attr) : attr
        Element element = Element.findByLabelIlike(attr)
//    println("Element: ${element}")
        if (element) {
            foundElements << element
        }
        else {
            missingAttributes << attr
        }
    }


    println("Found elements: ${foundElements.collect {Element element -> [element.id, element.label]}}")
    println("Missing attributes: ${missingAttributes}")
    assert missingAttributes.isEmpty(), "We could not have missing attributes - all attributes should be validatied against the Element table"
}

/**
 * Make sure all the aid map to valid Assay IDs
 *
 * @param assayContextListCleaned
 */
private void validateAssayExistence(List<AssayContextDTO> assayContextListCleaned) {
//Build aid-to-AssayId mapping and validate that all aid exist
    Map<Long, Assay> aidToAssayMap = [:]
    assayContextListCleaned*.aid.unique().each { Long AID ->
        Assay assay = getAssayFromAid(AID)
//        assert assay, "Could not find an Assay that is associated with aid ${AID}"
        aidToAssayMap.put(AID, assay)
    }

    Map<Long, Assay> failedMapping = aidToAssayMap.findAll {aid, assay -> !assay}
//    assert failedMapping.isEmpty(), "There must be only one and only one AssayId for each aid. Some aids are not associated with an assay: ${failedMapping.keySet()}"
}

/**
 * Finds an Assay that is associated with an AID.
 * If no assay found, or more than one assay are associated with a single aid, return null.
 *
 * @param AID
 * @return
 */
private Assay getAssayFromAid(long AID) {
    def criteria = Assay.createCriteria()
    List<Assay> results = criteria.list {
        experiments {
            externalReferences {
                eq('extAssayRef', "aid=${AID.toString()}")
            }
        }
    }

    return (results && (results.size() == 1)) ? results.first() : null
}

/**
 * Creates and persists AssayContexts and AssayContextItems from the group of attributes we created earlier.
 * 1. Attribute key are taken always from Element (AssayContextItem.attributeElement), unless it's a qualifier attribute.
 * 2. Attribute value is taken from:
 *  i. if an element exist, use that and update AssayContextItem.valueElement
 *  ii. if the value is a numeric value, update valueNum
 *  iii. if the value is type-in, update valueDisplay only
 *  iv. if the attribute-type is Free, accept empty values
 *  v. otherwise, throw an error
 * 3. Always update valueDisplay
 * 4. If the attribute is a qualifier, update the qualifier field
 *
 * @param assayContextListCleaned
 * @return
 */
private List<AssayContextDTO> createAndPersistAssayContexts(List<AssayContextDTO> assayContextListCleaned) {
//    def out = new File('DnaSpreadsheetParsingAssayContextValidationErrors' + '_' + System.currentTimeMillis() + '.txt')
//    out.withWriterAppend { writer ->
    assayContextListCleaned.each { AssayContextDTO assayContextDTO ->
        AssayContext.withTransaction { TransactionStatus status ->
            //create the assay-context
            AssayContext assayContext = new AssayContext()
            assayContext.assay = getAssayFromAid(assayContextDTO.aid)
            //TODO DELETE DELETE DELETE the following line should be deleted once all assays have been uploaded to CAP
            if (!assayContext.assay) return //skip this assay context
            assayContext.contextName = assayContextDTO.name

            //create the assay-context-item and add them to assay-context
            assayContextDTO.attributes.each { Attribute attribute ->
                AssayContextItem assayContextItem = new AssayContextItem()
                assayContextItem.assayContext = assayContext
                assayContextItem.attributeType = attribute.attributeType
                //populate the attribute key's element
                Element element = Element.findByLabelIlike(attribute.key)
                assert element, "We must have an element for the assay-context-item attribute"
                assayContextItem.attributeElement = element

                //populate attribute-value type and value
                element = attribute.value ? Element.findByLabelIlike(attribute.value) : null
                if (element) {//if the value string could be matched against an element then add it to the valueElement
                    assayContextItem.valueElement = element
                    assayContextItem.valueDisplay = element.label
                } else if (attribute.value && (!(attribute.value instanceof String) || attribute.value.isNumber())) {//if the attribute's value is a number value, store it in the valueNum field
                    Float val = new Float(attribute.value)
                    assayContextItem.valueNum = val
                    assayContextItem.valueDisplay = val.toString()
                } else if (attribute.typeIn || (attribute.attributeType == AttributeType.Free)) {//if the attribute's value is a type-in or attribute-type is Free, then simply store it the valueDisplay field
                    assayContextItem.valueDisplay = attribute.value
                } else {
                    //throw an error
                    println("Illage attribute value: '${attribute.key}'/'${attribute.value}'")
                    assert false, "Illage attribute value"
                }

                //populate the qualifier field, if exists
                if (attribute.qualifier) {
                    assayContextItem.qualifier = String.format('%-2s', attribute.qualifier)
                }

                assayContext.addToAssayContextItems(assayContextItem)
            }

            assayContext.save()
            if (assayContext.hasErrors()) {
                println("AssayContext errors")
                writer.writeLine("AssayContext Errors: ${assayContext.errors}")
            } else {
                println("Assay ID: ${assayContext.assay.id}")
                println("ContextName: ${assayContext.contextName}")
                assayContext.assayContextItems.each { AssayContextItem assayContextItem ->
                    println("new attribute")
                    println("\tAttributeElement: '${assayContextItem?.attributeElement?.label}'")
                    println("\tValueElement: '${assayContextItem?.valueElement?.label}'")
                    println("\tAttributeType: '${assayContextItem?.attributeType}'")
                    println("\tValueDisplay: '${assayContextItem?.valueDisplay}'")
                    println("\tValueNum: '${assayContextItem?.valueNum}'")
                    println("\tQualifier: '${assayContextItem?.qualifier}'")
                }
            }

            //comment out to commit the transaction
            //status.setRollbackOnly()
        }
    }
//    }
}

/**
 * Holds a single attribute (a key/value pair) value.
 * This should correspond to an Element in the data model
 */
class Attribute {
    String key;
    def value;
    AttributeType attributeType
    Boolean typeIn //defines whether or not the user can type-in a value for the Value field or does it have to come from the dictionary
    String qualifier //Used to describe the qualifier in result-type (e.g., '<')

    public Attribute(String key, def value, AttributeType attributeType, Boolean typeIn = false, String qualifier = null) {
        this.key = key
        this.value = value
        this.attributeType = attributeType
        this.typeIn = typeIn
        this.qualifier = qualifier
    }

    public Attribute(Attribute attribute) {
        this.key = attribute.key
        this.value = attribute.value
        this.attributeType = attribute.attributeType
        this.typeIn = attribute.typeIn
        this.qualifier = attribute.qualifier
    }
}

/**
 * Corresponds to an assay-context element that usually groups together few attributes
 */
class contextGroup {
    String name;
    List<Attribute> attributes = [];
}

class AssayContextDTO extends contextGroup {
    Long aid
}