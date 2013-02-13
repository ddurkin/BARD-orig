<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="logoSearchCartAndFooter"/>

    <r:require modules="search,promiscuity,compoundOptions"/>

</head>

<body>
<div class="row-fluid">
    <div class="span12">
        <div id="resultTab">

            <ul id="resultTabUL" class="nav nav-tabs">
                <li class="active" id="assaysTabLi"><a href="#assays" data-toggle="tab"
                                                       id="assaysTab">Assay Definitions (0)</a></li>
                <li id="compoundsTabLi"><a href="#compounds" data-toggle="tab" id="compoundsTab">Compounds (0)</a></li>
                <li id="projectsTabLi"><a href="#projects" data-toggle="tab" id="projectsTab">Projects (0)</a></li>
            </ul>

            <div id="resultTabContent" class="tab-content">

                <div class="tab-pane fade in active" id="assays" data-target="#assays">
                    <g:render template="assays"/>
                </div>
                <r:script>
                    //Overrides the Twitter Bootstraps' Dropdown behavior that hides the menu when a menu item was clicked
                    $(document).on('click', '#cutoff', function () {
                        $(this).parent().parent().parent().find('[data-toggle="dropdown"]').dropdown('toggle');
                        $(this).select();
                        $(this).keypress(function (event) {
                            if (event.keyCode == 13) {//enter
                                $(this).parent().find('a').click();
                            }
                        });
                    });
                </r:script>
                <div class="tab-pane fade" id="compounds" data-target="#compounds">
                    <g:render template="compounds"/>
                </div>

                <div class="tab-pane fade" id="projects" data-target="#projects">
                    <g:render template="projects"/>
                </div>

            </div>
        </div>
    </div>

    <r:require modules="search,promiscuity"/>

</div>
</body>
</html>