package bardqueryapi

import bard.core.adapter.CompoundAdapter
import bard.core.rest.RESTAssayService
import bard.core.rest.RESTExperimentService
import bard.core.rest.RESTProjectService
import com.metasieve.shoppingcart.ShoppingCartService
import molspreadsheet.MolSpreadSheetCell
import molspreadsheet.MolSpreadSheetData
import molspreadsheet.SpreadSheetActivityStorage
import bard.core.*

class MolecularSpreadSheetService {

    QueryCartService queryCartService
    QueryServiceWrapper queryServiceWrapper
    ShoppingCartService shoppingCartService
    IQueryService queryService

    final static Integer MAXIMUM_NUMBER_OF_COMPOUNDS = 1000

    /**
     * High-level routine to pull information out of the query cart and store it into a data structure suitable
     * for passing to the molecular spreadsheet.
     *
     * @return MolSpreadSheetData
     */
    MolSpreadSheetData retrieveExperimentalData() {

        MolSpreadSheetDataBuilderDirector molSpreadSheetDataBuilderDirector = new MolSpreadSheetDataBuilderDirector()

        MolSpreadSheetDataBuilder molSpreadSheetDataBuilder = new MolSpreadSheetDataBuilder(this)

        molSpreadSheetDataBuilderDirector.setMolSpreadSheetDataBuilder(molSpreadSheetDataBuilder)
        molSpreadSheetDataBuilderDirector.constructMolSpreadSheetData(retrieveCartCompoundFromShoppingCart(),
                retrieveCartAssayFromShoppingCart(),
                retrieveCartProjectFromShoppingCart())

        molSpreadSheetDataBuilderDirector.getMolSpreadSheetData()
    }

    /**
     * For a given SpreadSheetActivityList, pull back a map specifying all compounds, along with structures, CIDs, etc
     * @param SpreadSheetActivityList
     * @return
     */
    Map convertSpreadSheetActivityToCompoundInformation(List<SpreadSheetActivity> SpreadSheetActivityList) {
        def compoundIds = new ArrayList<Long>()
        for (SpreadSheetActivity spreadSheetActivity in SpreadSheetActivityList) {
            compoundIds.add(spreadSheetActivity.cid)
        }
        List<SearchFilter> filters = new ArrayList<SearchFilter>()
        queryService.findCompoundsByCIDs(compoundIds, filters)
    }
    /**
     * When do we have sufficient data to charge often try to build a spreadsheet?
     *
     * @return
     */
    Boolean weHaveEnoughDataToMakeASpreadsheet() {
        boolean returnValue = false
        LinkedHashMap<String, List> itemsInShoppingCart = queryCartService.groupUniqueContentsByType(shoppingCartService)
        if (queryCartService?.totalNumberOfUniqueItemsInCart(itemsInShoppingCart, QueryCartService.cartProject) > 0)
            returnValue = true
        else if (queryCartService?.totalNumberOfUniqueItemsInCart(itemsInShoppingCart, QueryCartService.cartAssay) > 0)
            returnValue = true
        else if (queryCartService?.totalNumberOfUniqueItemsInCart(itemsInShoppingCart, QueryCartService.cartCompound) > 0)
            returnValue = true
        returnValue
    }

