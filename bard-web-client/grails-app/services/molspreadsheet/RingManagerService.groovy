package molspreadsheet

import bard.core.rest.spring.AssayRestService
import bard.core.rest.spring.BiologyRestService
import bard.core.rest.spring.CompoundRestService
import bard.core.rest.spring.SunburstCacheService
import bard.core.rest.spring.assays.Assay
import bard.core.rest.spring.assays.BardAnnotation
import bard.core.rest.spring.biology.BiologyEntity
import bard.core.rest.spring.compounds.CompoundSummary
import bard.core.rest.spring.compounds.TargetClassInfo
import bard.core.rest.spring.experiment.Activity
import bard.core.rest.spring.util.RingNode
import groovy.json.JsonBuilder
import org.apache.log4j.Level
import org.apache.log4j.Logger

class RingManagerService {
    CompoundRestService compoundRestService
    SunburstCacheService sunburstCacheService
    AssayRestService assayRestService
    BiologyRestService biologyRestService

    static Logger log
    static {
        this.log = Logger.getLogger(RingManagerService.class)
        log.setLevel(Level.ERROR)
    }



    /***
     * Retrieve all the data we need to build a linked visualization based on multiple calls
     * to the annotation data on a per assay basis. The return value will have one key for
     * each assay, and then each assay will have multiple keyvalue pairs containing
     * the information we came for. Those values in the inner map may have a multiplicity
     * greater than one, so let's just return a list to be safe.
     *
     * @param aidList
     * @return
     */
    LinkedHashMap<Long, LinkedHashMap <String,List<String>>>  getLinkedAnnotationData ( List <Long> aidList )  {
        LinkedHashMap<Long, LinkedHashMap <String,List<String>>> returnData = [:]
        if (aidList) {
            for(Long aid in aidList){
                BardAnnotation bardAnnotation = assayRestService.findAnnotations(aid)
                LinkedHashMap <String,List<String>> mapForThisAssay = [:]
                if (bardAnnotation)  {
                    String keyTerm =  "GO biological process term"
                    mapForThisAssay [keyTerm.replaceAll(/\s/,"_")] =  bardAnnotation.contexts*.contextItems.flatten().findAll{it->it.key==keyTerm}.display
                    keyTerm =  "assay format"
                    mapForThisAssay [keyTerm.replaceAll(/\s/,"_")] =  bardAnnotation.contexts*.contextItems.flatten().findAll{it->it.key==keyTerm}.value
                    keyTerm =  "assay type"
                    mapForThisAssay [keyTerm.replaceAll(/\s/,"_")] =  bardAnnotation.contexts*.contextItems.flatten().findAll{it->it.key==keyTerm}.value
                }
                returnData[aid]  =  mapForThisAssay
            }
        }
        returnData
    }

    /***
     * Unfortunately we have to get the target information from elsewhere and combine it with the linked annotation data.
     * @param annotationData
     * @param compoundSummary
     */
    void  combineLinkedAnnotationDataWithTargetInformation ( LinkedHashMap<Long, LinkedHashMap <String,List<String>>> annotationData, CompoundSummary compoundSummary )  {
        // Since we currently have no target data we can safely leave this method as a no-op for now.
        return;
    }


    String  convertDataIntoJson ( LinkedHashMap<Long, LinkedHashMap <String,List<String>>> annotationData )  {
        // Since we currently have no target data we can safely leave this method as a no-op for now.
        JsonBuilder jsonBuilder = new  JsonBuilder( annotationData )
        jsonBuilder.toPrettyString()
    }






    String writeRingTree( RingNode ringNode, boolean includeText, int typeOfColoring = 0 ) {
        if (typeOfColoring  == 2) {
            ringNode = ringNode //We will need to modify the tree
        }
        StringBuilder stringBuilder = new StringBuilder("var \$data = [")
        if (ringNode) {
               if (includeText) {
                   stringBuilder << ringNode.toString()
               } else {
                   stringBuilder << ringNode.toStringNoText()
               }
        }
        stringBuilder << "]"

        stringBuilder.toString()
    }


    Boolean classDataExistsForThisCompound (CompoundSummary compoundSummary){
        Boolean returnValue = false
        if (compoundSummary != null){
            for (Assay assay in compoundSummary.testedAssays) {
                if (assay.targetIds?.size()  > 0)  {  // At least one assay has at least one target -- better make a sunburst
                    returnValue = true
                    break
                }
            }
        }
        return returnValue
    }


