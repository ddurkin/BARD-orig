package bard.db.experiment

import bard.db.registration.Panel

/**
 * Created with IntelliJ IDEA.
 * User: pmontgom
 * Date: 10/23/13
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
class PanelExperiment {

    private static final int MODIFIED_BY_MAX_SIZE = 40

    Panel panel

    static belongsTo = [panel: Panel]

    Set<Experiment> experiments = [] as Set
    static hasMany = [experiments: Experiment]

    Date dateCreated = new Date()
    // grails auto-timestamp
    Date lastUpdated = new Date()

    String modifiedBy

    static mapping = {
        id(column: 'PANEL_EXPRMT_ID', generator: 'sequence', params: [sequence: 'PANEL_EXPRMT_ID_SEQ'])
    }

    static constraints = {
        panel(nullable: false)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
        modifiedBy(nullable: true, blank: false, maxSize: MODIFIED_BY_MAX_SIZE)
    }

    String getDisplayName() {
        return "${this.id} - ${this.panel?.name ?: ''}"
    }
}
