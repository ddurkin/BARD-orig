package bard.db.experiment

import bard.db.registration.Assay

class ProjectAssay implements Serializable {

//		Stage stage
		Integer sequenceNo
		Float promotionThreshold
		String promotionCriteria
		Date dateCreated
		Date lastUpdated
		String modifiedBy
		Project project
		Assay assay

		static belongsTo = [Assay, Project]

		static mapping = {
			id column: "project_assay_id", generator: "assigned"
//            stage column: "stage"
		}

		static constraints = {
//			stage maxSize: 128, nullable: false
			sequenceNo nullable: true
			promotionThreshold nullable: true
			promotionCriteria nullable: true, maxSize: 1000
			dateCreated maxSize: 19
			lastUpdated nullable: true, maxSize: 19
			modifiedBy nullable: true, maxSize: 40
		}
}