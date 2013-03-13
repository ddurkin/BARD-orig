package bardqueryapi.compoundBioActivitySummary

import bard.core.adapter.AssayAdapter
import bard.core.adapter.ProjectAdapter
import org.apache.commons.lang3.tuple.ImmutablePair
import org.apache.commons.lang3.tuple.Pair
import org.apache.log4j.Logger
import bard.core.rest.spring.experiment.*
import bardqueryapi.*

/**
 * Created with IntelliJ IDEA.
 * User: gwalzer
 * Date: 3/6/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
class CompoundBioActivitySummaryBuilder {
    IQueryService queryService
    static final Logger log = Logger.getLogger(CompoundBioActivitySummaryBuilder.class)

    CompoundBioActivitySummaryBuilder(IQueryService queryService) {
        this.queryService = queryService
    }

    public List<TableModel> buildModel(GroupByTypes groupByType, Map groupedByExperimentalData) {// Map<ADID/PID, List<Activity>>
        TableModel tableModel = new TableModel()

        //Create a list rows, each row represents a collection of experiments grouped by a resource (assay or project)
        for (Long resourceId in groupedByExperimentalData.keySet()) {
            List<WebQueryValue> singleRowData = []

            //The first cell (column) is the resource (assay or a project)
            WebQueryValue resource = findResourceByTypeAndId(groupByType, resourceId)
            if (!resource) {
                log.error("Could not map resource ${groupByType}: ${resourceId}")
                continue
            }
            singleRowData << resource

            List<Activity> exptDataList = groupedByExperimentalData[resourceId]
            //For each grouped-by resource item (assay/project), add all the grouped-by experimental data, converting result types into the appropriate value-types.
            //Use:
            // 1. A list of 'box' maps to collect all the experiments data, grouped by experiment (experiment being the key)
            // 2. A map for each experiment-level data, where the experiment is the key and the results are in a list
            // 3. A list of result type values (curves, single-points, etc.)
            for (Activity exptData in exptDataList) {
                String key = exptData.exptDataId //e.g., "14359.26749233"
                //Represents an experiment box in the Compound Bio Activity Summary panel. Each 'box' includes
                // an experiment summary title and then a list of WebQueryValue result types (curves, single-points, etc.)
                Map<WebQueryValue, List<WebQueryValue>> experimentBox = [:]
                //Add the experiment itself (for an experiment's description title)
                ExperimentShow experimentShow
                try {
                    experimentShow = this.queryService.experimentRestService.getExperimentById(exptData.bardExptId)
                }
                catch (Exception exp) {
                    log.error("Could not find BARD experiment ID: ${exptData.bardExptId}")
                }
                WebQueryValue experiment = new ExperimentValue(value: experimentShow)

                List<WebQueryValue> results = convertExperimentResultsToValues(exptData)

                experimentBox.put(experiment, results)
                //Cast the experimentBox to a MapValue type
                MapValue experimentBoxValue = new MapValue(value: experimentBox)
                //Add the experimnet box to the row's list of value
                singleRowData << experimentBoxValue
            }
            //Cast the single-row list into a ListValue and store in the table
            ListValue rowValue = new ListValue(value: singleRowData)
            tableModel.data << rowValue
        }
        return tableModel
    }


    WebQueryValue findResourceByTypeAndId(GroupByTypes groupByType, Long resourceId) {
        WebQueryValue resource

        switch (groupByType) {
            case GroupByTypes.ASSAY:
                List<AssayAdapter> assayAdapters
                try {
                    assayAdapters = queryService.findAssaysByADIDs([resourceId]).assayAdapters
                }
                catch (Exception exp) {
                    log.error("Could not find Assay: ${resourceId}")
                    return  resource
                }
                if (assayAdapters.size() == 1) {
                    resource = new AssayValue(value: assayAdapters.first())
                } else {
                    Log.error("Could not find Assay with ADID=${resourceId}")
                    resource = new AssayValue()
                }
                break;
            case GroupByTypes.PROJECT:
                List<ProjectAdapter> projectAdapters
                try {
                    projectAdapters = queryService.findProjectsByPIDs([resourceId]).projectAdapters
                }
                catch (Exception exp) {
                    Log.error("Could not find Project: ${resourceId}")
                    return  resource
                }
                if (projectAdapters.size() == 1) {
                    resource = new ProjectValue(value: projectAdapters.first())
                } else {
                    Log.error("Could not find Project with PID=${resourceId}")
                    resource = new ProjectValue()
                }
                break;
            default:
                throw new RuntimeException("Group-by type ${groupByType} is not supported")
        }

        return resource
    }

    /**
     * Map an experiment (exptData) into a list of table-model 'Values'.
     * The main result data is in the experiment's priority elements.
     * @param exptData
     * @return
     */
    List<WebQueryValue> convertExperimentResultsToValues(Activity exptData) {
        List<WebQueryValue> values = []
        ResponseClassEnum responseClass = ResponseClassEnum.toEnum(exptData.resultData.responseClass)

        for (PriorityElement priorityElement in exptData.resultData.priorityElements) {
            switch (responseClass) {
                case ResponseClassEnum.SP:
                    //The result-type is a single-point, key/value pair.
                    Pair<String, String> pair = new ImmutablePair<String, String>(priorityElement.pubChemDisplayName, priorityElement.value)
                    PairValue pairValue = new PairValue(value: pair)
                    values << pairValue
                    break;
                case ResponseClassEnum.CR_SER:
                    //the result type is a curve.
                    if (priorityElement.concentrationResponseSeries) {
                        //Add the concentration/value series
                        ConcentrationResponseSeries concentrationResponseSeries = priorityElement.concentrationResponseSeries
                        List<ConcentrationResponsePoint> concentrationResponsePoints = concentrationResponseSeries.concentrationResponsePoints
                        ActivityConcentrationMap doseResponsePointsMap = ConcentrationResponseSeries.toDoseResponsePoints(concentrationResponsePoints)
                        CurveFitParameters curveFitParameters = concentrationResponseSeries.curveFitParameters
                        ConcentrationResponseSeriesValue concentrationResponseSeriesValue = new ConcentrationResponseSeriesValue(value: doseResponsePointsMap, title: priorityElement.pubChemDisplayName)
                        values << concentrationResponseSeriesValue
                    }
                    break;
                default:
                    log.info("Response-class not supported: ${responseClass}")
            }
        }

        return values
    }
}
