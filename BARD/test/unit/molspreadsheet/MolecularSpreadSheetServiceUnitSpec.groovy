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

package molspreadsheet

import bard.core.adapter.CompoundAdapter
import bard.core.rest.spring.AssayRestService
import bard.core.rest.spring.CompoundRestService
import bard.core.rest.spring.ExperimentRestService
import bard.core.rest.spring.ProjectRestService
import bard.core.rest.spring.assays.Assay
import bard.core.rest.spring.compounds.Compound
import bard.core.rest.spring.experiment.ExperimentSearch
import bard.core.rest.spring.experiment.ExperimentSearchResult
import bard.core.rest.spring.project.Project
import bard.core.rest.spring.project.ProjectResult
import bardqueryapi.IQueryService
import com.metasieve.shoppingcart.ShoppingCartService
import grails.test.mixin.TestFor
import querycart.QueryCartService
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created with IntelliJ IDEA.
 * User: balexand
 * Date: 10/9/12
 * Time: 12:17 PM
 * To change this template use File | Settings | File Templates.
 */

@Unroll
@TestFor(MolecularSpreadSheetService)
class MolecularSpreadSheetServiceUnitSpec extends Specification {

    QueryCartService queryCartService
    ShoppingCartService shoppingCartService
    IQueryService queryService
    ExperimentRestService experimentRestService
    AssayRestService assayRestService
    CompoundRestService compoundRestService
    ProjectRestService projectRestService
    CompoundAdapter compoundAdapter = buildCompoundAdapter(6L)
    @Shared Map compoundAdapterMap = [compoundAdapters: [buildCompoundAdapter(6L)], facets: null, nHits: 0]


    void setup() {
        this.experimentRestService = Mock(ExperimentRestService)
        this.compoundRestService = Mock(CompoundRestService)
        this.projectRestService = Mock(ProjectRestService)
        this.assayRestService = Mock(AssayRestService)
        this.queryCartService = Mock(QueryCartService)
        this.shoppingCartService = Mock(ShoppingCartService)
        this.queryService = Mock(IQueryService)
        service.assayRestService = assayRestService
        service.compoundRestService = compoundRestService
        service.experimentRestService = experimentRestService
        service.projectRestService = projectRestService
        service.queryService = this.queryService
    }