    /**
     * For a set of experiments
     * @param experimentList
     * @param etag
     * @return
     */
    protected List<SpreadSheetActivity> extractMolSpreadSheetData(MolSpreadSheetData molSpreadSheetData, List<Experiment> experimentList, Object etag) {
        // now step through the data and place into molSpreadSheetData
        List<SpreadSheetActivity> spreadSheetActivityList = new ArrayList<SpreadSheetActivity>()

        // we need to handle each experiment separately ( until NCGC can do this in the background )
        // Note that each experiment corresponds to a column in our spreadsheet
        int columnCount = 0
        for (Experiment experiment in experimentList) {

            ServiceIterator<Value> experimentIterator = queryServiceWrapper.restExperimentService.activities(experiment, etag)

            // Now step through the result set and pull back  one value for each compound
            Value experimentValue
            while (experimentIterator.hasNext()) {
                experimentValue = experimentIterator.next()
                Long translation = new Long(experimentValue.id.split("\\.")[0])
                if (!molSpreadSheetData.columnPointer.containsKey(translation)) {
                    molSpreadSheetData.columnPointer.put(translation, columnCount)
                }
                spreadSheetActivityList.add(extractActivitiesFromExperiment(experimentValue))
            }
            columnCount++
        }
        spreadSheetActivityList
    }
    /**
     * For a set of experiments
     * @param experimentList
     * @param etag
     * @return
     */
    protected List<SpreadSheetActivity> extractMolSpreadSheetData(MolSpreadSheetData molSpreadSheetData, List<Experiment> experimentList, List<Long> compounds) {
        // now step through the data and place into molSpreadSheetData
        List<SpreadSheetActivity> spreadSheetActivityList = new ArrayList<SpreadSheetActivity>()

        // we need to handle each experiment separately ( until NCGC can do this in the background )
        // Note that each experiment corresponds to a column in our spreadsheet
        int columnCount = 0
        for (Experiment experiment in experimentList) {
           // List<Long> collectCompounds = []
            final ServiceIterator<Compound> compoundsTestedInExperimentIter = queryServiceWrapper.restExperimentService.compounds(experiment)
//            while (compoundsTestedInExperimentIter.hasNext()) {
//                final Compound compound = compoundsTestedInExperimentIter.next()
//                CompoundAdapter c = new CompoundAdapter(compound)
//                collectCompounds.add(c.pubChemCID)
//            }
            List<Long> collectCompounds = compoundsTestedInExperimentIter.collect{Compound compound -> new CompoundAdapter(compound).pubChemCID }
            //only do this if compounds is not empty
            if (!compounds.isEmpty()) {
                //find the intersection of the two Compound lists
                collectCompounds = compounds.intersect(collectCompounds)
            }
            if (!collectCompounds.isEmpty()) {
                ServiceIterator<Value> experimentIterator = queryServiceWrapper.restExperimentService.activities(experiment)
                // Now step through the result set and pull back  one value for each compound
                Value experimentValue
                while (experimentIterator.hasNext()) {
                    experimentValue = experimentIterator.next()
                    Long translation = new Long(experimentValue.id.split("\\.")[0])
                    if (!molSpreadSheetData.columnPointer.containsKey(translation)) {
                        molSpreadSheetData.columnPointer.put(translation, columnCount)
                    }
                    spreadSheetActivityList.add(extractActivitiesFromExperiment(experimentValue, experiment.id))
                }
            } else {
                molSpreadSheetData.columnPointer.put(experiment.id as Long, columnCount)
            }
            columnCount++
        }
        spreadSheetActivityList
    }
    /**
     *
     * @param molSpreadSheetData
     * @param experimentList
     * @param spreadSheetActivityList
     */
    protected void populateMolSpreadSheetData(MolSpreadSheetData molSpreadSheetData, List<Experiment> experimentList, List<SpreadSheetActivity> spreadSheetActivityList) {
        // now step through the data and place into molSpreadSheetData
        int columnPointer = 0
        Map<String,MolSpreadSheetCell> map   = new HashMap<String,MolSpreadSheetCell> ()
        // we need to handle each experiment separately ( until NCGC can do this in the background )
        // Note that each experiment corresponds to a column in our spreadsheet
        for (Experiment experiment in experimentList) {

            for (SpreadSheetActivity spreadSheetActivity in spreadSheetActivityList) {
                if (molSpreadSheetData.rowPointer.containsKey(spreadSheetActivity.cid)) {
                    int innerRowPointer = molSpreadSheetData.rowPointer[spreadSheetActivity.cid]
                    int innerColumnCount = molSpreadSheetData.columnPointer[spreadSheetActivity.experimentId]
                    String arrayKey = innerRowPointer.toString() + "_" + (innerColumnCount + 3).toString()
                    SpreadSheetActivityStorage spreadSheetActivityStorage = new SpreadSheetActivityStorage(spreadSheetActivity)

                    MolSpreadSheetCell molSpreadSheetCell = new MolSpreadSheetCell(spreadSheetActivity.interpretHillCurveValue().toString(),
                            MolSpreadSheetCellType.numeric,
                            MolSpreadSheetCellUnit.Molar,
                            spreadSheetActivityStorage)
                    if (spreadSheetActivityStorage == null)
                        molSpreadSheetCell.activity = false
                    map.put(arrayKey, molSpreadSheetCell)
                }
               // else {
                    //println "did not expect cid = ${spreadSheetActivity.cid}"
                //}

            }
            columnPointer++
        }
        molSpreadSheetData.mssData.putAll(map)
        molSpreadSheetData
    }

