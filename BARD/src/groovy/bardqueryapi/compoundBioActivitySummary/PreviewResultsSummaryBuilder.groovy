package bardqueryapi.compoundBioActivitySummary

import bard.core.rest.spring.experiment.ActivityConcentrationMap
import bard.core.rest.spring.experiment.CurveFitParameters
import bard.db.dictionary.Element
import bard.db.dictionary.ResultTypeTree
import bard.db.experiment.JsonResult
import bard.db.experiment.JsonResultContextItem
import bardqueryapi.*
import org.apache.commons.lang3.tuple.ImmutablePair
import org.apache.commons.lang3.tuple.Pair
import org.apache.log4j.Logger

/**
 * TODO: START WARNING!!!!!
 * NOTE THAT THIS CLASS SHOULD BE REFACTORED TO READ THE RESULT TYPE HIERARCHY
 * FROM THE RESULT_TYPE_TREE IN CAP.
 * RIGHT NOW SOME OF THE DICTIONARY IDs ARE HARD-CODED AND IF THAT CHANGES THIS FUNCTIONALITY WILL BREAK
 * ALSO NEWLY ADDED RESULT TYPES MAY NOT BE ACCOUNTED FOR. END WARNING!!!!!
 *
 * Created with IntelliJ IDEA.
 * User: jasiedu
 *
 */
class PreviewResultsSummaryBuilder {
    //Local cache, for mapping result type ids to the ResultType tree. We should consider making it application wide

    private Map<Long, ResultTypeTree> resultTypeTreeCache = [:]
    static final Logger log = Logger.getLogger(PreviewResultsSummaryBuilder.class)

    final static String PUBCHEM_OUTCOME_DICT_URI = "http://www.bard.nih.gov/ontology/bard#BARD_0000998"

    final static List<Long> SCREENING_CONCENTRATION = [
            new Long(1943),
            new Long(1948),
            new Long(1949),
            new Long(1950),
            new Long(971)
    ]
    /**
     * So called high priority elements
     */
    private static List<Long> HIGH_PRIORITY_DICT_ELEM = [
            new Long(917),
            new Long(952),
            new Long(956),
            new Long(959),
            new Long(961),
            new Long(963),
            new Long(964),
            new Long(965),
            new Long(966),
            new Long(983),
            new Long(984),
            new Long(993),
            new Long(997),
            new Long(1000),
            new Long(1002),
            new Long(1008),
            new Long(1015),
            new Long(1378),
            new Long(1387)
    ]

