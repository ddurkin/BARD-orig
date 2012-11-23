package dataexport.experiment

import bard.db.dictionary.Element
import bard.db.enums.ReadyForExtraction
import bard.db.experiment.Experiment
import bard.db.project.Project
import bard.db.project.ProjectContextItem
import bard.db.project.ProjectDocument
import bard.db.project.ProjectStep
import bard.db.registration.ExternalReference
import dataexport.registration.BardHttpResponse
import dataexport.registration.MediaTypesDTO
import dataexport.util.ExportAbstractService
import dataexport.util.UtilityService
import exceptions.NotFoundException
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

class ProjectExportService extends ExportAbstractService {
    final String projectMediaType
    final String projectsMediaType
    final MediaTypesDTO mediaTypesDTO
    UtilityService utilityService
    final LinkGenerator grailsLinkGenerator

    //This is instantiated from resources.groovy
    ProjectExportService(final MediaTypesDTO mediaTypesDTO) {
        this.mediaTypesDTO = mediaTypesDTO
        this.projectMediaType = mediaTypesDTO.projectMediaType
        this.projectsMediaType = mediaTypesDTO.projectsMediaType
    }

    /**
     * Set the ReadyForExtraction value on the element to 'Complete'
     *
     * Return a 409, conflict, if the version supplied by client is less than the version in the database
     *
     * Return a 412, precondition failed, if the version supplied by client is not equal to the version in the database
     *
     * Return a 404 , if the element cannot be found
     *
     * @param id
     * @param version
     * Returns the HTTPStatus Code
     */
    public BardHttpResponse update(final Long id, final Long clientVersion, final String latestStatus) {
        final Project project = Project.findById(id)
        return utilityService.update(project, id, clientVersion, latestStatus as ReadyForExtraction, "Project")
    }

    protected void generateProjectContextItems(final MarkupBuilder markupBuilder, Set<ProjectContextItem> projectContextItemsSet) {
        markupBuilder.projectContextItems() {
            for (ProjectContextItem projectContextItem : projectContextItemsSet) {
                generateProjectContextItem(markupBuilder, projectContextItem)
            }

        }
    }

    protected Map<String, String> createAttributesForProjectContextItem(final ProjectContextItem projectContextItem) {
        final Map<String, String> attributes = [:]


        if (projectContextItem.id) {
            attributes.put('projectContextItemId', projectContextItem?.id?.toString())
        }
        if (projectContextItem.parentGroupProjectContext) {
            attributes.put('parentGroup', projectContextItem?.parentGroupProjectContext?.id?.toString())
        }
        if (projectContextItem.qualifier) {
            attributes.put('qualifier', projectContextItem.qualifier)
        }

        if (projectContextItem.valueDisplay) {
            attributes.put('valueDisplay', projectContextItem.valueDisplay)
        }
        if (projectContextItem.valueNum || projectContextItem.valueNum.toString().isInteger()) {
            attributes.put('valueNum', projectContextItem.valueNum.toString())
        }
        if (projectContextItem.valueMin || projectContextItem.valueMin.toString().isInteger()) {
            attributes.put('valueMin', projectContextItem.valueMin.toString())
        }
        if (projectContextItem.valueMax || projectContextItem.valueMax.toString().isInteger()) {
            attributes.put('valueMax', projectContextItem.valueMax.toString())
        }
        return attributes;
    }

    protected void generateProjectContextItem(final MarkupBuilder markupBuilder, final ProjectContextItem projectContextItemInstance) {
        final Map<String, String> attributes = createAttributesForProjectContextItem(projectContextItemInstance)

        markupBuilder.projectContextItem(attributes) {
            final Element valueElement = projectContextItemInstance.valueElement
            final Element attributeElement = projectContextItemInstance.attributeElement

            //add value id element
            if (valueElement) {
                valueId(label: valueElement.label) {
                    final String valueHref = generateHref('element', valueElement.id, this.grailsLinkGenerator)
                    generateLink(markupBuilder, valueHref, 'related', this.mediaTypesDTO.elementMediaType)

                }
            }
            //add attributeId element
            if (attributeElement) {

                attributeId(label: attributeElement.label) {
                    final String attributeHref = generateHref('element', attributeElement.id, this.grailsLinkGenerator)
                    generateLink(markupBuilder, attributeHref, 'related', this.mediaTypesDTO.elementMediaType)
                }
            }
            if (projectContextItemInstance.extValueId) {
                extValueId(projectContextItemInstance.extValueId)
            }
        }
    }