    /**
     *
     * @param experimentList
     * @return
     */
    protected Object retrieveImpliedCompoundsEtagFromAssaySpecification(List<Experiment> experimentList) {
        Object etag = null
        for (Experiment experiment in experimentList) {
            final ServiceIterator<Compound> compoundServiceIterator = this.queryServiceWrapper.restExperimentService.compounds(experiment)
            List<Compound> singleExperimentCompoundList = compoundServiceIterator.next(MAXIMUM_NUMBER_OF_COMPOUNDS)
            if (etag == null)
                etag = this.queryServiceWrapper.restCompoundService.newETag("dsa", singleExperimentCompoundList*.id);
            else if ((singleExperimentCompoundList != null) &&
                    (singleExperimentCompoundList.size() > 0))
                this.queryServiceWrapper.restCompoundService.putETag(etag, singleExperimentCompoundList*.id);
        }
        etag
    }

    protected List<CartCompound> retrieveCartCompoundFromShoppingCart() {
        List<CartCompound> cartCompoundList = new ArrayList<CartCompound>()
        for (CartCompound cartCompound in (queryCartService.groupUniqueContentsByType(shoppingCartService)[(QueryCartService.cartCompound)])) {
            cartCompoundList.add(cartCompound)
        }
        cartCompoundList
    }

    protected List<CartAssay> retrieveCartAssayFromShoppingCart() {
        List<CartAssay> cartAssayList = new ArrayList<CartAssay>()
        for (CartAssay cartAssay in (queryCartService.groupUniqueContentsByType(shoppingCartService)[(QueryCartService.cartAssay)])) {
            cartAssayList.add(cartAssay)
        }
        cartAssayList
    }

    protected List<CartProject> retrieveCartProjectFromShoppingCart() {
        List<CartProject> cartProjectList = new ArrayList<CartProject>()
        for (CartProject cartProject in (queryCartService.groupUniqueContentsByType(shoppingCartService)[(QueryCartService.cartProject)])) {
            cartProjectList.add(cartProject)
        }
        cartProjectList
    }

    /**
     *
     * @param molSpreadSheetData
     * @param cartCompoundList
     */
    protected void populateMolSpreadSheetRowMetadata(final MolSpreadSheetData molSpreadSheetData, final List<CartCompound> cartCompoundList) {

        // Make sure that the variable we're filling  leaves this routine with something in
        if (molSpreadSheetData.rowPointer == null) molSpreadSheetData.rowPointer = new LinkedHashMap<Long, Integer>()
        if (molSpreadSheetData.mssData == null) molSpreadSheetData.rowPointer = new LinkedHashMap<Long, Integer>()

        // add specific values for the cid column
        int rowCount = 0
        for (CartCompound cartCompound in cartCompoundList) {
            updateMolSpreadSheetDataToReferenceCompound(molSpreadSheetData, rowCount++, cartCompound.compoundId as Long, cartCompound.name, cartCompound.smiles)
        }

    }

    /**
     *
     * @param molSpreadSheetData
     * @param compoundAdapterMap
     * @return
     */
    protected void populateMolSpreadSheetRowMetadata(final MolSpreadSheetData molSpreadSheetData, final Map compoundAdapterMap) {

        // Make sure that the variable we're filling  leaves this routine with something in
        if (molSpreadSheetData.rowPointer == null) molSpreadSheetData.rowPointer = new LinkedHashMap<Long, Integer>()
        if (molSpreadSheetData.mssData == null) molSpreadSheetData.rowPointer = new LinkedHashMap<Long, Integer>()

        // Add every compound we can find in the compound adapters map
        List<CompoundAdapter> compoundAdaptersList = compoundAdapterMap.compoundAdapters
        int rowCount = 0
        for (CompoundAdapter compoundAdapter in compoundAdaptersList) {
            String smiles = compoundAdapter.structureSMILES
            Long cid = compoundAdapter.pubChemCID
            String name = compoundAdapter.name
            updateMolSpreadSheetDataToReferenceCompound(molSpreadSheetData, rowCount++, cid, name, smiles)
        }

    }

