<%@ page import="bard.db.registration.*" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <r:require modules="core,bootstrap,contextItem"/>
    <meta name="layout" content="basic"/>
    <title>Create Assay Document</title>

</head>

<body>
<g:render template="form" model="${[instance: instance, action:'Save']}" />
</body>
</html>