import grails.util.Environment
import org.apache.log4j.DailyRollingFileAppender
import org.apache.log4j.net.SMTPAppender
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler

ncgc.thickclient.compounds.url="http://bard.nih.gov/bard/compounds/"
ncgc.thickclient.etags.url="http://bard.nih.gov/bard/etag/"

ncgc.server.root.url = "http://bard.nih.gov/api/v17.3"
promiscuity.badapple.url = "${ncgc.server.root.url}/plugins/badapple/prom/cid/"
//override in config file for environment
server.port = System.properties.getProperty('server.port') ?: 8081
grails.serverURL = "http://localhost:${server.port}/${appName}"
//URL to the ROOT of the cap server
bard.cap.home = "${grails.serverURL}"
bard.cap.assay = "${bard.cap.home}/assayDefinition/show/"
bard.cap.project="${bard.cap.home}/project/show/"

//Override in config file
dataexport.apikey= "test"
dataexport.dictionary.accept.type= "application/vnd.bard.cap+xml;type=dictionary"
dataexport.dictionary.url = "https://bard-qa.broadinstitute.org/dataExport/api/dictionary"


grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.cache.clearAtStartup=true
grails.cache.config = {
    cache {
        name 'dictionaryElements'
        eternal false
        overflowToDisk true
        maxElementsInMemory 10000
        maxElementsOnDisk 10000000
    }
}
grails.mime.types = [
        html: ['text/html', 'application/xhtml+xml'],
        xml: ['text/xml', 'application/xml'],
        text: 'text/plain',
        js: 'text/javascript',
        rss: 'application/rss+xml',
        atom: 'application/atom+xml',
        css: 'text/css',
        csv: 'text/csv',
        pdf: 'application/pdf',
        rtf: 'application/rtf',
        excel: 'application/vnd.ms-excel',
        ods: 'application/vnd.oasis.opendocument.spreadsheet',
        all: '*/*',
        json: ['application/json', 'text/json'],
        form: 'application/x-www-form-urlencoded',
        multipartForm: 'multipart/form-data'
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "html" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
grails.views.javascript.library = "jquery"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = true

// web property id for google analytics account
google.analytics.webPropertyID = "UA-37532975-1"

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
    }
}

rememberme.key = 'bard_web_client_crowd_remember_me'
rememberme.cookieName = 'bard_web_client_crowd_remember_me_cookie'

bard.home.page = "http://localhost:8080/${appName}"
// this should get overwritten by
bard.services.resultService.archivePath = System.getProperty("java.io.tmpdir")
grails.plugins.springsecurity.rememberMe.cookieName = rememberme.cookieName
grails.plugins.springsecurity.rememberMe.key = rememberme.key


switch (Environment.current) {
    case Environment.PRODUCTION:
        grails.plugins.springsecurity.providerNames = ['bardAuthorizationProviderService', 'anonymousAuthenticationProvider', 'rememberMeAuthenticationProvider']

        grails {
            plugins {
                springsecurity {
                    controllerAnnotations.staticRules = [
                            '/console/**': ['ROLE_CONSOLE_USER']
                    ]
                    ipRestrictions = [
                            '/console/**': '127.0.0.1'
                    ]
                    useBasicAuth = true
                    basic.realmName = 'CAP'
                    filterChain.chainMap = [
                            '/bardWebInterface/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/chemAxon/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/context/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/contextItem/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/dictionaryTerms/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/document/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/doseResponseCurve/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/element/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/experiment/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/mergeAssayDefinition/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/moveExperiments/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/person/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/project/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/splitAssayDefinition/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/**': 'JOINED_FILTERS,-basicAuthenticationFilter,-basicExceptionTranslationFilter'
                    ]
                }
            }
        }
        break;
    default:
        //use basic auth and in memory security services in no-production environments
        grails.plugins.springsecurity.providerNames = ['bardAuthorizationProviderService', 'inMemMapAuthenticationProviderService', 'anonymousAuthenticationProvider', 'rememberMeAuthenticationProvider']
        grails {
            plugins {
                springsecurity {
                    controllerAnnotations.staticRules = [
                            '/console/**': ['ROLE_CONSOLE_USER']
                    ]
                    ipRestrictions = [
                            '/console/**': '127.0.0.1'
                    ]
                    useBasicAuth = true
                    basic.realmName = 'CAP'
                    filterChain.chainMap = [
                            '/bardWebInterface/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/sandbox/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/element/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/mergeAssayDefinition/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/splitAssayDefinition/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/project/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/context/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/contextItem/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/dictionaryTerms/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/document/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/doseResponseCurve/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/element/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/experiment/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/mergeAssayDefinition/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/moveExperiments/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/person/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/project/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/splitAssayDefinition/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
                            '/**': 'JOINED_FILTERS,-basicAuthenticationFilter,-basicExceptionTranslationFilter'
                    ]
                }
            }
        }
}