    /**
     * Whether this result type represents a dose response curve parameter
     * @param resultTypeId
     * @return true/false
     *
     *  We assume that if you have a concentration-response curve or concentration end point in your path
     *  you are a curve fit parameter. This is not always true for concentration endpoint (for e.g logEC50),
     *  but in that case the context in which it is being used helps
     */
    protected boolean isCurveFitParameter(final Long resultTypeId) {
        ResultTypeTree resultTypeTree = addOrFindResultTypeTreeToCache(resultTypeId)
        if (resultTypeTree) {
            if (resultTypeTree.fullPath.toUpperCase().contains("CONCENTRATION-RESPONSE CURVE")) {
                return true
            }
        }
        return false
    }
    /**
     * Lets find the ResultTypeTree from the cache, if it does not exists lets get it from the database
     * and add it to the local cache
     * @param resultTypeId
     * @return
     */
    protected ResultTypeTree addOrFindResultTypeTreeToCache(final Long resultTypeId) {
        ResultTypeTree resultTypeTree = this.resultTypeTreeCache.get(resultTypeId)
        if (!resultTypeTree) {
            Element element = Element.get(resultTypeId)
            if (element) {
                resultTypeTree = ResultTypeTree.findByElementAndLeaf(element, true)
                this.resultTypeTreeCache.put(resultTypeId, resultTypeTree)
            }
        }
        return resultTypeTree
    }
    /**
     * If this resultTypeId references a pubchem outcome.
     * @param resultTypeId
     * @return true/false
     */
    protected boolean isPubChemOutcome(Long resultTypeId) {
        ResultTypeTree resultTypeTree = addOrFindResultTypeTreeToCache(resultTypeId)
        if (resultTypeTree) {
            if (resultTypeTree.label.toUpperCase().contains("PUBCHEM OUTCOME")) {
                return true
            }
        }
        return false
    }
    /**
     * Here we only look for things with 'percent response' or 'percent endpoint' in its path.
     * NOTE that this is not a scaleable solution at all.
     *
     * We are thinking of changing the model so that the jsonresult object contains enough information for
     * us to not have to resort to this hack. However, this should work for most cases, since most pubchem assays
     * fits this hack
     * @param resultTypeId
     * @return true/false
     */
    protected boolean isPercentResponse(Long resultTypeId) {
        ResultTypeTree resultTypeTree = addOrFindResultTypeTreeToCache(resultTypeId)
        if (resultTypeTree) {
            if (resultTypeTree.fullPath.toUpperCase().contains("RESPONSE ENDPOINT") &&
                    resultTypeTree.fullPath.toUpperCase().contains("PERCENT RESPONSE")) {
                return true
            }
        }
        return false
    }
    // http://www.bard.nih.gov/ontology/bard#BARD_0000998
    protected boolean isPubChemActivityScore(Long resultTypeId) {
        Element element = Element.get(resultTypeId)
        if (PUBCHEM_OUTCOME_DICT_URI.equals(element?.bardURI)) {
            return true
        }
        return false
    }
    /**
     * Set the curve fit parameters
     * Note that if we were to add a parameter that is not accounted for here, it will
     * we can likely not fit the curve
     * @param curveFitParameters
     * @param resultTypeTree
     * @param jsonResult
     */
    protected void setCurveFitParameters(CurveFitParameters curveFitParameters, ResultTypeTree resultTypeTree, JsonResult jsonResult) {
        switch (resultTypeTree.label.toUpperCase()) {
            case "HILL COEFFICIENT":
                curveFitParameters.setHillCoef(jsonResult.valueNum)
                break
            case "HILL S0":
                curveFitParameters.setS0(jsonResult.valueNum)
                break
            case "HILL SINF":
                curveFitParameters.setSInf(jsonResult.valueNum)
                break
            default: //We call everything else logEc50. Need to fix this. This is actually the slope
                curveFitParameters.setLogEc50(jsonResult.valueNum)
                break
        }
    }
    /**
     * Convert the list of jsonResults to Dose Response Curve points
     * @param jsonResults
     * @return
     */
    protected Map convertToDoseResponse(List<JsonResult> jsonResults, final Set<String> priorityElements,
                                        final Set<StringValue> priorityElementValues) {

        List<Double> concentrations = []
        List<Double> activities = []
        List<WebQueryValue> childElements = []
        CurveFitParameters curveFitParameters = new CurveFitParameters()
        String responseUnits = ""
        String concentrationUnits = ""
        for (JsonResult jsonResult : jsonResults) {
            final Long resultTypeId = jsonResult.resultTypeId
            String resultType = jsonResult.resultType

            if (priorityElements.contains(resultType?.trim())) {
                priorityElementValues.add(new StringValue(value: jsonResult.valueDisplay))
            }
            if (isCurveFitParameter(resultTypeId)) {
                setCurveFitParameters(curveFitParameters, resultTypeTreeCache.get(resultTypeId), jsonResult)
            } else if (jsonResult.valueNum && jsonResult.contextItems) {
                final float activity = jsonResult.valueNum
                final List<JsonResultContextItem> items = jsonResult.getContextItems()

                for (JsonResultContextItem resultContextItem : items) {
                    if (SCREENING_CONCENTRATION.contains(resultContextItem.attributeId)) { //this is a screening concentration
                        if (!responseUnits) {
                            responseUnits = jsonResult.resultType
                        }
                        final float concentration = resultContextItem.valueNum
                        //get concentration units
                        String valueDisplay = resultContextItem.valueDisplay
                        if (!concentrationUnits) {
                            concentrationUnits = parseUnits(valueDisplay)
                        }

                        activities.add(activity.doubleValue())
                        concentrations.add(concentration.doubleValue())
                    } else {
                        //this is a child element                               qualifier
                        childElements << contextItemToChildElement(resultContextItem)
                    }
                }
                if (jsonResult.related) {
                    handleRelatedElements(jsonResult, childElements)
                }
            } else {
                childElements << new StringValue(value: jsonResult.resultType + ":" + jsonResult.valueDisplay)
            }

        }
        if (activities && concentrations) {
            return [
                    activityConcentrationMap: new ActivityConcentrationMap(activities: activities, concentrations: concentrations),
                    curveFitParameters: curveFitParameters, responseUnit: responseUnits,
                    testConcentrationUnit: concentrationUnits,
                    childElements: childElements
            ]
        }
        return [
                activityConcentrationMap: null,
                curveFitParameters: null,
                responseUnit: responseUnits,
                testConcentrationUnit: concentrationUnits,
                childElements: childElements
        ]

    }