    /**
     * Generate projectStep
     *
     * @param markupBuilder
     * @param projectStep
     */
    protected void generateProjectStep(final MarkupBuilder markupBuilder, final ProjectStep projectStepInstance) {

        markupBuilder.projectStep(projectStepId: projectStepInstance?.id.toString()) {
            if (projectStepInstance.description) {
                description(projectStepInstance.description)
            }
            final Experiment experiment = projectStepInstance.experiment
            if (experiment) {
                final String experimentHref = generateHref('experiment', experiment.id, this.grailsLinkGenerator)
                generateLink(markupBuilder, experimentHref, 'related', this.mediaTypesDTO.experimentMediaType)
            }
        }
    }

    protected void generateProjectSteps(final MarkupBuilder markupBuilder, Set<ProjectStep> projectStepSet) {
        markupBuilder.projectSteps() {
            for (ProjectStep projectStep : projectStepSet) {
                generateProjectStep(markupBuilder, projectStep)
            }

        }
    }
    /**
     * Generate a Project
     * @param markupBuilder
     * @param project
     */
    protected void generateProject(final MarkupBuilder markupBuilder, final Project project) {
        def attributes = [:]
        attributes.put('projectId', project.id)
        if (project.readyForExtraction) {
            attributes.put('readyForExtraction', project.readyForExtraction.toString())
        }
        if (project.groupType) {
            attributes.put('groupType', project.groupType)
        }
        markupBuilder.project(
                attributes
        ) {
            if (project.projectName) {
                projectName(project.projectName)
            }
            if (project.description) {
                description(project.description)
            }
            if (project.projectSteps) {
                generateProjectSteps(markupBuilder, project.projectSteps)
            }
            if (project.projectContextItems) {
                generateProjectContextItems(markupBuilder, project.projectContextItems)
            }
            generateProjectLinks(markupBuilder, project)
        }
    }

    protected void generateProjectLinks(final MarkupBuilder markupBuilder, final Project project) {

        final String projectHref = generateHref('project', project.id, this.grailsLinkGenerator)
        generateLink(markupBuilder, projectHref, 'edit', this.projectMediaType)

        final String projectsHref = generateHref('projects', null, this.grailsLinkGenerator)
        generateLink(markupBuilder, projectsHref, 'up', this.projectsMediaType)

        final Set<ExternalReference> externalReferences = project.externalReferences
        generateExternalReferencesLink(markupBuilder, externalReferences as List<ExternalReference>, this.grailsLinkGenerator, this.mediaTypesDTO)
    }

    /**
     *  Generate a project from a given projectId
     * @param markupBuilder
     * @param projectId
     */
    public Long generateProject(final MarkupBuilder markupBuilder, final Long projectId) {
        final Project project = Project.get(projectId)
        if (!project) {
            throw new NotFoundException("Could not find Project with Id ${projectId}")
        }
        this.generateProject(markupBuilder, project)
        return project.version
    }
    /**
     * @param xml
     */
    public void generateProjects(def markupBuilder) {
        final List<Project> projects = Project.findAllByReadyForExtraction(ReadyForExtraction.Ready)
        final int numberOfProjects = projects.size()
        markupBuilder.projects(count: numberOfProjects) {
            for (Project project : projects) {
                generateProject(markupBuilder, project)
            }
        }
    }

    public void generateProjectDocument(final MarkupBuilder markupBuilder, ProjectDocument projectDocument) {
        generateDocument(this.grailsLinkGenerator, markupBuilder, projectDocument,
                'projectDocument', 'project',
                projectDocument.id,
                projectDocument.project.id,
                this.mediaTypesDTO.projectDocMediaType,
                this.mediaTypesDTO.projectMediaType
        )
    }

    public void generateProjectDocuments(
            final MarkupBuilder markupBuilder,
            final Set<ProjectDocument> projectDocuments) {
        if (projectDocuments) {
            markupBuilder.projectDocuments() {
                this.generateDocument(this.grailsLinkGenerator,)
                for (ProjectDocument projectDocument : projectDocuments) {
                    generateProjectDocument(markupBuilder, projectDocument)
                }
            }
        }
    }

    /**
     * Stream an project document
     * @param markupBuilder
     * @param projectDocument
     */
    public Long generateProjectDocument(
            final MarkupBuilder markupBuilder, final Long projectDocumentId) {
        final ProjectDocument projectDocument = ProjectDocument.get(projectDocumentId)
        if (!projectDocument) {
            final String errorMessage = "Project Document with Id ${projectDocumentId} does not exists"
            log.error(errorMessage)
            throw new NotFoundException(errorMessage)
        }

        generateProjectDocument(markupBuilder, projectDocument)
        return projectDocument.version
    }

}