    /**
     *  add a pointer to a row along with the first two columns
     * @param molSpreadSheetData
     * @param rowCount
     * @param compoundId
     * @param compoundName
     * @param compoundSmiles
     * @return
     */
    protected MolSpreadSheetData updateMolSpreadSheetDataToReferenceCompound(final MolSpreadSheetData molSpreadSheetData,
                                                                             final int rowCount,
                                                                             final Long compoundId,
                                                                             final String compoundName,
                                                                             final String compoundSmiles) {
        // need to be able to map from CID to row location
        molSpreadSheetData.rowPointer.put(compoundId, rowCount)

        // add values for the cid column
        molSpreadSheetData.mssData.put("${rowCount}_0".toString(), new MolSpreadSheetCell(compoundName, compoundSmiles, MolSpreadSheetCellType.image))
        molSpreadSheetData.mssData.put("${rowCount}_1".toString(), new MolSpreadSheetCell(compoundId.toString(), MolSpreadSheetCellType.identifier))
        //we will use this to get the promiscuity score
        molSpreadSheetData.mssData.put("${rowCount}_2".toString(), new MolSpreadSheetCell(compoundId.toString(), MolSpreadSheetCellType.identifier))

        molSpreadSheetData

    }

    /**
     *
     * @param molSpreadSheetData
     * @param experimentList
     */
    protected void populateMolSpreadSheetColumnMetadata(MolSpreadSheetData molSpreadSheetData, List<Experiment> experimentList) {

        // Check variable that we plan to modify
        if (molSpreadSheetData.mssHeaders == null) molSpreadSheetData.mssHeaders = new ArrayList<String>()

        // now retrieve the header names from the assays
        molSpreadSheetData.mssHeaders.add("Struct")
        molSpreadSheetData.mssHeaders.add("CID")
        molSpreadSheetData.mssHeaders.add("UNM Promiscuity Analysis")
        for (Experiment experiment in experimentList)
            molSpreadSheetData.mssHeaders.add(experiment.name)
    }



    protected Object generateETagFromCartCompounds(List<CartCompound> cartCompoundList) {
        List<Long> cartCompoundIdList = new ArrayList<Long>()
        for (CartCompound cartCompound in cartCompoundList)
            cartCompoundIdList.add(new Long(cartCompound.compoundId))
        Date date = new Date()

        queryServiceWrapper.restCompoundService.newETag(date.toString(), cartCompoundIdList);
    }

    /**
     *
     * @param cartCompounds
     * @return list of Experiment's from a list of CartCompound's
     */
    protected List<Long> cartCompoundsToCIDS(final List<CartCompound> cartCompounds) {
        List<Long> cids = []
        for (CartCompound cartCompound : cartCompounds) {
            long cid = cartCompound.compoundId
            cids.add(cid)
        }

        return cids
    }

    /**
     * Convert Assay ODs to expt ids
     * @param cartAssays
     * @return
     */
    protected List<Long> generateCompoundListFromAssays(final List<CartAssay> cartAssays) {
        List<Long> assayIds = []
        for (CartAssay cartAssay : cartAssays) {
            long assayId = cartAssay.assayId
            assayIds.add(assayId)
        }
        final RESTAssayService restAssayService = queryServiceWrapper.getRestAssayService()
        List<Experiment> allExperiments = []
        for (Long individualAssayIds in assayIds) {

            Assay assay = restAssayService.get(individualAssayIds)
            final ServiceIterator<Experiment> serviceIterator = restAssayService.iterator(assay, Experiment.class)
            Collection<Experiment> experimentList = serviceIterator.collect()
            allExperiments.addAll(experimentList)
        }
        new ArrayList<Long>()
    }

