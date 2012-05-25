package barddataexport.cap

import groovy.sql.Sql
import groovy.xml.MarkupBuilder

import javax.sql.DataSource

class CapExportService {
    DataSource dataSource
    final static String STAGE_MEDIA_TYPE = "application/vnd.bard.cap+xml;type=stage"
    final static String STAGE_BASE_URL = "http://bard/dictionary/stage/"
    final static String PROTOCOL_DOCUMENT_BASE_URL = "http://bard/protocolDocument/"
    final static String RESULT_TYPE_BASE_URL = "http://bard/dictionary/resultType/"
    final static String RESULT_TYPE_MEDIA_TYPE = "application/vnd.bard.cap+xml;type=resultType"
    final static String ELEMENT_BASE_URL = "http://bard/dictionary/element/"
    final static String ELEMENT_MEDIA_TYPE = "application/vnd.bard.cap+xml;type=element"
    final static String PROJECT_BASE_URL = "http://bard/dictionary/projects/"
    final static String PROJECT_MEDIA_TYPE = "application/vnd.bard.cap+xml;type=project"

    /**
     * Generate a measure contexts
     * @param sql
     * @param xml
     */
    protected void generateMeasureContexts(final Sql sql, final MarkupBuilder xml, final BigDecimal assayId) {

        xml.measureContexts() {
            sql.eachRow('select MEASURE_CONTEXT_ID,CONTEXT_NAME from MEASURE_CONTEXT WHERE ASSAY_ID=' + assayId) { row ->
                generateMeasureContext(xml, row.MEASURE_CONTEXT_ID, row.CONTEXT_NAME)
            }
        }
    }

    protected void generateMeasureContext(final MarkupBuilder xml, final BigDecimal measureContextId, final String contextNameText) {

        xml.measureContext(measureContextId: measureContextId) {
            contextName(contextNameText)
        }
    }

    protected void generateMeasures(final Sql sql, final MarkupBuilder xml, final BigDecimal assayId) {

        xml.measures() {
            sql.eachRow('select MEASURE_ID,MEASURE_CONTEXT_ID,RESULT_TYPE_ID,ENTRY_UNIT,PARENT_MEASURE_ID from MEASURE WHERE ASSAY_ID=' + assayId) { row ->
                final MeasureDTO measureDTO = new MeasureDTO(row.MEASURE_ID, row.MEASURE_CONTEXT_ID, row.RESULT_TYPE_ID, row.PARENT_MEASURE_ID, row.ENTRY_UNIT)
                generateMeasure(xml, measureDTO)
            }
        }
    }

    protected void generateMeasure(final MarkupBuilder xml, final MeasureDTO measureDTO) {

        def attributes = [:]

        if (measureDTO.measureId || measureDTO.measureId.toString().isInteger()) {
            attributes.put('measureId', measureDTO.measureId)
        }


        if (measureDTO.measureContextId || measureDTO.measureContextId.toString().isInteger()) {
            attributes.put('measureContextRef', measureDTO.measureContextId)
        }
        if (measureDTO.measureId || measureDTO.measureId.toString().isInteger()) {
            attributes.put('measureRef', measureDTO.measureId)
        }
        if (measureDTO.entryUnit) {
            attributes.put('entryUnit', measureDTO.entryUnit)
        }
        xml.measure(attributes) {
            if (measureDTO.resultTypeId || measureDTO.resultTypeId.toString().isInteger()) {
                resultTypeRef() {
                    link(rel: 'related', href: "${RESULT_TYPE_BASE_URL}${measureDTO.resultTypeId}", type: "${RESULT_TYPE_MEDIA_TYPE}")
                }
            }
        }
    }

    protected void generateMeasureContextItems(final Sql sql, final MarkupBuilder xml, final BigDecimal assayId) {

        xml.measureContextItems() {
            sql.eachRow('select MEASURE_CONTEXT_ITEM_ID,GROUP_MEASURE_CONTEXT_ITEM_ID,MEASURE_CONTEXT_ID,ATTRIBUTE_TYPE,ATTRIBUTE_ID,QUALIFIER,VALUE_ID,VALUE_DISPLAY,VALUE_NUM,VALUE_MIN,VALUE_MAX from MEASURE_CONTEXT_ITEM WHERE ASSAY_ID=' + assayId) { row ->
                MeasureContextItemDTO dto = new MeasureContextItemDTO(
                        row.MEASURE_CONTEXT_ITEM_ID,
                        row.GROUP_MEASURE_CONTEXT_ITEM_ID,
                        row.MEASURE_CONTEXT_ID,
                        row.ATTRIBUTE_ID,
                        row.VALUE_ID,
                        row.VALUE_NUM,
                        row.VALUE_MIN,
                        row.VALUE_MAX,
                        row.VALUE_DISPLAY,
                        row.QUALIFIER,
                        row.ATTRIBUTE_TYPE)
                generateMeasureContextItem(xml, dto)
            }
        }

    }