    void retrievedTargetsAndBiologicalProcesses (List<String> currentTargets,
                                                 List<String>  currentExperimentIds,
                                                 CompoundSummary compoundSummary,
                                                 LinkedHashMap<String, Object> returnValue,
                                                 String assayFormat,
                                                 String assayType )  {
        for (String currentExperimentId in currentExperimentIds) {
            Long  experimentIdAsLong
            try {
                experimentIdAsLong = Long.parseLong(currentExperimentId)
            } catch (NumberFormatException nfe) {
                log.warn("Error in response data from REST API. Expected experiment ID as a long but received: '${currentExperimentId}'")
                return
            }
            List<Activity> testedExperimentList = compoundSummary.getTestedExptdata().findAll {Activity activity -> activity.bardExptId == experimentIdAsLong}
            if (testedExperimentList?.size() > 0)   {

                // only if it's tested? Is this right?
                CompoundSummaryCategorizer compoundSummaryCategorizer = returnValue["compoundSummaryCategorizer"]
                // One or the other of   assayFormat/assayType might be null ( though not both, or else we wouldn't have been called )
                //  Therefore substitute a nice friendly string "none" for that null string
                compoundSummaryCategorizer.addNewRecord ( experimentIdAsLong, //assayFormat,  assayType )
                                                          assayFormat ?: 'none',
                                                          assayType ?: 'none' )
                List <String> unconvertedBiologyHitIds = []
                List <String> unconvertedBiologyMissIds = []
                int outcome
                for (Activity testedExperiment in testedExperimentList)   {
                    outcome = testedExperiment.outcome
                    if (testedExperiment.outcome==2) {  // It's a hit!  Save all targets!
                        for (String oneTarget in currentTargets) {
                            unconvertedBiologyHitIds << oneTarget
                            returnValue ["hits"] <<  oneTarget
                        }
                    }  else { // It's a miss.  Save all the targets to a different list.
                        for (String oneTarget in currentTargets) {
                            returnValue ["misses"] <<  oneTarget
                            unconvertedBiologyMissIds << oneTarget

                        }
                    }
                }
                compoundSummaryCategorizer.updateOutcome(experimentIdAsLong,outcome,unconvertedBiologyHitIds,unconvertedBiologyMissIds)
            }
        }
    }







/**
     * The ideas to bring back a map which contains two lists, identified by the keys "hits" and "misses".  For
     * each one of these we will bring back a list of strings, where each string represents a target. Strings in
     * this list are not meant to be unique necessarily.  So we might have:
     *  "hits": "Q123", "Q123", "P456"
     *  "misses": "R789"
     *  [documentation note:  now we are getting biology IDs from the backend, so we might have
     *      "hits": 123, 734, 991  [and]   "misses": 666 ]
     * A particular protein target could conceivably be in both the hits and the misses category.
     *
     * @param compoundSummary
     * @return
     */
    LinkedHashMap<String, Object> retrieveActiveInactiveDataFromCompound (CompoundSummary compoundSummary){
        LinkedHashMap<String, List <String>> returnValue = [:]
        returnValue ["hits"]  = []
        returnValue ["misses"]  = []
        returnValue ["compoundSummaryCategorizer"]  = new CompoundSummaryCategorizer()
        if (compoundSummary != null){
           for (Assay assay in compoundSummary.testedAssays) {
               List<String>  currentExperimentIds = assay.experimentIds
               List<String>  currentTargets = assay.targetIds
               String assayFormat = assay.minimumAnnotation.assayFormat
               String assayType = assay.minimumAnnotation.assayType
               if ( (currentTargets != null)  ||
                    (assayFormat != null) ||
                    (assayType != null)){  // If one of the values we care about is non-null then retrieve everything we can find
                   retrievedTargetsAndBiologicalProcesses ( currentTargets,
                                                            currentExperimentIds,
                                                            compoundSummary,
                                                            returnValue,
                                                            assayFormat,
                                                            assayType )

               }
             }

        }
        return returnValue
    }


    String colorMappingOnPage (  String minimumValue, String maximumValue) {
        StringBuilder stringBuilder = new StringBuilder("")
        stringBuilder << """
        var continuousColorScale = d3.scale.linear()
            .domain([${minimumValue}, ${maximumValue}])
            .interpolate(d3.interpolateRgb)
            .range(["#deffd9", "#74c476"]);
    """.toString()
        stringBuilder.toString()
    }



    String placeSunburstOnPage ( int width, int height, long cid ) {
        StringBuilder stringBuilder = new StringBuilder("")

            stringBuilder << """
        <div id="sunburstdiv_empty">
        <script>
            if (\$data[0].children !== undefined) {
                createASunburst( ${width}, ${height},5,1000,continuousColorScale,'div#sunburstdiv', ${cid} );
            } else {
                            d3.select('div#sunburstdiv')
                                    .append('div')
                                    .attr("width", ${width})
                                    .attr("height", ${height} )
                                    .style("padding-top", '200px' )
                                    .style("text-align", 'center' )
                                    .append("h1")
                                    .html("No off-embargo assay data are  available for this compound." +
                                    "Please either choose a different compound, or else come" +
                                    " back later when more assay data may have accumulated.\");
                        }
        </script>

        </div>""".toString()

            stringBuilder.toString()
    }


