<h3><a href="#">Assays (${assays.size()})</a></h3>
<div>
    <g:each var="assay" in="${assays}">
        <g:link controller="bardWebInterface" action="search" params="[searchString:assay,searchType:'COMPOUNDS']">${assay}</g:link>
        %{--ID: ${assayInstance?.id}, Target/pathway: Assay format: Date created:<br/> --}%
        <br/>
    </g:each>
</div>