    protected void generateMeasureContextItem(final MarkupBuilder xml, final MeasureContextItemDTO dto) {
        def attributes = [:]

        if (dto.measureContextItemId || dto.measureContextItemId.toString().isInteger()) {
            attributes.put('measureContextItemId', dto.measureContextItemId)
        }
        if (dto.groupMeasureContextItemId || dto.groupMeasureContextItemId.toString().isInteger()) {
            attributes.put('measureContextItemRef', dto.groupMeasureContextItemId)
        }
        if (dto.measureContextId || dto.measureContextId.toString().isInteger()) {
            attributes.put('measureContextRef', dto.measureContextId)
        }
        if (dto.qualifier) {
            attributes.put('qualifier', dto.qualifier)
        }
        attributes.put('attributeType', dto.attributeType)

        if (dto.valueDisplay) {
            attributes.put('valueDisplay', dto.valueDisplay)
        }
        if (dto.valueNum || dto.valueNum.toString().isInteger()) {
            attributes.put('valueNum', dto.valueNum)
        }
        if (dto.valueMin || dto.valueMin.toString().isInteger()) {
            attributes.put('valueMin', dto.valueMin)
        }
        if (dto.valueMax || dto.valueMax.toString().isInteger()) {
            attributes.put('valueMax', dto.valueMax)
        }


        xml.measureContextItem(attributes) {
            if (dto.valueId || dto.valueId.toString().isInteger()) {
                valueId() {
                    link(rel: 'related', href: "${ELEMENT_BASE_URL}${dto.valueId}", type: "${ELEMENT_MEDIA_TYPE}")
                }
            }
            if (dto.attributeId || dto.attributeId.toString().isInteger()) {
                attributeId() {
                    link(rel: 'related', href: "${ELEMENT_BASE_URL}${dto.attributeId}", type: "${ELEMENT_MEDIA_TYPE}")
                }
            }
        }

    }

    protected void generateExternalAssays(final Sql sql,
                                          final MarkupBuilder xml, final BigDecimal assayId) {

        xml.externalAssays() {
            sql.eachRow('select EXTERNAL_SYSTEM_ID,EXT_ASSAY_ID from EXTERNAL_ASSAY WHERE ASSAY_ID=' + assayId) { externalAssayRow ->
                generateExternalAssay(sql, xml, externalAssayRow.EXT_ASSAY_ID, externalAssayRow.EXTERNAL_SYSTEM_ID)
            }
        }
    }

    protected void generateExternalAssay(final Sql sql,
                                         final MarkupBuilder xml,
                                         final String externalAssayId,
                                         final BigDecimal externalSystemId) {


        xml.externalAssay(externalAssayId: externalAssayId) {
            if (externalSystemId) {
                sql.eachRow('select SYSTEM_NAME,OWNER,SYSTEM_URL from EXTERNAL_SYSTEM WHERE EXTERNAL_SYSTEM_ID=' + externalSystemId) { externalSystemRow ->
                    def attributes = [:]

                    if (externalSystemRow.SYSTEM_URL) {
                        attributes.put('systemUrl', externalSystemRow.SYSTEM_URL + externalAssayId)
                    }
                    externalSystem(attributes) {
                        if (externalSystemRow.SYSTEM_NAME) {
                            systemName(externalSystemRow.SYSTEM_NAME)
                        }
                        if (externalSystemRow.OWNER) {
                            owner(externalSystemRow.OWNER)
                        }
                    }
                }
            }
        }
    }



