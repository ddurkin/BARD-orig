databaseHost = "localhost"
databaseSid = "AL32UTF8"
dataSource {
    url = 'jdbc:oracle:thin:@${databaseHost}:1522:${databaseSid}'
    driverClassName = 'oracle.jdbc.driver.OracleDriver'
    dialect = 'bard.SequencePerTableOracleDialect'
    username = "devenv"
    password = "devenv"
    dbCreate='no-val'
}

bard.services.resultService.archivePath = "~tomcat7/bard-results"
appName= "BARD"
bard.hostname = "localhost"
server.port = "8080"
grails.serverURL = "http://${bard.hostname}:${server.port}/${appName}"
ncgc.server.root.url = "https://bard.nih.gov/api/v17.3/"
promiscuity.badapple.url = "${ncgc.server.root.url}/plugins/badapple/prom/cid/"
grails.jesque.enabled = false
bard.externalOntologyProxyUrlBase = "http://localhost:8080/external-ontology-proxy/externalOntology"
