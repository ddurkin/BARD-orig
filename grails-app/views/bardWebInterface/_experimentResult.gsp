<%--
  Created by IntelliJ IDEA.
  User: gwalzer
  Date: 9/21/12
  Time: 10:55 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="bard.core.ExperimentValues; bard.core.AssayValues" contentType="text/html;charset=UTF-8" %>

<p><b>Title: ${experimentDataMap?.experiment?.name}</b></p>

<p><b>Assay ID:<g:link controller="bardWebInterface" action="showAssay"
                       id="${experimentDataMap?.experiment?.assay?.id}">${experimentDataMap?.experiment?.assay?.id}</g:link></b></p>

<div class="row-fluid">
    <table class="table table-condensed">
        <thead>
        <tr>
            <th>SID</th>
            <th>CID</th>
            <th>Structure</th>
            <th>Activity</th>
            <th>Outcome</th>
            <th>Potency</th>
            <g:if test="${experimentDataMap?.role && (experimentDataMap?.role != ExperimentValues.ExperimentRole.Primary)}">
                <th>Curve</th>
            </g:if>
        </tr>
        </thead>
        <g:each in="${experimentDataMap?.spreadSheetActivities}" var="experimentData">
            <tr>
                <td><a href="http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?sid=${experimentData.sid}">${experimentData.sid}</a>
                </td>
                <td><a href="${createLink(controller: 'bardWebInterface', action: 'showCompound', params: [cid: experimentData.cid])}">${experimentData.cid}</a>
                </td>
                <td style="min-width: 180px;">
                    <img alt="SID: ${experimentData.sid}" title="SID: ${experimentData.sid}"
                         src="${createLink(controller: 'chemAxon', action: 'generateStructureImage', params: [cid: experimentData.cid, width: 180, height: 150])}"/>
                </td>
                <td>
                    <g:each in="${0..(experimentData.hillCurveValue.size() - 1)}" var="i">
                        ${experimentData.hillCurveValue.response[i]} @ ${experimentData.hillCurveValue.conc[i]}
                        <br/>
                    </g:each>
                </td>
                <td>${experimentData.activityOutcome?.label}</td>
                <td>${experimentData.potency}</td>
                <td>
                    <g:if test="${experimentDataMap?.role && (experimentDataMap?.role != ExperimentValues.ExperimentRole.Primary)}">
                        <img alt="" title=""
                             src="${createLink(controller: 'doseResponseCurve', action: 'doseResponseCurve', params: [sinf: experimentData.hillCurveValue.sInf, s0: experimentData.hillCurveValue.s0, ac50: experimentData.hillCurveValue.slope, hillSlope: experimentData.hillCurveValue.coef, concentrations: experimentData.hillCurveValue.conc, activities: experimentData.hillCurveValue.response])}"/>
                        <br/><br/>

                        <p>AC50 = ${experimentData.hillCurveValue.slope}</p>
                    </g:if>
                </td>
            </tr>
        </g:each>
    </table>

    <div class="pagination">
        <g:paginate total="${experimentDataMap?.total ? experimentDataMap?.total : 0}" params='[id: "${params?.id}"]'/>
    </div>
</div>