    protected void generateProtocolDocuments(final Sql sql,
                                             final MarkupBuilder xml, final BigDecimal assayId) {
        xml.protocolDocuments() {
            sql.eachRow('select PROTOCOL_ID,PROTOCOL_NAME,PROTOCOL_DOCUMENT from PROTOCOL WHERE ASSAY_ID=' + assayId) { documentRow ->
                generateProtocolDocument(xml, documentRow.PROTOCOL_NAME, documentRow.PROTOCOL_ID)
            }
        }
    }
    /**
     * Should we expose this as a service?
     * @param xml
     * @param protocolNameText
     * @param protocolId
     */
    public void generateProtocolDocument(
            final MarkupBuilder xml, final String protocolNameText, final BigDecimal protocolId) {
        final String protocolUri = PROTOCOL_DOCUMENT_BASE_URL + protocolId
        xml.protocolDocument(uri: protocolUri) {
            protocolName(protocolNameText)
        }
    }


    protected void generateProjectAssay(final Sql sql,
                                        final MarkupBuilder xml,
                                        ProjectAssayDTO projectAssayDTO
    ) {

        def attributes = [:]
        if (projectAssayDTO.relatedAssayId || projectAssayDTO.relatedAssayId.toString().isInteger()) {
            attributes.put('relatedAssayRef', projectAssayDTO.relatedAssayId)
        }
        if (projectAssayDTO.sequenceNumber || projectAssayDTO.sequenceNumber.toString().isInteger()) {
            attributes.put('sequenceNumber', projectAssayDTO.sequenceNumber)
        }
        if (projectAssayDTO.promotionThreshold || projectAssayDTO.promotionThreshold.toString().isInteger()) {
            attributes.put('promotionThreshold', projectAssayDTO.promotionThreshold)
        }
        xml.projectAssay(
                attributes
        ) {
            if (projectAssayDTO.promotionCriteria) {
                promotionCriteria(projectAssayDTO.promotionCriteria)
            }
            if (projectAssayDTO.stageId) {
                stage() {
                    link(rel: 'related', href: "${STAGE_BASE_URL}${projectAssayDTO.stageId}", type: "${STAGE_MEDIA_TYPE}")
                }
            }

            sql.eachRow('select ASSAY_NAME,ASSAY_STATUS_ID,ASSAY_VERSION,DESCRIPTION,DESIGNED_BY from ASSAY WHERE ASSAY_ID=' + projectAssayDTO.assayId) { assayRow ->

                def assayStatusId = assayRow.ASSAY_STATUS_ID
                def assayStatus = null
                if (assayStatusId) {
                    sql.eachRow('select STATUS from ASSAY_STATUS where ASSAY_STATUS_ID=' + assayStatusId) {  assayStatusRow ->
                        assayStatus = assayStatusRow.STATUS
                    }
                }
                generateAssay(sql, xml, new AssayDTO(projectAssayDTO.projectId,projectAssayDTO.assayId, assayRow.ASSAY_VERSION, assayStatus, assayRow.ASSAY_NAME, assayRow.DESCRIPTION, assayRow.DESIGNED_BY))
            }
        }
    }



    protected void generateProjectAssays(final Sql sql, final MarkupBuilder xml, final BigDecimal projectId) {
        xml.projectAssays() {
            sql.eachRow('select PROJECT_ASSAY_ID,PROJECT_ID,ASSAY_ID,STAGE,RELATED_ASSAY_ID,SEQUENCE_NO,PROMOTION_THRESHOLD,PROMOTION_CRITERIA from PROJECT_ASSAY WHERE PROJECT_ID=' + projectId) { projectAssayRow ->

                def stage = projectAssayRow.STAGE
                def stageId = null
                if (stage) {
                    sql.eachRow("select STAGE_ID from STAGE where STAGE='" + stage + "'") {  stageRow ->
                        stageId = stageRow.STAGE_ID
                    }
                }
                final ProjectAssayDTO projectAssayDTO =
                    new ProjectAssayDTO(
                            projectAssayRow.PROJECT_ID,
                            projectAssayRow.PROJECT_ASSAY_ID,
                            projectAssayRow.RELATED_ASSAY_ID,
                            projectAssayRow.ASSAY_ID,
                            stageId,
                            projectAssayRow.SEQUENCE_NO,
                            projectAssayRow.PROMOTION_THRESHOLD,
                            projectAssayRow.PROMOTION_CRITERIA)
                generateProjectAssay(sql,
                        xml, projectAssayDTO)
            }

        }

    }

