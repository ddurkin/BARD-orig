<g:if test="${currentCard || (subTemplate.equals("edit") && contextOwner instanceof bard.db.registration.Assay)}">

    %{--<g:if test="${contextOwner instanceof bard.db.registration.Assay}">--}%
        %{--<g:render template="../contextItem/${subTemplate}GroupHeader"--}%
                  %{--model="[contextOwner: contextOwner, cardSection: currentCard.key]"/>--}%
    %{--</g:if>--}%
    %{--<p>--}%
        %{--${currentCard.description}--}%
    %{--</p>--}%

    <div class="row-fluid">
        <g:each in="${contextOwner.splitForColumnLayout(currentCard.value)}" var="contextColumnList">
            <div class="span6">
                <g:each in="${contextColumnList}" var="context">
                    <g:render template="../contextItem/${subTemplate}"
                              model="[contextOwner: contextOwner,
                                      context: context,
                                      cardSection: currentCard.key]"/>
                </g:each>
            </div>
        </g:each>
    </div>
</g:if>