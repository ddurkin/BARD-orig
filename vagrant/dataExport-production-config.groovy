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
