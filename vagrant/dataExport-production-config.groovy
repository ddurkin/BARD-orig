databaseHost = "DATABASE_HOST"
databaseSid = "DATABASE_SID"
databaseUsername = "DATABASE_USERNAME"
databasePassword = "DATABASE_PASWORD"
hashedApiKey = "HASHED_API_KEY"

dataSource {
    url = "jdbc:oracle:thin:@${databaseHost}:1521:${databaseSid}"
    driverClassName = "oracle.jdbc.driver.OracleDriver"
    dialect = "bard.SequencePerTableOracleDialect"
    username = "${databaseUsername}"
    password = "${databasePassword}"
    dbCreate="no-val"
}

bard.services.resultService.archivePath = "~tomcat7/bard-results"
dataexport.externalapplication.apiKey.hashed = "${hashedApiKey}"