    LinkedHashMap<String, Integer> accumulateAccessionNumbers( List<String> listOfAllTargets ) {
        LinkedHashMap<String, Integer> returnValue = [:]
        for ( String oneTarget in listOfAllTargets)  {
            if (returnValue.containsKey(oneTarget))  {
                returnValue[oneTarget]   =  returnValue[oneTarget]+1
            }  else {
                returnValue[oneTarget]  = 1
            }
        }
        return returnValue
    }



    /**
     * Convert the class information we have into a tree of StubRings suitable for building a sunburst
     *
     * Here are some corner cases:
     *      input empty or null: return a single root node
     *      single input element: single root node with a child
     *      three input elements, two of which are identical: single root node with two children, one of which will have weight==2
     *
     * @param targetClassInfoList
     * @return
     */
    public ringNodeFactory ( List<TargetClassInfo> targetClassInfoList, LinkedHashMap activeInactiveData = [:] ) {
        RingNode rootRingNode = new RingNode("/")
        if (targetClassInfoList?.size()  > 0){
            LinkedHashMap<String, RingNode> ringNodeMgr = [:]
            ringNodeMgr["/"] = rootRingNode
            for (TargetClassInfo targetClassInfo in targetClassInfoList) {
                RingNode currentRingNode = ringNodeMgr["/"]
                List<String> pathElements = targetClassInfo.path?.split("\\\\")
//                if (pathElements?.size()>0){
//                    String onePathElements = pathElements.last()
                for (String onePathElements in pathElements) {
                    String terminalElement = pathElements.last()
                    if (onePathElements?.size()>0) {
                        // is this piece of path in the tree already? If not then add it, otherwise boost the reference count of the existing element
                        if (ringNodeMgr.containsKey(onePathElements)) {
                            currentRingNode =  ringNodeMgr[onePathElements]
                            if (terminalElement == onePathElements) {  // don't repeatedly count an element which is serving only to mark our place in the tree
                                currentRingNode.size += 1
                            }
                        }  else {
                            ringNodeMgr[onePathElements] = new RingNode (onePathElements)
                            currentRingNode.children << ringNodeMgr[onePathElements]
                        }
                        if ( (activeInactiveData["hits"]?.contains (targetClassInfo.accessionNumber)) &&
                                (terminalElement == onePathElements)  ){
                            ringNodeMgr[onePathElements].actives <<   targetClassInfo.accessionNumber
                        }
                        if ( (activeInactiveData["misses"]?.contains (targetClassInfo.accessionNumber)) &&
                                (terminalElement == onePathElements)   ){
                            ringNodeMgr[onePathElements].inactives <<   targetClassInfo.accessionNumber
                        }
                    }
                }

            }
        }
        return  rootRingNode
    }

    /***
     *
     * @param unconvertedValues
     * @param whichSubset
     * @return
     */
    private List <String> generateAccessionIdentifiers(List<String>  unconvertedValues,String whichSubset)   {
        final List<String> targets = []
        List <String> returnValues = []

        unconvertedValues.each {targets <<  it }
        if (targets.size() > 0)  {
            List<Long> targetsAsLongs = []
            for (String target in targets) {
                try {
                    targetsAsLongs << Long.parseLong(target)
                } catch (NumberFormatException numberFormatException){
                    println("Please contact NCGC. We saw a nonnumeric value in the targets field coming back from/compound/{cid}/summary. Instead = ${target}.")
                    break
                }

            }
            List <BiologyEntity> biologyEntityList =  biologyRestService.convertBiologyId(targetsAsLongs)
            // processes, as opposed to proteins, start with the suffix GO. Remove those, since they are relevant to the Sunburst
            //As well, check to make sure that whatever comes back really has the format of a UNIPROT accession number
            returnValues=  biologyEntityList*.extId.findAll{!(it==~/^GO.*/)}.findAll{it==~/([A-N,R-Z][0-9][A-Z][A-Z,0-9][A-Z,0-9][0-9])|([O,P,Q][0-9][A-Z, 0-9][A-Z,0-9][A-Z,0-9][0-9])/}
        }
        returnValues
    }