    /**
     * Convert Assay ODs to expt ids
     * @param cartAssays
     * @return
     */
    protected List<Experiment> assaysToExperiments(final Collection<Assay> assays) {
        final RESTAssayService restAssayService = this.queryServiceWrapper.getRestAssayService()
        List<Experiment> allExperiments = []
        if (!assays.isEmpty()) {
            Iterator<Assay> assayIterator = assays.iterator()
            while (assayIterator.hasNext()) {
                Assay assay = assayIterator.next()
                final ServiceIterator<Experiment> serviceIterator = restAssayService.iterator(assay, Experiment.class)
                Collection<Experiment> experimentList = serviceIterator.collect()
                allExperiments.addAll(experimentList)
            }
        }

        return allExperiments
    }

    /**
     * Convert Cart assays to Experiments starting with a list of Assay IDs
     * @param cartAssays
     * @return
     */
    protected List<Experiment> assaysToExperiments(List<Experiment> incomingExperimentList, final List<Long> assayIds) {

        List<Experiment> allExperiments
        if (incomingExperimentList == null) {
            allExperiments = []
        }
        else {
            allExperiments = incomingExperimentList
        }
        final RESTAssayService restAssayService = queryServiceWrapper.getRestAssayService()

        for (Long individualAssayIds in assayIds) {
            Assay assay = restAssayService.get(individualAssayIds)
            final ServiceIterator<Experiment> serviceIterator = restAssayService.iterator(assay, Experiment.class)
            Collection<Experiment> experimentList = serviceIterator.collect()
            allExperiments.addAll(experimentList)
        }

        return allExperiments
    }

    /**
     * Convert Cart assays to Experiments, starting this time with cart Assays
     * @param cartAssays
     * @return
     */
    protected List<Experiment> cartAssaysToExperiments(List<Experiment> incomingExperimentList, final List<CartAssay> cartAssays) {
        List<Long> assayIds = []
        for (CartAssay cartAssay : cartAssays) {
            long assayId = cartAssay.assayId
            assayIds.add(assayId)
        }

        assaysToExperiments(incomingExperimentList, assayIds)
    }

    /**
     *
     * @param incomingExperimentList
     * @param cartCompounds
     * @return
     */
    protected List<Experiment> cartCompoundsToExperiments(List<Experiment> incomingExperimentList, final List<CartCompound> cartCompounds) {
        List<Long> compoundIds = []
        for (CartCompound cartCompound in cartCompounds) {
            int compoundId = cartCompound.compoundId
            compoundIds.add(compoundId as Long)
        }


        List<Assay> allAssays = []
        for (Long individualCompoundId in compoundIds) {
            Compound compound = queryServiceWrapper.getRestCompoundService().get(individualCompoundId)
            if (compound != null) {
                Collection<Assay> activeAssaysForThisCompound = queryServiceWrapper.getRestCompoundService().getTestedAssays(compound, true)  // true = active only
                for (Assay assay in activeAssaysForThisCompound) {
                    allAssays << assay
                }
            }
        }
        assaysToExperiments(allAssays)
    }

    /**
     *
     * @param cartProjects
     * @return list of Experiment's from a list of CartProject's
     */
    protected List<Experiment> cartProjectsToExperiments(final List<CartProject> cartProjects) {
        List<Long> projectIds = []
        for (CartProject cartProject : cartProjects) {
            long projectId = cartProject.projectId
            projectIds.add(projectId)
        }
        List<Experiment> allExperiments = []
        final RESTProjectService restProjectService = queryServiceWrapper.getRestProjectService()
        final RESTAssayService restAssayService = queryServiceWrapper.getRestAssayService()
        final Collection<Project> projects = restProjectService.get(projectIds)

        for (Project project : projects) {
            final ServiceIterator<Assay> serviceIterator = restProjectService.iterator(project, Assay.class)
            Collection<Assay> assays = serviceIterator.collect()
            for (Assay assay : assays) {
                final ServiceIterator<Experiment> experimentIterator = restAssayService.iterator(assay, Experiment.class)
                Collection<Experiment> experimentList = experimentIterator.collect()
                allExperiments.addAll(experimentList)
            }
        }
        return allExperiments
    }

