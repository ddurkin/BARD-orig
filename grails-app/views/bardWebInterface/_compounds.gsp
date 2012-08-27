<%@ page import="grails.converters.JSON" %>
<div class="row-fluid">
    <g:if test="${params.paging == 'NotPaging'}">
        <g:render template="facets" model="['metaData': metaData]"/>
    </g:if>
<div class="span9">
    <table class="table">
    <g:each var="compound" in="${docs}">
        <tr>
        <td>
            <img src="${createLink(controller: 'chemAxon', action: 'generateStructureImage', params: [smiles: compound.iso_smiles, width: 150, height: 120])}"/>
        </td>
        <td>
            <g:link action="showCompound" id="${compound.cid}" target="_blank">
                PubChem CID: ${compound.cid}
                <g:if test="${compound.iupac_name}">
                    - ${compound.iupac_name}
                </g:if>
            </g:link>
            <a href="/bardwebquery/sarCart/add/${compound.cid}"
               onclick="jQuery.ajax({  type:'POST',
                   data:{'id': '${compound.cid}','class': 'class bardqueryapi.CartCompound','smiles':'${compound.iupac_name}','version': '0'},
                   url:'/bardwebquery/sarCart/add',
                   success:function(data,textStatus){
                       jQuery('#sarCartRefill').html(data);
                   },
                   error:function(XMLHttpRequest,textStatus,errorThrown){
                       alert('problem adding compound')
                   }
               });
               return false;"
               action="add"
               controller="sarCart"><div class="cntrcart"><nobr><i class="icon-shopping-cart"></i> Add to Cart</nobr></div></a>
        </td>
        </tr>
    </g:each>
    </table>
    <g:hiddenField name="totalCompounds" id="totalCompounds" value="${metaData?.nhit}"/>
    <div id="listCompoundsPage">
        <div class="pagination">
            <util:remotePaginate total="${metaData ? metaData.nhit : 0}" update="listCompoundsPage" controller="bardWebInterface"
                                 action="searchCompounds" pageSizes="[10,50]"
                                 params='[searchString: "${searchString}"]'/>
        </div>
    </div>
</div>
</div>