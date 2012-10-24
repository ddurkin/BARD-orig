package bard.db.registration

import grails.plugin.spock.IntegrationSpec
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.junit.Before
import registration.AssayService
import spock.lang.Shared
import bard.db.experiment.Experiment
import spock.lang.Unroll
import org.springframework.transaction.TransactionStatus

/**
 * Created with IntelliJ IDEA.
 * User: ddurkin
 * Date: 8/21/12
 * Time: 9:18 AM
 * To change this template use File | Settings | File Templates.
 */
@Unroll
class AssayServiceIntegrationSpec extends IntegrationSpec {

    AssayService assayService
    @Shared Assay assay1
    @Shared Assay assay2

    @Before
    void doSetup() {
//        Assay.withTransaction {TransactionStatus transactionStatus ->
        grails.buildtestdata.TestDataConfigurationHolder.reset()
        assay1 = Assay.build() //An assay with two experiments, each link to a different external reference (e.g., PubChem AID)
        final ExternalReference extRef1 = ExternalReference.build(extAssayRef: 'aid=1')
        extRef1.save(flush: true)
        Experiment experiment1 = Experiment.build() //first experiment with a reference to aid=1
        experiment1.addToExternalReferences(extRef1)
        experiment1.save(flush: true)
        final ExternalReference extRef2 = ExternalReference.build(extAssayRef: 'aid=2')
        extRef2.save(flush: true)
        Experiment experiment2 = Experiment.build()//second experiment with a reference to aid=2
        experiment2.addToExternalReferences(extRef2)
        experiment2.save(flush: true)
        assay1.addToExperiments(experiment1)
        assay1.addToExperiments(experiment2)
        assay1.validate()
        assert assay1.save(flush: true)
        assay2 = Assay.build() //As assay with two experiments, both link to the same external reference
        assay2.addToExperiments(experiment1)
        assert assay2.save(flush: true)
//        }
    }

    void "test findByPubChemAid #label"() {

        given:


        when:
        List<Assay> foundAssays = assayService.findByPubChemAid(aid)


        then: 'order preserved'
        assert foundAssays*.id == expectedAssayIDs

        where:
        label                                           | aid       | expectedAssayIDs
//        'find an exiting aid associated with two ADIDs' | 1         | [1, 2]
        'find an ADID with two AIDs associated with it' | 2         | [1]
        'find a non-exiting aid'                        | 123456789 | []
    }
}