    /**
     *
     * @param experiment
     * @param compoundETag
     * @return
     */
    List<SpreadSheetActivity> findActivitiesForCompounds(final Experiment experiment, final Object compoundETag) {
        final List<SpreadSheetActivity> spreadSheetActivities = new ArrayList<SpreadSheetActivity>()
        final ServiceIterator<Value> experimentIterator = this.queryServiceWrapper.restExperimentService.activities(experiment, compoundETag);
        while (experimentIterator.hasNext()) {
            Value experimentValue = experimentIterator.next()
            if (experimentValue) {
                SpreadSheetActivity spreadSheetActivity = extractActivitiesFromExperiment(experimentValue, new Long(experiment.id))
                spreadSheetActivities.add(spreadSheetActivity)
            }
        }
        return spreadSheetActivities
    }


    public Map findExperimentDataById(final Long experimentId, final int top = 10, final int skip = 0) {
        List<SpreadSheetActivity> spreadSheetActivities = []
        final RESTExperimentService restExperimentService = queryServiceWrapper.getRestExperimentService()
        long totalNumberOfRecords = 0
        ExperimentValues.ExperimentRole role = null
        Experiment experiment = restExperimentService.get(experimentId)
        if (experiment) {
            role = experiment?.role
            final ServiceIterator<Value> experimentIterator = restExperimentService.activities(experiment);

            List<Value> activityValues = []
            if (experimentIterator.hasNext()) {
                if (skip == 0) {
                    activityValues = experimentIterator.next(top)
                }
                else { //There is no way to pass in skip and top to the iterator so we have to do this hack
                    //which is not perfect
                    activityValues = experimentIterator.next(top + skip)
                    activityValues = activityValues.subList(skip, activityValues.size())
                }
            }
            final Iterator<Value> iterator = activityValues.iterator()
            while (iterator.hasNext()) {
                Value experimentValue = iterator.next()
                SpreadSheetActivity spreadSheetActivity = extractActivitiesFromExperiment(experimentValue, experimentId)
                spreadSheetActivities.add(spreadSheetActivity)
            }

            totalNumberOfRecords = experimentIterator.getCount()
        }
        return [total: totalNumberOfRecords, spreadSheetActivities: spreadSheetActivities, role: role, experiment: experiment]
    }

    /**
     *
     * @param experimentValue
     * @return SpreadSheetActivity
     */
    SpreadSheetActivity extractActivitiesFromExperiment(final Value experimentValue, final Long experimentId) {
        final Iterator<Value> experimentValueIterator = experimentValue.children()
        SpreadSheetActivity spreadSheetActivity = new SpreadSheetActivity()
        spreadSheetActivity.experimentId = experimentId
        while (experimentValueIterator.hasNext()) {
            Value childValue = experimentValueIterator.next()
            addCurrentActivityToSpreadSheet(spreadSheetActivity, childValue)
        }
        return spreadSheetActivity
    }
    /**
     *
     * @param spreadSheetActivity
     * @param childValue
     */
    void addCurrentActivityToSpreadSheet(final SpreadSheetActivity spreadSheetActivity, final Value childValue) {
        String identifier = childValue.id

        if (childValue instanceof HillCurveValue) {
            spreadSheetActivity.hillCurveValue = childValue
            spreadSheetActivity.readouts.push(childValue)
            return
        }
        switch (identifier) {
            case "potency":
                spreadSheetActivity.potency = (Double) childValue.value
                break
            case "outcome":
                spreadSheetActivity.activityOutcome = ActivityOutcome.findActivityOutcome((Integer) childValue.value)
                break
            case "eid":
                spreadSheetActivity.eid = (Long) childValue.value
                break
            case "cid":

                spreadSheetActivity.cid = (Long) childValue.value
                break
            case "sid":
                spreadSheetActivity.sid = (Long) childValue.value
                break
            default:
                throw new RuntimeException("Experiment Identifier: ${identifier} is unknown")
        }
    }

}