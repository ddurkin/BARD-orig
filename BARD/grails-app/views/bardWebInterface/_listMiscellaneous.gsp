<%@ page import="bard.core.rest.spring.assays.Annotation; bard.core.rest.spring.assays.Annotation" %>
<div id="cardHolderMisc">
    <div class="roundedBorder card-group">
        <div class="row-fluid">
            <g:each in="${Annotation.splitForColumnLayout(annotations.otherAnnotations)}" var="contextColumnList">
                <div class="span6">
                    <g:each in="${contextColumnList}" var="contextItem">
                        <g:render template="showMiscItems" model="[contextItem: contextItem]"/>
                    </g:each>
                </div>
            </g:each>
        </div>
    </div>
</div>