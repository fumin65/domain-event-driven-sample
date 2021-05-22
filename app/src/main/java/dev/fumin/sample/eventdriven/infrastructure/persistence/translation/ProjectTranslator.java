package dev.fumin.sample.eventdriven.infrastructure.persistence.translation;

import dev.fumin.sample.eventdriven.domain.model.project.Project;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;
import dev.fumin.sample.eventdriven.infrastructure.persistence.entity.ProjectDto;

public class ProjectTranslator extends ConcurrencyDataTranslator<Project, ProjectDto> {

    @Override
    public Project toModel(ProjectDto dto) {
        Project project = new Project(
                new ProjectId(dto.getId()),
                dto.getName(),
                dto.isActive()
        );
        attachToModel(dto, project);
        return project;
    }

    @Override
    public ProjectDto toDto(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId().getValue());
        dto.setName(project.getName());
        dto.setActive(project.isActive());
        attachToDto(project, dto);
        return dto;
    }

}
