<g:hiddenField name="version" id="versionId" value="${assay.version}"/>
<div class="row-fluid">
    <div class="span9">
        <dl class="dl-horizontal">
            <dt><g:message code="assay.id.label" default="ADID"/>:</dt>
            <dd>${assay?.id}</dd>


            <dt><g:message code="assay.assayStatus.label" default="Fix i18n"/>:</dt>
            <dd>
                <a href="#"
                   data-sourceCache="true"
                   class="status ${assay?.assayStatus?.id}"
                   data-toggle="manual"
                   id="${assay?.assayStatus?.id}"
                   data-type="select"
                   data-value="${assay?.assayStatus?.id}"
                   data-source="/BARD/assayDefinition/assayStatus"
                   data-pk="${assay.id}"
                   data-url="/BARD/assayDefinition/editAssayStatus"
                   data-original-title="Select Assay Status">${assay?.assayStatus?.id}</a>
                <i class="icon-pencil documentPencil" title="Click to edit Status" data-id="${assay?.assayStatus?.id}"></i>
            </dd>

            <dt><g:message code="assay.assayName.label" default="Fix i18n"/>:</dt>
            <dd>
                <a href="#"
                   class="assayNameY"
                   id="nameId"
                   data-toggle="manual"
                   data-type="text"
                   data-value="${assay?.assayName}"
                   data-pk="${assay.id}"
                   data-url="/BARD/assayDefinition/editAssayName"
                   data-placeholder="Required"
                   data-original-title="Edit Assay Name">${assay?.assayName}</a>
                <i class="icon-pencil documentPencil" title="Click to edit Name" data-id="nameId"></i>
            </dd>

            <dt><g:message code="assay.assayShortName.label" default="Fix i18n"/>:</dt>
            <dd id="shortNameId"><g:fieldValue bean="${assay}" field="assayShortName"/></dd>


            <dt><g:message code="assay.designedBy.label" default="Fix i18n"/>:</dt>
            <dd>
                <a href="#"
                   class="designedBy"
                   id="designedById"
                   data-toggle="manual"
                   data-type="text"
                   data-value="${assay?.designedBy}"
                   data-pk="${assay.id}"
                   data-url="/BARD/assayDefinition/editDesignedBy"
                   data-placeholder="Required"
                   data-original-title="Edit Designed By">${assay?.designedBy}</a>
                <i class="icon-pencil documentPencil" data-id="designedById" title="Click to edit designed by"></i>
            </dd>
            <dt>Owner:</dt>
            <dd>${assay?.designedBy}</dd>

            <dt><g:message code="default.dateCreated.label"/>:</dt>
            <dd><g:formatDate date="${assay.dateCreated}" format="MM/dd/yyyy"/></dd>
            <dt><g:message code="assay.assayType.label" default="Fix i18n"/>:</dt>
            <dd>
                <a href="#"
                   class="type"
                   data-toggle="manual"
                   data-sourceCache="true"
                   id="${assay?.assayType?.id}"
                   data-type="select"
                   data-value="${assay?.assayType?.id}"
                   data-source="/BARD/assayDefinition/assayTypes"
                   data-pk="${assay.id}"
                   data-url="/BARD/assayDefinition/editAssayType"
                   data-original-title="Select Assay Type">${assay?.assayType?.id}</a>
                <i class="icon-pencil documentPencil"  data-id="${assay?.assayType?.id}" title="Click to edit type"></i>
            </dd>
           <dt><g:message code="assay.assayVersion.label" default="Fix i18n"/>:</dt>
            <dd><g:fieldValue bean="${assay}" field="assayVersion"/></dd>

            <dt><g:message code="default.lastUpdated.label"/>:</dt>
            <dd id="lastUpdatedId"><g:formatDate date="${assay.lastUpdated}" format="MM/dd/yyyy"/></dd>

            <dt><g:message code="default.modifiedBy.label"/>:</dt>
            <dd id="modifiedById"><g:fieldValue bean="${assay}" field="modifiedBy"/></dd>
        </dl>
    </div>
</div>