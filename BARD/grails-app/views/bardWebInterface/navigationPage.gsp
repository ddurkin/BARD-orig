<%@ page import="bard.db.command.BardCommand" %>
<!DOCTYPE html>
<html>
<head>
    <r:require
            modules="core,bootstrap"/>
    <meta name="layout" content="basic"/>
    <title>My Submissions</title>
</head>

<body>
<div class="container-fluid">

    <div class="row-fluid">
        <div class="span12">
            <div class="navbar navbar-inverse navbar-static-top">
                <div>
                    <ul>
                        <g:if test="${isBardAdmin || isManager}">
                            <li>
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    Admin
                                </a>
                                <g:if test="${isBardAdmin}">
                                    <ul>
                                        <li class=" controller"><g:link controller="downTimeScheduler"
                                                                        action="create">Schedule Down Time</g:link></li>
                                        <li class="controller"><g:link controller="downTimeScheduler"
                                                                       action="list">View Down Times</g:link></li>
                                        <li class="controller"><g:link controller="person"
                                                                       action="list">List Person Table</g:link></li>
                                        <li class="controller"><g:link controller="assayDefinition"
                                                                       action="assayComparisonReport">Compare Assays</g:link></li>
                                        <li class="controller"><g:link controller="splitAssayDefinition"
                                                                       action="show">Split Assays</g:link></li>
                                        <li class="controller"><g:link controller="config"
                                                                       action="index">Override API URL</g:link></li>
                                        <li class="controller"><g:link controller="role"
                                                                       action="list">List Teams</g:link></li>
                                        <li class="controller"><g:link controller="role"
                                                                       action="create">Create new Team</g:link></li>
                                        <li class="controller"><g:link
                                                controller="offlineValidation">Offline Validation</g:link></li>
                                    </ul>
                                </g:if>
                                <g:if test="${isManager}">
                                    <ul>
                                        <li class="controller"><g:link controller="role"
                                                                       action="myTeams">My Teams</g:link></li>
                                    </ul>
                                </g:if>
                            </li>
                        </g:if>

                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                Assay Definitions

                            </a>
                            <ul>
                                <li class="controller"><g:link controller="assayDefinition"
                                                               action="myAssays">My Assay Definitions</g:link></li>
                            %{--//You should belong to at least one team to create--}%
                                <g:if test="${BardCommand.userRoles()}">
                                    <li class="controller"><g:link controller="assayDefinition"
                                                                   action="create">Create Assay Definition</g:link></li>
                                </g:if>
                                <li class="controller"><g:link controller="assayDefinition"
                                                               action="assayComparisonReport">Compare Assay Definitions</g:link></li>
                            </ul>
                        </li>
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                Projects

                            </a>
                            <ul>
                                <li class="controller"><g:link controller="project"
                                                               action="myProjects">My Projects</g:link></li>
                            %{--//You should belong to at least one team to create--}%
                                <g:if test="${BardCommand.userRoles()}">
                                    <li class="controller"><g:link controller="project"
                                                                   action="create">Create a New Project</g:link></li>
                                </g:if>
                            </ul>
                        </li>
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                Experiments

                            </a>
                            <ul>
                                <li class="controller"><g:link controller="experiment"
                                                               action="myExperiments">My Experiments</g:link></li>
                                <g:if test="${ownsExperiments}">
                                    <li class="controller"><g:link controller="moveExperiments"
                                                                   action="show">Move Experiments</g:link></li>
                                </g:if>

                                <li class="controller"><g:link controller="jobs"
                                                               action="index">My import jobs</g:link></li>
                                <li class="controller"><g:link controller="panelExperiment"
                                                               action="create">Create a New Panel-Experiment</g:link></li>
                            </ul>
                        </li>
                        <sec:ifAnyGranted roles="ROLE_BARD_ADMINISTRATOR,ROLE_CURATOR">
                            <li>
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    Elements

                                </a>
                                <ul>
                                    <li class="controller"><g:link controller="element"
                                                                   action="select">Edit</g:link></li>
                                </ul>

                            </li>
                        </sec:ifAnyGranted>
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                Panels

                            </a>
                            <ul>
                                <li class="controller"><g:link controller="panel"
                                                               action="myPanels">My Panels</g:link></li>
                            %{--//You should belong to at least one team to create--}%
                                <g:if test="${BardCommand.userRoles()}">
                                    <li class="controller"><g:link controller="panel"
                                                                   action="create">Create New Panel</g:link></li>
                                </g:if>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>