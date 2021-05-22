package dev.fumin.sample.eventdriven.application.usecase.project;

import javax.inject.Inject;

import dev.fumin.sample.eventdriven.domain.model.project.Project;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectRepository;
import dev.fumin.sample.eventdriven.application.persistence.Transactional;

public class CreateProjectUseCase {

    private final ProjectRepository projectRepository;

    @Inject
    public CreateProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public String handle(String name) {
        ProjectId id = projectRepository.newId();
        Project project = Project.create(id, name);
        projectRepository.create(project);
        return project.getId().getValue();
    }

}