    void "test prepareForExport #label"() {
        given:
        final MolSpreadSheetData molSpreadSheetData = new MolSpreadSheetData()

        when:
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'a')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'b')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'c')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'e'),
                new MolSpreadSheetColSubHeader(columnTitle:'f'),
                new MolSpreadSheetColSubHeader(columnTitle:'g')])
        MolSpreadSheetCell molSpreadSheetCell0 = new MolSpreadSheetCell("2", MolSpreadSheetCellType.image)
        molSpreadSheetCell0.spreadSheetActivityStorage = new SpreadSheetActivityStorage()
        MolSpreadSheetCell molSpreadSheetCell1 = new MolSpreadSheetCell("2", MolSpreadSheetCellType.string)
        molSpreadSheetCell1.spreadSheetActivityStorage = new SpreadSheetActivityStorage()
        MolSpreadSheetCell molSpreadSheetCell2 = new MolSpreadSheetCell("2.1", MolSpreadSheetCellType.numeric)
        molSpreadSheetCell2.spreadSheetActivityStorage = new SpreadSheetActivityStorage()
        molSpreadSheetData.mssData = [:]
        molSpreadSheetData.mssData.put("1_0", molSpreadSheetCell0)
        molSpreadSheetData.mssData.put("1_1", molSpreadSheetCell1)
        molSpreadSheetData.mssData.put("1_3", molSpreadSheetCell1)
        molSpreadSheetData.mssData.put("1_4", molSpreadSheetCell1)
        molSpreadSheetData.mssData.put("1_5", molSpreadSheetCell2)
        molSpreadSheetData.rowPointer[0L] = 0
        molSpreadSheetData.rowPointer[1L] = 1
        LinkedHashMap<String, Object> dataForExporting = service.prepareForExport(molSpreadSheetData)

        then:
        assertNotNull dataForExporting
        assertNotNull dataForExporting."labels"
        assert (dataForExporting["labels"]).size() == 6
        assertNotNull dataForExporting."fields"
        assert (dataForExporting["fields"]).size() == 5
        assertNotNull dataForExporting."data"
        assert (dataForExporting["data"]).size() == 2

    }

    void "test prepareForExport degenerate"() {
        given:
        final MolSpreadSheetData molSpreadSheetData = new MolSpreadSheetData()

        when:
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'a')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'b')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'c')])
        molSpreadSheetData.mssData = [:]
        molSpreadSheetData.rowPointer[0L] = 0
        LinkedHashMap<String, Object> dataForExporting = service.prepareForExport(molSpreadSheetData)

        then:
        assertNotNull dataForExporting
        assertNotNull dataForExporting."labels"
        assert (dataForExporting["labels"]).size() == 3
        assertNotNull dataForExporting."fields"
        assert (dataForExporting["fields"]).size() == 2
        assertNotNull dataForExporting."data"
        assert (dataForExporting["data"]).size() == 1

    }

    void "test assays To Experiments"() {
        given:
        Collection<Assay> assays = [new Assay(assayId: 2)]
        when:
        List<ExperimentSearch> experiments = service.assaysToExperiments(assays)
        then:
        assert experiments.isEmpty()
    }



    void "test assays To Experiments multi parm"() {
        given:
        Collection<Assay> assays = [new Assay(assayId: 2)]
        when:
        List<ExperimentSearch> experiments = service.assaysToExperiments(assays )
        then:
        assert experiments.isEmpty()
    }




    void "test prepareMapOfColumnsToAssay "() {
        when:
        List<ExperimentSearch> experimentList = [new  ExperimentSearch(capExptId: 0L),
                                                 new  ExperimentSearch(capExptId: 1L),
                                                 new  ExperimentSearch(capExptId: 2L),
                                                 new  ExperimentSearch(capExptId: 3L),
                                                 new  ExperimentSearch(capExptId: 4L)]
        final MolSpreadSheetData molSpreadSheetData = new MolSpreadSheetData()
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'a')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'b')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'c')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'d')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'e'),
                new MolSpreadSheetColSubHeader(columnTitle:'f'),
                new MolSpreadSheetColSubHeader(columnTitle:'g')])
        service.prepareMapOfColumnsToAssay(molSpreadSheetData,experimentList)
        molSpreadSheetData.experimentNameList << 'a'

        then: "we want to pull out the active values"
        assertNotNull molSpreadSheetData
        assert molSpreadSheetData.mapColumnsToAssay.size() == 7
    }

    void "test prepareMapOfColumnsToAssay inner loops"() {
        when:
        final List<ExperimentSearch> experimentList = [new  ExperimentSearch(capExptId: 0L),
                new  ExperimentSearch(capExptId: 1L),
                new  ExperimentSearch(capExptId: 2L),
                new  ExperimentSearch(capExptId: 3L),
                new  ExperimentSearch(capExptId: 4L)]
        final MolSpreadSheetData molSpreadSheetData = new MolSpreadSheetData()
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'a')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'b')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'c')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'d')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'e',unitsInColumn: "uM"),
                new MolSpreadSheetColSubHeader(columnTitle:'f',unitsInColumn: "uM"),
                new MolSpreadSheetColSubHeader(columnTitle:'g')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'h',unitsInColumn: "uM"),
                new MolSpreadSheetColSubHeader(columnTitle:'i',unitsInColumn: "pM"),
                new MolSpreadSheetColSubHeader(columnTitle:'j')])
        molSpreadSheetData.rowPointer[47 as Long]  =  1
        molSpreadSheetData.rowPointer[48 as Long]  =  2
        molSpreadSheetData.rowPointer[49 as Long]  =  3
        molSpreadSheetData.experimentNameList << 'a'
        MolSpreadSheetCell molSpreadSheetCell0 = new MolSpreadSheetCell("2.1", MolSpreadSheetCellType.numeric)
        molSpreadSheetCell0.spreadSheetActivityStorage = new SpreadSheetActivityStorage()
        MolSpreadSheetCell molSpreadSheetCell1 = new MolSpreadSheetCell("2.1", MolSpreadSheetCellType.numeric)
        molSpreadSheetCell1.spreadSheetActivityStorage = new SpreadSheetActivityStorage()
        MolSpreadSheetCell molSpreadSheetCell2 = new MolSpreadSheetCell("2.1", MolSpreadSheetCellType.numeric)
        molSpreadSheetCell2.spreadSheetActivityStorage = new SpreadSheetActivityStorage()
        molSpreadSheetData.mssData.put("0_4", molSpreadSheetCell0)
        molSpreadSheetData.mssData.put("1_4", molSpreadSheetCell1)
        molSpreadSheetData.mssData.put("2_4", molSpreadSheetCell2)
        molSpreadSheetData.mssData.put("0_5", molSpreadSheetCell0)
        molSpreadSheetData.mssData.put("1_5", molSpreadSheetCell1)
        molSpreadSheetData.mssData.put("2_5", molSpreadSheetCell2)
        service.prepareMapOfColumnsToAssay(molSpreadSheetData,experimentList)
        println 'hello'

        then: "we want to pull out the active values"
        assertNotNull molSpreadSheetData
        molSpreadSheetData.mapColumnsToAssay.size() == 10
        molSpreadSheetData.mssHeaders.size() == 6
        molSpreadSheetData.mssHeaders[4].molSpreadSheetColSubHeaderList[0].columnTitle=='e'
        molSpreadSheetData.mssHeaders[4].molSpreadSheetColSubHeaderList[0].unitsInColumn=='uM'
        molSpreadSheetData.mssHeaders[4].molSpreadSheetColSubHeaderList[0].unitsInColumnAreUniform==false
        molSpreadSheetData.mssHeaders[4].molSpreadSheetColSubHeaderList[1].columnTitle=='f'
        molSpreadSheetData.mssHeaders[4].molSpreadSheetColSubHeaderList[1].unitsInColumn=='uM'
        molSpreadSheetData.mssHeaders[4].molSpreadSheetColSubHeaderList[1].unitsInColumnAreUniform==false
        molSpreadSheetData.mssHeaders[4].molSpreadSheetColSubHeaderList[2].columnTitle=='g'
        molSpreadSheetData.mssHeaders[4].molSpreadSheetColSubHeaderList[2].unitsInColumnAreUniform==true
        molSpreadSheetData.mssHeaders[5].molSpreadSheetColSubHeaderList[0].columnTitle=='h'
        molSpreadSheetData.mssHeaders[5].molSpreadSheetColSubHeaderList[0].unitsInColumn=='uM'
        molSpreadSheetData.mssHeaders[5].molSpreadSheetColSubHeaderList[0].unitsInColumnAreUniform==true
        molSpreadSheetData.mssHeaders[5].molSpreadSheetColSubHeaderList[1].columnTitle=='i'
        molSpreadSheetData.mssHeaders[5].molSpreadSheetColSubHeaderList[1].unitsInColumn=='pM'
        molSpreadSheetData.mssHeaders[5].molSpreadSheetColSubHeaderList[1].unitsInColumnAreUniform==true
        molSpreadSheetData.mssHeaders[5].molSpreadSheetColSubHeaderList[2].columnTitle=='j'
        molSpreadSheetData.mssHeaders[5].molSpreadSheetColSubHeaderList[2].unitsInColumn==null
        molSpreadSheetData.mssHeaders[5].molSpreadSheetColSubHeaderList[2].unitsInColumnAreUniform==true
    }





    void "test prepareMapOfColumnsToAssay min max determination"() {
        when:
        List<ExperimentSearch> experimentList = [new  ExperimentSearch(capExptId: 0L),
                new  ExperimentSearch(capExptId: 1L),
                new  ExperimentSearch(capExptId: 2L),
                new  ExperimentSearch(capExptId: 3L),
                new  ExperimentSearch(capExptId: 4L)]
        final MolSpreadSheetData molSpreadSheetData = new MolSpreadSheetData()
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'a')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'b')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'c')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'d')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'e',unitsInColumn: "uM"),
                new MolSpreadSheetColSubHeader(columnTitle:'f',unitsInColumn: "uM"),
                new MolSpreadSheetColSubHeader(columnTitle:'g')])
        molSpreadSheetData.mssHeaders << new MolSpreadSheetColumnHeader (molSpreadSheetColSubHeaderList:[new MolSpreadSheetColSubHeader(columnTitle:'h',unitsInColumn: "uM"),
                new MolSpreadSheetColSubHeader(columnTitle:'i',unitsInColumn: "pM"),
                new MolSpreadSheetColSubHeader(columnTitle:'j')])
        molSpreadSheetData.rowPointer[47 as Long]  =  1
        molSpreadSheetData.rowPointer[48 as Long]  =  2
        molSpreadSheetData.rowPointer[49 as Long]  =  3
        molSpreadSheetData.experimentNameList << 'a'
        MolSpreadSheetCell molSpreadSheetCell0 = new MolSpreadSheetCell("2.1", MolSpreadSheetCellType.numeric)
        molSpreadSheetCell0.spreadSheetActivityStorage = new SpreadSheetActivityStorage()
        molSpreadSheetCell0.spreadSheetActivityStorage.hillCurveValueHolderList =[(new HillCurveValueHolder(response: [20d, 10d, 30d], conc: [0.01d, 0.1d, 1d])) ]
         MolSpreadSheetCell molSpreadSheetCell1 = new MolSpreadSheetCell("2.1", MolSpreadSheetCellType.numeric)
        molSpreadSheetCell1.spreadSheetActivityStorage = new SpreadSheetActivityStorage()
        molSpreadSheetCell1.spreadSheetActivityStorage.hillCurveValueHolderList =[(new HillCurveValueHolder(response: [ 0.8d, 0.7d, 0.9d], conc: [0.01d, 0.1d, 1d])) ]
        MolSpreadSheetCell molSpreadSheetCell2 = new MolSpreadSheetCell("2.1", MolSpreadSheetCellType.numeric)
        molSpreadSheetCell2.spreadSheetActivityStorage = new SpreadSheetActivityStorage()
        molSpreadSheetData.mssData.put("0_4", molSpreadSheetCell0)
        molSpreadSheetData.mssData.put("1_4", molSpreadSheetCell1)
        molSpreadSheetData.mssData.put("2_4", molSpreadSheetCell2)
        molSpreadSheetData.mssData.put("0_5", molSpreadSheetCell0)
        molSpreadSheetData.mssData.put("1_5", molSpreadSheetCell1)
        molSpreadSheetData.mssData.put("2_5", molSpreadSheetCell2)
        service.prepareMapOfColumnsToAssay(molSpreadSheetData,experimentList)
        println 'hello'

        then: "we want to pull out the active values"
        assertNotNull molSpreadSheetData
        molSpreadSheetData.mapColumnsToAssay.size() == 10
        molSpreadSheetData.mssHeaders.size() == 6
        molSpreadSheetData.mssHeaders[4].molSpreadSheetColSubHeaderList[0].minimumResponse == 0.7d
        molSpreadSheetData.mssHeaders[4].molSpreadSheetColSubHeaderList[0].maximumResponse == 30d
        molSpreadSheetData.mssHeaders[4].molSpreadSheetColSubHeaderList[1].minimumResponse == 0.7d
        molSpreadSheetData.mssHeaders[4].molSpreadSheetColSubHeaderList[1].maximumResponse == 30d
    }





    void "test convertSpreadSheetActivityToCompoundInformation"() {
        given:
        final List<SpreadSheetActivity> spreadSheetActivityList = []
        spreadSheetActivityList << new SpreadSheetActivity(cid: 47L)

        when:
        Map map = service.convertSpreadSheetActivityToCompoundInformation(spreadSheetActivityList)

        then:
        queryService.findCompoundsByCIDs(_,_) >> {[123:456]}

        assertNotNull map
    }



    void "test null cmp iterator in retrieveImpliedCompoundsEtagFromAssaySpecification"() {
        given:
        final List<ExperimentSearch> experimentList = []
        experimentList << new ExperimentSearch()

        when:
        String eTag = service.retrieveImpliedCompoundsEtagFromAssaySpecification(experimentList)

        then:
        compoundRestService.newETag(_, _) >> { null }
        and:
        experimentRestService.compoundsForExperiment(_) >> {[123, 456]}

        assertNull eTag
    }

    void "test empty cmp response in retrieveImpliedCompoundsEtagFromAssaySpecification"() {
        given:
        final List<ExperimentSearch> experimentList = []
        experimentList << new ExperimentSearch()

        when:
        String eTag = service.retrieveImpliedCompoundsEtagFromAssaySpecification(experimentList)

        then:
        compoundRestService.newETag(_, _) >> { null }
        and:
        experimentRestService.compoundsForExperiment(_) >> {[]}

        assertNull eTag
    }


    void "test populateMolSpreadSheetData"() {
        given:
        final List<ExperimentSearch> experimentList = [new  ExperimentSearch(capExptId: 0L),
                new  ExperimentSearch(capExptId: 1L),
                new  ExperimentSearch(capExptId: 2L),
                new  ExperimentSearch(capExptId: 3L),
                new  ExperimentSearch(capExptId: 4L)]
        experimentList << new ExperimentSearch()
        final List<SpreadSheetActivity> spreadSheetActivityList = []
        spreadSheetActivityList << new SpreadSheetActivity(cid: 54687454L,eid: 3997L, potency: 47L)
        spreadSheetActivityList << new SpreadSheetActivity(cid: 54687454L,eid: 3997L, potency: 47L)
        Map<String, List<MolSpreadSheetCell>> dataMap = [:]
        dataMap["0_4"]  = [new  MolSpreadSheetCell ()]
        MolSpreadSheetData molSpreadSheetData = new   MolSpreadSheetData ()
        molSpreadSheetData.rowPointer[54687454L] = 0
        molSpreadSheetData.columnPointer[3997L] = 0


        when:
        service.populateMolSpreadSheetData(molSpreadSheetData,experimentList,spreadSheetActivityList,dataMap)

        then:
        assert dataMap["0_4"]!=null
        assert dataMap["0_4"].size()==3
    }


    void "test addFixedColumnData error case"() {
        given:
        final LinkedHashMap<String, String> mapForThisRow = [:]
        final MolSpreadSheetData molSpreadSheetData = new MolSpreadSheetData()
        final int rowCnt = 1
        when:
        service.addFixedColumnData(mapForThisRow, molSpreadSheetData, rowCnt)
        then:
        assert mapForThisRow.size() == 3
        assert mapForThisRow["molstruct"] == "Unknown smiles"
        assert mapForThisRow["cid"] == "-"
        assert mapForThisRow["c3"] == "-"


    }



    void "test addFixedColumnData"() {
        given:
        final LinkedHashMap<String, String> mapForThisRow = [:]
        final MolSpreadSheetData molSpreadSheetData = new MolSpreadSheetData()
        final int rowCnt = 0
        when:
        service.addFixedColumnData(mapForThisRow, molSpreadSheetData, rowCnt)
        then:
        assert mapForThisRow.size() == 3
        assert mapForThisRow["molstruct"] == "Unknown smiles"
        assert mapForThisRow["cid"] == "-"
        assert mapForThisRow["c3"] == "-"


    }

    void "test assaysToExperiments with an empty list of assays"() {
        when:
        List<ExperimentSearch> experimentSearches = service.assaysToExperiments([])
        then:
        0 * assayRestService.findExperimentsByAssayId(_) >> {[new ExperimentSearch()]}
        assert !experimentSearches
    }

    void "test projectIdsToExperiments"() {
        given:
        final List<Long> pids = [2, 4]
        Map<Long, Long> mapExperimentIdsToCapAssayIds = [:]
        Map<Long, String> mapCapAssayIdsToAssayNames = [:]
        when:
        final List<ExperimentSearch> experimentSearches = service.projectIdsToExperiments(pids, mapExperimentIdsToCapAssayIds,mapCapAssayIdsToAssayNames)
        then:
        projectRestService.searchProjectsByIds(_) >> {projectResult}
        expectedNumberOfMethodCalls * experimentRestService.searchExperimentsByIds(_) >> { new ExperimentSearchResult(experiments: [new ExperimentSearch()])}
        assert experimentSearches.size() == expectedSize
        where:
        label                              | projectResult                                         | expectedSize | expectedNumberOfMethodCalls
        "Return list of experiments"       | new ProjectResult(projects: [new Project(eids: [1])]) | 1            | 1
        "Return empty list of experiments" | null                                                  | 0            | 0
    }

    void "test projectsToExperiments #label"() {
        when:
        Map<Long, Long> mapExperimentIdsToCapAssayIds = [:]
        Map<Long, String> mapCapAssayIdsToAssayNames = [:]
        List<ExperimentSearch> experiments = service.projectsToExperiments(projectResult, mapExperimentIdsToCapAssayIds,mapCapAssayIdsToAssayNames)
        then:
        expectedNumberOfMethodCalls * experimentRestService.searchExperimentsByIds(_) >> { new ExperimentSearchResult(experiments: [new ExperimentSearch()])}
        assert experiments.size() == expectedSize
        where:
        label                              | projectResult                                         | expectedSize | expectedNumberOfMethodCalls
        "With ExperimentSearchResult"      | new ProjectResult(projects: [new Project(eids: [1])]) | 1            | 1
        "With Null ExperimentSearchResult" | null                                                  | 0            | 0


    }

    void "test projectToExperiment #label"() {
        given:
        final List<Long> eids = [2, 4]
        final List<ExperimentSearch> allExperiments = []
        when:
        service.projectToExperiment(eids, allExperiments)
        then:
        experimentRestService.searchExperimentsByIds(_) >> {experimentSearchResult}
        allExperiments.size() == expectedSize
        where:
        label                              | experimentSearchResult                                            | expectedSize
        "With ExperimentSearchResult"      | new ExperimentSearchResult(experiments: [new ExperimentSearch()]) | 1
        "With Null ExperimentSearchResult" | null                                                              | 0
    }


    void "test populateMolSpreadSheetColumnMetadata when experiment list is empty"() {
        given: "we have an experiment"
        final MolSpreadSheetData molSpreadSheetData = new MolSpreadSheetData()
        final List<ExperimentSearch> experimentList = []

        when: "we want to pull out the active values"
        service.populateMolSpreadSheetColumnMetadata(molSpreadSheetData, experimentList)

        then: "prove that the active values are available"
        assertNotNull molSpreadSheetData
        assertNotNull molSpreadSheetData.mssHeaders

        assert molSpreadSheetData.getColumns().size() == 4
        assert molSpreadSheetData.getColumns().contains("Struct")
        assert molSpreadSheetData.getColumns().contains("CID")
        assert molSpreadSheetData.getColumns().contains("UNM Promiscuity Analysis")
        assert molSpreadSheetData.getColumns().contains("Active vs Tested across all Assay Definitions")
    }


    void "test populateMolSpreadSheetColumnMetadata when experiment list is empty and mssheader is null"() {
        given: "we have an experiment"
        final MolSpreadSheetData molSpreadSheetData = new MolSpreadSheetData()
        final List<ExperimentSearch> experimentList = []

        when: "we want to pull out the active values"
        service.populateMolSpreadSheetColumnMetadata(molSpreadSheetData, experimentList)

        then: "prove that the active values are available"
        assertNotNull molSpreadSheetData
        assertNotNull molSpreadSheetData.mssHeaders
        assert molSpreadSheetData.getColumnCount() == 4
        assert molSpreadSheetData.getColumns().contains("Struct")
        assert molSpreadSheetData.getColumns().contains("CID")
        assert molSpreadSheetData.getColumns().contains("UNM Promiscuity Analysis")
        assert molSpreadSheetData.getColumns().contains("Active vs Tested across all Assay Definitions")
    }


    void "test populateMolSpreadSheetColumnMetadata when experiment list is not empty"() {
        given: "we have an experiment"
        final MolSpreadSheetData molSpreadSheetData = new MolSpreadSheetData()
        final List<ExperimentSearch> experimentList = []
        experimentList << new ExperimentSearch(name: "a")
        experimentList << new ExperimentSearch(name: "b")
        experimentList << new ExperimentSearch(name: "c")

        when: "we want to pull out the active values"
        service.populateMolSpreadSheetColumnMetadata(molSpreadSheetData, experimentList)
        molSpreadSheetData.mssHeaders[4].molSpreadSheetColSubHeaderList << new MolSpreadSheetColSubHeader(columnTitle : 'a')
        molSpreadSheetData.mssHeaders[5].molSpreadSheetColSubHeaderList << new MolSpreadSheetColSubHeader(columnTitle : 'b')
        molSpreadSheetData.mssHeaders[6].molSpreadSheetColSubHeaderList << new MolSpreadSheetColSubHeader(columnTitle : 'c' )
        molSpreadSheetData.mssHeaders[6].molSpreadSheetColSubHeaderList << new MolSpreadSheetColSubHeader(columnTitle : 'd' )

        then: "prove that the active values are available"
        assertNotNull molSpreadSheetData
        assertNotNull molSpreadSheetData.mssHeaders

        assert molSpreadSheetData.getColumnCount() == 8
        assert molSpreadSheetData.getColumns().contains("Struct")
        assert molSpreadSheetData.getColumns().contains("CID")
        assert molSpreadSheetData.getColumns().contains("UNM Promiscuity Analysis")
    }




    void "test populateMolSpreadSheetRowMetadata"() {
        given: "we have an experiment"
        final MolSpreadSheetData molSpreadSheetData = new MolSpreadSheetData()
        molSpreadSheetData.rowPointer = new LinkedHashMap<Long, Integer>()
        molSpreadSheetData.mssData = new LinkedHashMap<String, MolSpreadSheetCell>()
        //  List<CartCompound> cartCompoundList = []
        //cartCompoundList.add(new CartCompound("c1ccccc1", "benzene", 47, 0, 0))
        Map<String, MolSpreadSheetCell> dataMap = [:]

        // final MolSpreadSheetData molSpreadSheetData, final Map compoundAdapterMap, Map<String, MolSpreadSheetCell> dataMap
        when: "we want to pull out the active values"
        service.populateMolSpreadSheetRowMetadata(molSpreadSheetData, [:], dataMap)

        then: "prove that the active values are available"
        assertNotNull molSpreadSheetData
        assertNotNull molSpreadSheetData.mssHeaders
        assert molSpreadSheetData.getColumnCount() == 0
    }


    void "test populateMolSpreadSheetRowMetadata compoundAdapterMap"() {
        given: "we have an experiment"
        final MolSpreadSheetData molSpreadSheetData = new MolSpreadSheetData()
        molSpreadSheetData.rowPointer = new LinkedHashMap<Long, Integer>()
        molSpreadSheetData.mssData = new LinkedHashMap<String, MolSpreadSheetCell>()
        Map<String, MolSpreadSheetCell> dataMap = [:]

        when: "we want to pull out the active values"
        service.populateMolSpreadSheetRowMetadata(molSpreadSheetData, compoundAdapterMap, dataMap)

        then: "prove that the active values are available"
        assertNotNull molSpreadSheetData
        assertNotNull molSpreadSheetData.mssHeaders
        assert molSpreadSheetData.getColumnCount() == 0
    }






    CompoundAdapter buildCompoundAdapter(final Long cid) {
        final Compound compound = new Compound()

        compound.smiles = 'c1ccccc1'
        compound.cid = cid
        compound.name = 'name'
        compound.numActiveAssay = new Integer("1")
        compound.numAssay = new Integer("5")
        return new CompoundAdapter(compound)
    }


}