    void handleRelatedElements(JsonResult jsonResult, List<WebQueryValue> childElements) {
        for (JsonResult relatedElement : jsonResult.related) {
            String display = relatedElement.valueDisplay
            String resultType = relatedElement.resultType
            childElements << new StringValue(value: resultType + ":" + display)

            if (relatedElement.contextItems) {
                childElements << contextItemsToChildElements(relatedElement.contextItems)
            }
        }
    }

    protected ConcentrationResponseSeriesValue handleConcentrationResponsePoints(final JsonResult jsonResult, final Set<String> priorityElements,
                                                                                 final Set<StringValue> priorityElementValues,
                                                                                 List<WebQueryValue> childElements = [], Double yNormMin = null,
                                                                                 Double yNormMax = null) {
        final Long dictionaryId = jsonResult.resultTypeId
        Pair<StringValue, StringValue> title =
            new ImmutablePair<StringValue, StringValue>(new StringValue(value: jsonResult.resultType), new StringValue(value: jsonResult.valueNum))
        LinkValue dictionaryElement

        if (dictionaryId) {
            dictionaryElement = new LinkValue(value: "/BARD/dictionaryTerms/#${jsonResult.resultTypeId}")
        }


        String qualifier = jsonResult.qualifier ?: ""

        Map concentrationResponseMap = convertToDoseResponse(jsonResult.related, priorityElements, priorityElementValues)
        childElements << concentrationResponseMap.childElements
        ActivityConcentrationMap doseResponsePointsMap = concentrationResponseMap.activityConcentrationMap
        if (doseResponsePointsMap) {
            CurveFitParameters curveFitParameters = concentrationResponseMap.curveFitParameters
            String responseUnits = concentrationResponseMap.responseUnit
            String concentrationUnits = concentrationResponseMap.testConcentrationUnit

            ConcentrationResponseSeriesValue concentrationResponseSeriesValue = new ConcentrationResponseSeriesValue(value: doseResponsePointsMap,
                    title: new PairValue(value: title, dictionaryElement: dictionaryElement),
                    curveFitParameters: curveFitParameters,
                    slope: jsonResult.valueNum,
                    responseUnit: responseUnits,
                    testConcentrationUnit: concentrationUnits,
                    qualifier: qualifier,
                    yNormMin: yNormMin,
                    yNormMax: yNormMax)
            concentrationResponseSeriesValue.yAxisLabel = responseUnits
            concentrationResponseSeriesValue.xAxisLabel = concentrationUnits ? "Concentration (log [${concentrationUnits}])" : ""
            return concentrationResponseSeriesValue
        }
        //add context items , they are child elements

        return null

    }

    protected void processJsonResults(final JsonResult jsonResult,
                                      final Set<String> priorityElements,
                                      final Set<StringValue> priorityElementValues,
                                      List<WebQueryValue> childElements = [],
                                      List<WebQueryValue> values = [],
                                      Double yNormMin = null,
                                      Double yNormMax = null) {

        final Long resultTypeId = jsonResult.resultTypeId
        final String resultType = jsonResult.resultType
        //if it has a qualifier then do not try to fit the curve
        if (priorityElements.contains(resultType.trim())) {
            priorityElementValues.add(new StringValue(value: resultType + ":" + jsonResult.valueDisplay))
        }

        if (HIGH_PRIORITY_DICT_ELEM.contains(resultTypeId)) {
            String qualifier = jsonResult.qualifier ?: ""

            //if this has no child nodes then it cannot be a dose
            if (!jsonResult.related) {
                childElements << new StringValue(value: resultType + ":" + jsonResult.valueDisplay)
            } else {
                    final ConcentrationResponseSeriesValue concentrationResponsePoints = handleConcentrationResponsePoints(jsonResult, priorityElements, priorityElementValues, childElements, yNormMin, yNormMax)
                    if (concentrationResponsePoints) {
                        values << concentrationResponsePoints
                    }else{
                        if (!priorityElements.contains(resultType.trim())) {
                            childElements << new StringValue(value: resultType + ":" + jsonResult.valueDisplay)
                        }
                        handleRelatedElements(jsonResult,childElements)
                    }
               // }
            }
        } else if (isPercentResponse(resultTypeId)) {
            values << handleEfficacyMeasures(jsonResult)
        } else {  //everything else is a child element
            childElements << new StringValue(value: resultType + ":" + jsonResult.valueDisplay)
        }
    }