    protected void generateProject(Sql sql, final MarkupBuilder xml, ProjectDTO projectDTO) {
        def attributes = [:]
        attributes.put('projectId', projectDTO.projectId)
        if (projectDTO.groupType) {
            attributes.put('groupType', projectDTO.groupType)
        }
        xml.project(
                attributes
        ) {
            if (projectDTO.projectName) {
                projectName(projectDTO.projectName)
            }
            if (projectDTO.description) {
                description(projectDTO.description)
            }
            generateProjectAssays(sql, xml, projectDTO.projectId)
        }

    }



    protected void generateProjects(final Sql sql, final MarkupBuilder xml, final boolean onlyNewProjects) {

        xml.projects() {
            String selectStatement
            if (onlyNewProjects) {
                selectStatement = 'SELECT PROJECT_ID, PROJECT_NAME, GROUP_TYPE, DESCRIPTION FROM PROJECT WHERE IS_DIRTY=0'
            }
            else {
                selectStatement = 'SELECT PROJECT_ID, PROJECT_NAME, GROUP_TYPE, DESCRIPTION from PROJECT'
            }
            sql.eachRow(selectStatement) { row ->
                generateProject(sql, xml, new ProjectDTO(row.PROJECT_ID, row.GROUP_TYPE, row.PROJECT_NAME, row.DESCRIPTION))
            }
        }
    }
    /**
     * Root node for generating the entire cap
     * @return cap as XML
     */
    protected String generateCap(final Sql sql, final MarkupBuilder xml) {
        xml.cap() {
            generateProjects(sql, xml, false)
        }
    }

    // These are the methods called from the Controller

    public void generateProject(final MarkupBuilder xml, final BigDecimal projectId) {
        final Sql sql = new Sql(dataSource)
        final String selectStatement = 'SELECT PROJECT_ID, PROJECT_NAME, GROUP_TYPE, DESCRIPTION FROM PROJECT WHERE PROJECT_ID=' + projectId

        sql.eachRow(selectStatement) { row ->
            generateProject(sql, xml, new ProjectDTO(row.PROJECT_ID, row.GROUP_TYPE, row.PROJECT_NAME, row.DESCRIPTION))
        }
    }
    /**
     * Root node for generating the entire cap
     * @return cap as XML
     */
    public String generateCap(final MarkupBuilder xml) {
        final Sql sql = new Sql(dataSource)
        generateCap(sql, xml)
    }

    public void generateNewProjects(final MarkupBuilder xml) {
        Sql sql = new Sql(dataSource)
        generateProjects(sql, xml, true)
    }

    protected void generateAssay(final Sql sql,
                                 final MarkupBuilder xml,
                                 final AssayDTO assayDTO) {

        def attributes = [:]
        attributes.put('assayId', assayDTO.assayId)
        if (assayDTO.assayVersion) {
            attributes.put('assayVersion', assayDTO.assayVersion)
        }
        if (assayDTO.assayStatus) {
            attributes.put('status', assayDTO.assayStatus)
        }

        xml.assay(attributes) {
            if (assayDTO.assayName) {
                assayName(assayDTO.assayName)
            }

            if (assayDTO.description) {
                description(assayDTO.description)
            }
            if (assayDTO.designedBy) {
                designedBy(assayDTO.designedBy)
            }

            generateExternalAssays(sql, xml, assayDTO.assayId)
            generateMeasures(sql, xml, assayDTO.assayId)
            generateMeasureContexts(sql, xml, assayDTO.assayId)
            generateMeasureContextItems(sql, xml, assayDTO.assayId)
            generateProtocolDocuments(sql, xml, assayDTO.assayId)
            link(rel: 'up', href: "${PROJECT_BASE_URL}${assayDTO.projectId}", type: "${PROJECT_MEDIA_TYPE}")
        }
    }

