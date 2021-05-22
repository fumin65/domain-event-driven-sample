package dev.fumin.sample.eventdriven.application.usecase.project;

import javax.inject.Inject;

import dev.fumin.sample.eventdriven.application.event.EventQueue;
import dev.fumin.sample.eventdriven.domain.model.project.Project;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectRepository;
import dev.fumin.sample.eventdriven.application.persistence.Transactional;

public class DeactivateProjectUseCase {

    private final ProjectRepository projectRepository;
    private final EventQueue eventQueue;

    @Inject
    public DeactivateProjectUseCase(
            ProjectRepository projectRepository,
            EventQueue eventQueue
    ) {
        this.projectRepository = projectRepository;
        this.eventQueue = eventQueue;
    }

    @Transactional
    public void handle(String projectId) {
        projectRepository.projectOf(new ProjectId(projectId))
                .flatMap(Project::deactivate)
                .ifPresent(eventQueue::push);
    }

}