    protected PairValue handleEfficacyMeasures(final JsonResult jsonResult) {
        final Long dictionaryId = jsonResult.resultTypeId

        Pair<StringValue, StringValue> pair =
            new ImmutablePair<StringValue, StringValue>(new StringValue(value: jsonResult.resultType), new StringValue(value: jsonResult.valueDisplay))
        LinkValue dictionaryElement

        if (dictionaryId) {
            dictionaryElement = new LinkValue(value: "/BARD/dictionaryTerms/#${jsonResult.resultTypeId}")
        }
        return new PairValue(value: pair, dictionaryElement: dictionaryElement)
    }

    protected List<WebQueryValue> contextItemsToChildElements(List<JsonResultContextItem> jsonResultContextItems) {
        List<WebQueryValue> childElements = []
        for (JsonResultContextItem jsonResultContextItem : jsonResultContextItems) {
            childElements << contextItemToChildElement(jsonResultContextItem)
        }
        return childElements
    }

    protected StringValue contextItemToChildElement(JsonResultContextItem jsonResultContextItem) {
        return new StringValue(value: jsonResultContextItem.attribute + ":" + jsonResultContextItem.valueDisplay)
    }
    /**
     *
     * @param rootElements
     * @param yNormMin
     * @param yNormMax
     * @return
     */
    public Map convertExperimentResultsToTableModelCellsAndRows(List<JsonResult> rootElements,
                                                                Set<String> priorityElements,
                                                                Double yNormMin = null,
                                                                Double yNormMax = null) {
        List<WebQueryValue> values = []
        List<WebQueryValue> childElements = []
        StringValue outcome = null
        Set<StringValue> priorityElementValues = [] as Set<StringValue>
        for (JsonResult jsonResult : rootElements) {
            final long resultTypeId = jsonResult.resultTypeId
            String display = jsonResult.valueDisplay
            String resultType = jsonResult.resultType
            if (priorityElements.contains(resultType.trim())) {
                priorityElementValues.add(new StringValue(value: resultType + ":" + display))
            }
            if (isPubChemOutcome(resultTypeId)) { //what we actually need is everything at the root of the measures tree
                // that is not a Concentration/response endpoint so that we can display it in a column
                outcome = new StringValue(value: display)

                for (JsonResult relatedElement : jsonResult.related) {
                    display = relatedElement.valueDisplay
                    resultType = relatedElement.resultType
                    if (priorityElements.contains(resultType.trim())) {
                        priorityElementValues.add(new StringValue(value: resultType + ":" + display))
                    }
                    if (isPubChemActivityScore(relatedElement.resultTypeId)) {
                        childElements << new StringValue(value: relatedElement.resultType + ":" + relatedElement.valueDisplay)
                    } else {
                        processJsonResults(relatedElement, priorityElements, priorityElementValues, childElements, values, yNormMin, yNormMax)
                    }
                    if (relatedElement.contextItems) {
                        childElements << contextItemsToChildElements(relatedElement.contextItems)
                    }
                }
            } else if (isPubChemActivityScore(resultTypeId)) {
                childElements << new StringValue(value: resultType + ":" + display)
            } else {

                processJsonResults(jsonResult, priorityElements, priorityElementValues, childElements, values, yNormMin, yNormMax)
                if (jsonResult.contextItems) {
                    childElements << contextItemsToChildElements(jsonResult.contextItems)
                }
            }
        }
        final List<WebQueryValue> sortedValues = sortWebQueryValues(values)
        return [
                experimentalvalues: sortedValues,
                outcome: outcome,
                childElements: childElements,
                priorityElements: priorityElementValues
        ]
    }
    /**
     *
     * extract the units from a given string. Assume that the units are space separated from the value
     *
     */
    protected String parseUnits(String displayValue) {
        final String[] split = displayValue.trim().split(" ")
        if (split.length > 1) {
            return split[1].trim()
        }
        return ""

    }

    protected List<WebQueryValue> sortWebQueryValues(List<WebQueryValue> unsortedValues) {
        return unsortedValues.sort { WebQueryValue valueInst ->
            //Sort based on the WebQueryValue specific type
            switch (valueInst.class.simpleName) {
                case ConcentrationResponseSeriesValue.class.simpleName:
                    return 1
                    break;
                case ListValue.class.simpleName:
                    return 2
                    break;
                case PairValue.class.simpleName:
                    return 3
                    break;
                case StringValue.class.simpleName:
                    return 4
                    break;
                default:
                    return 99
            }
        }
    }
}