//prevent session fixation attacks
grails.plugins.springsecurity.useSessionFixationPrevention = true

//grails.plugins.springsecurity.rejectIfNoRule = true

CbipCrowd {
    application.url = 'https://crowd.somewhere.com/crowd/'
    register.url = 'https://crowd.somewhere.com/crowd/'
    application.username = 'bard'
    application.password = 'ChangeMe'
    applicationSpecificRoles = ['ROLE_TEAM_A', 'ROLE_TEAM_B', 'ROLE_Bard','ROLE_MOBILE', 'ROLE_USER', 'ROLE_CONSOLE_USER', 'ROLE_NO_ROLE', 'ROLE_CURATOR', 'CURATOR', "ROLE_BARD_ADMINISTRATOR", "ROLE_TEAM_BROAD"]
    mockUsers {
        integrationTestUser {
            roles = ['ROLE_USER', 'ROLE_CURATOR', 'ROLE_BARD_ADMINISTRATOR']
            username = 'integrationTestUser'
            password = 'integrationTestUser'
            email = 'integrationTestUser@nowhere.com'
        }
        curator {
            roles = ['ROLE_CURATOR']
            username = 'curator'
            password = 'curator'
            email = 'curator@nowhere.com'
        }
        teamA_1 {
            roles = ['ROLE_TEAM_A']
            username = 'teamA_1'
            password = 'teamA_1'
            email = 'team1@nowhere.com'
        }
        teamA_2 {
            roles = ['ROLE_TEAM_A']
            username = 'teamA_2'
            password = 'teamA_2'
            email = 'teamA2@nowhere.com'
        }
        teamB_1 {
            roles = ['ROLE_TEAM_B']
            username = 'teamB_1'
            password = 'teamB_2'
            email = 'team2@nowhere.com'
        }
    }
}

//TODO: Override In Production. Also add greenmail.disabled=true so Green mail is disabled
grails.mail.port = com.icegreen.greenmail.util.ServerSetupTest.SMTP.port
grails.mail.default.from = "noreply@broadinstitute.org"
grails.mail.default.to = "noreply@broadinstitute.org"
grails.mail.host = "localhost"
grails.mail.default.subject = "Error From BARD Web Query"

//TODO Replace with the analytics ID
google.analytics.webPropertyID = "UA-xxxxxx-x"

/**
 * Loads external config files from the .grails subfolder in the user's home directory
 * Home directory in Windows is usually: C:\Users\<username>\.grails
 * In Unix, this is usually ~\.grails
 *
 * dataExport-commons-config.groovy is used to holed generic, non envrironment-specific configurations such as external api credentials, etc.
 */
