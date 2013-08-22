import acl.CapPermissionService
import bard.core.rest.spring.DictionaryRestService
import bard.core.util.ExternalUrlDTO
import bard.db.ReadyForExtractFlushListener
import bard.hibernate.ModifiedByListener
import bard.person.RoleEditorRegistrar
import bardqueryapi.QueryService
import grails.util.Environment
import org.codehaus.groovy.grails.orm.hibernate.HibernateEventListeners
import org.springframework.web.client.RestTemplate

// Place your Spring DSL code here
beans = {

    customPropertyEditorRegistrar(RoleEditorRegistrar)

    clientBasicAuth(wslite.http.auth.HTTPBasicAuthorization) {
        username = grailsApplication.config.CbipCrowd.application.username
        password = grailsApplication.config.CbipCrowd.application.password
    }

    httpClient(wslite.http.HTTPClient) {
        connectTimeout = 5000
        readTimeout = 10000
        useCaches = false
        followRedirects = false
        sslTrustAllCerts = true
    }


    restClient(wslite.rest.RESTClient) {
        url = grailsApplication.config.CbipCrowd.register.url
        httpClient = ref('httpClient')
        authorization = ref('clientBasicAuth')
    }
    modifiedByListener(ModifiedByListener) {
        springSecurityService = ref('springSecurityService')
    }
    readyForExtractFlushListener(ReadyForExtractFlushListener) {
    }
    hibernateEventListeners(HibernateEventListeners) {
        listenerMap = [
                'flush': readyForExtractFlushListener,
                'post-insert': readyForExtractFlushListener,
                'post-update': readyForExtractFlushListener,
                'pre-insert': modifiedByListener,
                'pre-update': modifiedByListener,
        ]
    }


    bardAuthorizationProviderService(bard.auth.BardAuthorizationProviderService) {// beans here
        crowdClient = ref('crowdClient')
        grailsApplication = application
    }
    //if production then eliminate the mock user because it is a security hole in production

    switch (Environment.current) {
        case Environment.PRODUCTION:
            //don't use in memory map in production
            userDetailsService(org.broadinstitute.cbip.crowd.MultiProviderUserDetailsService) {
                crowdAuthenticationProviders = [ref('bardAuthorizationProviderService')]
            }
            break
        default:
            inMemMapAuthenticationProviderService(org.broadinstitute.cbip.crowd.noServer.MockCrowdAuthenticationProviderService) {
                grailsApplication = application
            }
            userDetailsService(org.broadinstitute.cbip.crowd.MultiProviderUserDetailsService) {
                crowdAuthenticationProviders = [ref('inMemMapAuthenticationProviderService'), ref('bardAuthorizationProviderService')]
            }
    }

    capPermissionService(CapPermissionService) {
        aclUtilService = ref("aclUtilService")
        springSecurityService = ref("springSecurityService")
    }
    def extOntologyFactory = externalOntologyFactory(bard.validation.ext.RegisteringExternalOntologyFactory) { bean ->
        bean.factoryMethod = "getInstance"
    }

    String ncgcBaseURL = grailsApplication.config.ncgc.server.root.url
    String badApplePromiscuityUrl = grailsApplication.config.promiscuity.badapple.url
    String bardCapUrl = grailsApplication.config.bard.cap.home

    externalUrlDTO(ExternalUrlDTO) {
        ncgcUrl = ncgcBaseURL
        promiscuityUrl = badApplePromiscuityUrl
        capUrl = bardCapUrl
    }



    restTemplate(RestTemplate)

    dictionaryRestService(DictionaryRestService) {
        externalUrlDTO = ref('externalUrlDTO')
        restTemplate = ref('restTemplate')
        loggerService = ref('loggerService')
    }

    queryService(QueryService) {
        queryHelperService = ref('queryHelperService')
        compoundRestService = ref('compoundRestService')
        projectRestService = ref('projectRestService')
        assayRestService = ref('assayRestService')
        substanceRestService = ref('substanceRestService')
        experimentRestService = ref('experimentRestService')
        capRestService = ref('capRestService')
    }


}