    public void generateAssay(
            final MarkupBuilder xml,
            final BigDecimal assayId) {
        final Sql sql = new Sql(dataSource)

        sql.eachRow('select ASSAY_NAME,ASSAY_STATUS_ID,ASSAY_VERSION,DESCRIPTION,DESIGNED_BY from ASSAY WHERE ASSAY_ID=' + assayId) { assayRow ->
            BigDecimal projectId = null
            sql.eachRow('select PROJECT_ID from PROJECT_ASSAY WHERE ASSAY_ID=' + assayId) { projectAssayRow ->
                projectId = projectAssayRow.PROJECT_ID
            }
            def assayStatusId = assayRow.ASSAY_STATUS_ID
            def assayStatus = null
            if (assayStatusId) {
                sql.eachRow('select STATUS from ASSAY_STATUS where ASSAY_STATUS_ID=' + assayStatusId) {  assayStatusRow ->
                    assayStatus = assayStatusRow.STATUS
                }
            }
            generateAssay(sql, xml, new AssayDTO(projectId,assayId, assayRow.ASSAY_VERSION, assayStatus, assayRow.ASSAY_NAME, assayRow.DESCRIPTION, assayRow.DESIGNED_BY))
        }
    }
}
class ProjectDTO {
    final BigDecimal projectId
    final String groupType
    final String projectName
    final String description

    ProjectDTO(final BigDecimal projectId, final String groupType, final String projectName, final String description) {
        this.projectId = projectId
        this.groupType = groupType
        this.projectName = projectName
        this.description = description
    }
}
class ProjectAssayDTO {
    final BigDecimal projectAssayId
    final BigDecimal projectId
    final BigDecimal relatedAssayId
    final BigDecimal assayId
    final BigDecimal stageId
    final BigDecimal sequenceNumber
    final BigDecimal promotionThreshold
    final String promotionCriteria

    ProjectAssayDTO(
            final BigDecimal projectId,
            final BigDecimal projectAssayId,
            final BigDecimal relatedAssayId,
            final BigDecimal assayId,
            final BigDecimal stageId,
            final BigDecimal sequenceNumber,
            final BigDecimal promotionThreshold,
            final String promotionCriteria) {
        this.projectId = projectId
        this.projectAssayId = projectAssayId
        this.relatedAssayId = relatedAssayId
        this.assayId = assayId
        this.stageId = stageId
        this.sequenceNumber = sequenceNumber
        this.promotionThreshold = promotionThreshold
        this.promotionCriteria = promotionCriteria
    }


}
class MeasureContextItemDTO {
    final BigDecimal measureContextItemId
    final BigDecimal groupMeasureContextItemId
    final BigDecimal measureContextId
    final BigDecimal attributeId
    final BigDecimal valueId
    final BigDecimal valueNum
    final BigDecimal valueMin
    final BigDecimal valueMax
    final String valueDisplay
    final String qualifier
    final String attributeType

    MeasureContextItemDTO(
            final BigDecimal measureContextItemId,
            final BigDecimal groupMeasureContextItemId,
            final BigDecimal measureContextId,
            final BigDecimal attributeId,
            final BigDecimal valueId,
            final BigDecimal valueNum,
            final BigDecimal valueMin,
            final BigDecimal valueMax,
            final String valueDisplay,
            final String qualifier,
            final String attributeType) {

        this.measureContextItemId = measureContextItemId
        this.groupMeasureContextItemId = groupMeasureContextItemId
        this.measureContextId = measureContextId
        this.attributeId = attributeId
        this.valueId = valueId
        this.valueNum = valueNum
        this.valueMin = valueMin
        this.valueMax = valueMax
        this.valueDisplay = valueDisplay
        this.qualifier = qualifier
        this.attributeType = attributeType
    }

}
class MeasureDTO {
    final BigDecimal measureId
    final BigDecimal measureContextId
    final BigDecimal resultTypeId
    final BigDecimal parentMeasureId
    final String entryUnit

    MeasureDTO(final BigDecimal measureId,
               final BigDecimal measureContextId,
               final BigDecimal resultTypeId,
               final BigDecimal parentMeasureId,
               final String entryUnit) {
        this.measureId = measureId
        this.measureContextId = measureContextId
        this.resultTypeId = resultTypeId
        this.parentMeasureId = parentMeasureId
        this.entryUnit = entryUnit
    }
}
class AssayDTO {
    final BigDecimal assayId
    final BigDecimal projectId
    final String assayVersion
    final String assayStatus
    final String assayName
    final String description
    final String designedBy

    AssayDTO(
            final BigDecimal projectId,
            final BigDecimal assayId,
             final String assayVersion,
             final String assayStatus,
             final String assayName,
             final String description,
             final String designedBy) {
        this.projectId = projectId
        this.assayId = assayId
        this.assayVersion = assayVersion
        this.assayStatus = assayStatus
        this.assayName = assayName
        this.description = description
        this.designedBy = designedBy
    }
}