if (appName) {
    grails.config.locations = []

    // If the developer specifies a directory for the external config files at the command line, use it.
    // This will look like 'grails -DprimaryConfigDir=[directory name] [target]'
    // Otherwise, look for these files in the user's home .grails/projectdb directory
    // If there are no external config files in either location, don't override anything in this Config.groovy
    String primaryOverrideDirName = System.properties.get('primaryConfigDir')
    String secondaryOverrideDirName = "${userHome}/.grails/${appName}"

    List<String> fileNames = ["${appName}-commons-config.groovy", "${appName}-${Environment.current.name}-config.groovy"]
    fileNames.each { fileName ->
        String primaryFullName = "${primaryOverrideDirName}/${fileName}"
        String secondaryFullName = "${secondaryOverrideDirName}/${fileName}"

        if (new File(primaryFullName).exists()) {
            println "Overriding Config.groovy with $primaryFullName"
            grails.config.locations << "file:$primaryFullName"
        } else if (new File(secondaryFullName).exists()) {
            println "Overriding Config.groovy with $secondaryFullName"
            grails.config.locations << "file:$secondaryFullName"
        } else {
            println "Skipping Config.groovy overrides: $primaryFullName and $secondaryFullName not found"
        }
    }
}

    if(System.getProperty("migrationContextsToRun") != null) {
        grails.plugin.databasemigration.updateOnStart = true
        grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']
        grails.plugin.databasemigration.updateOnStartContexts = System.getProperty("migrationContextsToRun").split(",") as List
    }

    log4j = {
        appenders {
            // This should work on both windows and unix
            appender new DailyRollingFileAppender(
                    name: "NCGCErrorAppender",
                    file: "logs/" + Environment.current.name + "/NCGC_Errors.log",
                    layout: pattern(conversionPattern: '%m%n'),
                    immediateFlush: true,
                    threshold: org.apache.log4j.Level.ERROR,
                    datePattern: "'.'yyyy-MM-dd"
            )
            appender new DailyRollingFileAppender(
                    name: "JavaScriptErrorsAppender",
                    file: "logs/" + Environment.current.name + "/Client_JavaScript_Errors.log",
                    layout: pattern(conversionPattern: '%m%n'),
                    immediateFlush: true,
                    threshold: org.apache.log4j.Level.ERROR,
                    datePattern: "'.'yyyy-MM-dd"
            )
            appenders {
                // This should work on both windows and unix
                appender new DailyRollingFileAppender(
                        name: "NCGCRestApiTimingAppender",
                        file: "logs/" + Environment.current.name + "/NCGC_StopWatch.log",
                        layout: pattern(conversionPattern: '%m%n'),
                        immediateFlush: true,
                        threshold: org.apache.log4j.Level.INFO,
                        datePattern: "'.'yyyy-MM-dd"
                )
            }

            def patternLayout = new org.apache.log4j.PatternLayout()
            patternLayout.setConversionPattern("%d [%t] %-5p %c - %m%n")
            appender new SMTPAppender(
                    name: "mail",
                    smtpPort: config.grails.mail.port,
                    from: config.grails.mail.default.from,
                    to: config.grails.mail.default.to,
                    subject: "[${InetAddress.getLocalHost().getHostName()}] " + config.grails.mail.default.subject,
                    smtpHost: config.grails.mail.host,
                    layout: patternLayout,
                    threshold: org.apache.log4j.Level.ERROR
            )
        }

        root {
            debug 'stdout'
            error 'mail', 'fileAppender'
        }


        error additivity: false,
                //Capture errors from the NCGC API (via JDO) but DO NOT send emails about them.
                NCGCErrorAppender: ['grails.app.services.bard.core.rest.spring.AbstractRestService']
        error additivity: true,
                //Capture JavaScript errors from the client (via the ErrorHandling controller)
                JavaScriptErrorsAppender: ['grails.app.controllers.bardqueryapi.ErrorHandlingController']

        //Capture NCGC REST API roundtrip timing.
        info NCGCRestApiTimingAppender: ['grails.app.services.bard.core.helper.LoggerService']
    }

// Added by the JQuery Validation UI plugin:
jqueryValidationUi {
    errorClass = 'error'
    validClass = 'valid'
    onsubmit = true
    renderErrorsOnTop = false

    qTip {
        packed = true
        classes = 'ui-tooltip-red ui-tooltip-shadow ui-tooltip-rounded'
    }

    /*
       Grails constraints to JQuery Validation rules mapping for client side validation.
       Constraint not found in the ConstraintsMap will trigger remote AJAX validation.
     */
    StringConstraintsMap = [
            blank: 'required', // inverse: blank=false, required=true
            creditCard: 'creditcard',
            email: 'email',
            inList: 'inList',
            minSize: 'minlength',
            maxSize: 'maxlength',
            size: 'rangelength',
            matches: 'matches',
            notEqual: 'notEqual',
            url: 'url',
            nullable: 'required',
            unique: 'unique',
            validator: 'validator'
    ]

    // Long, Integer, Short, Float, Double, BigInteger, BigDecimal
    NumberConstraintsMap = [
            min: 'min',
            max: 'max',
            range: 'range',
            notEqual: 'notEqual',
            nullable: 'required',
            inList: 'inList',
            unique: 'unique',
            validator: 'validator'
    ]

    CollectionConstraintsMap = [
            minSize: 'minlength',
            maxSize: 'maxlength',
            size: 'rangelength',
            nullable: 'required',
            validator: 'validator'
    ]

    DateConstraintsMap = [
            min: 'minDate',
            max: 'maxDate',
            range: 'rangeDate',
            notEqual: 'notEqual',
            nullable: 'required',
            inList: 'inList',
            unique: 'unique',
            validator: 'validator'
    ]

    ObjectConstraintsMap = [
            nullable: 'required',
            validator: 'validator'
    ]

    CustomConstraintsMap = [
            phone: 'true', // International phone number validation
            phoneUS: 'true',
            alphanumeric: 'true',
            letterswithbasicpunc: 'true',
            lettersonly: 'true'
    ]
}

grails.plugins.twitterbootstrap.fixtaglib = true
grails.spring.disable.aspectj.autoweaving = true