databaseHost = "DATABASE_HOST"
databaseSid = "DATABASE_SID"
databaseUsername = "DATABASE_USERNAME"
databasePassword = "DATABASE_PASWORD"
bardHostname = "BARD_HOSTNAME"
bardSearchApiUrl = "BARD_SEARCH_API_URL"

dataSource {
    url = "jdbc:oracle:thin:@${databaseHost}:1521:${databaseSid}"
    driverClassName = "oracle.jdbc.driver.OracleDriver"
    dialect = "bard.SequencePerTableOracleDialect"
    username = "${databaseUsername}"
    password = "${databasePassword}"
    dbCreate="no-val"
}

bard.services.resultService.archivePath = "~tomcat7/bard-results"
appName= "BARD"
server.port = "8080"
grails.serverURL = "http://${bardHostname}:${server.port}/${appName}"

ncgc.server.root.url = "{bardSearchApiUrl}"
promiscuity.badapple.url = "${ncgc.server.root.url}/plugins/badapple/prom/cid/"
grails.jesque.enabled = false

bard.externalOntologyProxyUrlBase = "http://${bardHostname}:${server.port}/external-ontology-proxy/externalOntology"