    private List <String> generateAccessionIdentifiers(CompoundSummaryCategorizer compoundSummaryCategorizer)   {

        // Make a map, so I can remember what went where
        List<Long> targetsAsLongs = []
        LinkedHashMap<Long,Long> biologyIdToEid = [:]
        for ( element in compoundSummaryCategorizer.getTotalContents() )  {
            Long eid =  element.key
            List<Long> unconvertedBiologyObjectList =  ((CompoundSummaryCategorizer.SingleEidSummary) element.value).unconvertedBiologyObjects
            for ( Long unconvertedBiologyObject in unconvertedBiologyObjectList){
                if (!biologyIdToEid.containsKey(unconvertedBiologyObject)) {
                    biologyIdToEid [unconvertedBiologyObject]  =  eid
                }
            }
        }

        //  Perform the conversion
        List <BiologyEntity> biologyEntityList =  biologyRestService.convertBiologyId(biologyIdToEid.keySet() as List<Long>)

        // Now go through the generated list, pull out the information I need and put it back where
        //  it belongs using our map
        for (BiologyEntity biologyEntity in biologyEntityList) {
            Long biologyId =  biologyEntity.serial
            Long targetEid = 0
            if (!biologyIdToEid.containsKey(biologyId)){
                println  "I am surprised. I expected ${biologyId} to be in this set"
            } else {
                targetEid =  biologyIdToEid [biologyId]
                if (biologyEntity.biology == "PROTEIN") {
                    compoundSummaryCategorizer.combineInNewProteinTargetValue(targetEid,biologyEntity.name)
                }  else {
                    compoundSummaryCategorizer.combineInNewBiologicalProcessValue(targetEid,biologyEntity.name)
                }
            }
        }
        return []
    }






    /***
     *
     * @param unconvertedValues
     * @return
     */
    private LinkedHashMap convertBiologyIdsToAscensionNumbers (LinkedHashMap unconvertedValues)  {
        LinkedHashMap convertedValues  = [:]
        // first will convert the hits
        convertedValues["hits"]  =  generateAccessionIdentifiers(unconvertedValues["hits"],"hits")
        // now add those assays that did not hit for this compound
        convertedValues["misses"]  =  generateAccessionIdentifiers(unconvertedValues["misses"],"misses")

        return  convertedValues
    }






    public  LinkedHashMap<String, Object>  convertCompoundSummaryIntoSunburst (CompoundSummary compoundSummary, Boolean includeHits, Boolean includeNonHits ){
        LinkedHashMap<String, Object> returnValue = [:]
        LinkedHashMap activeInactiveDataPriorToConversion = retrieveActiveInactiveDataFromCompound(compoundSummary)
        generateAccessionIdentifiers(activeInactiveDataPriorToConversion["compoundSummaryCategorizer"])
        returnValue ["CompoundSummaryCategorizer"]  =  activeInactiveDataPriorToConversion["compoundSummaryCategorizer"]
        LinkedHashMap activeInactiveData = convertBiologyIdsToAscensionNumbers(activeInactiveDataPriorToConversion)
        final List<String> targets = []
        if (includeHits) {
            activeInactiveData["hits"].each {targets <<  it }
        }
        if (includeNonHits) {
            activeInactiveData["misses"].each {targets <<  it }
        }
        LinkedHashMap<String, Integer> accumulatedTargets = accumulateAccessionNumbers( targets )
        List<List<TargetClassInfo>> accumulatedMaps = []
        accumulatedTargets.each{k,v->
            List<String> hierarchyDescription = sunburstCacheService.getTargetClassInfo(k)
            if (hierarchyDescription != null){
                List<TargetClassInfo> temporaryValue = null
                temporaryValue = sunburstCacheService.getTargetClassInfo(k)
                if ((temporaryValue != null) &&
                    (temporaryValue.size() > 0)) {
                    accumulatedMaps<<temporaryValue
                }
            }
        }
        returnValue ["RingNode"]  =   ringNodeFactory(accumulatedMaps.flatten(),activeInactiveData )
        returnValue
    }

    /**
     * Here's a wrapper routine in case someone wants to start with a CID as opposed to a fully constructed CompoundSummary
     * @param cid
     * @param includeHits
     * @param includeNonHits
     * @return
     */
    public  LinkedHashMap<String,Object> convertCompoundIntoSunburstById (Long cid, Boolean includeHits, Boolean includeNonHits, LinkedHashMap<String,Object> mapToHoldCompoundSummary = [:]){
        // Since we have no real data, I'll pull from previous versions.  When the situation changes and comment the line below
        CompoundSummary compoundSummary = compoundRestService.getSummaryForCompound(cid)
        mapToHoldCompoundSummary.'id' = cid
        mapToHoldCompoundSummary.'compoundSummary' = compoundSummary
        LinkedHashMap ringNodeAndCrossLinks =  convertCompoundSummaryIntoSunburst ( compoundSummary,  includeHits,  includeNonHits )
        return ringNodeAndCrossLinks
    }



    public  LinkedHashMap<String,Object>  convertCompoundIntoSunburst (CompoundSummary compoundSummary, Boolean includeHits, Boolean includeNonHits ){
        // Since we have no real data, I'll pull from previous versions.  When the situation changes and comment the line below
//        CompoundSummary compoundSummary = compoundRestService.getSummaryForCompound(cid)
        return convertCompoundSummaryIntoSunburst ( compoundSummary,  includeHits,  includeNonHits )
    }




}
