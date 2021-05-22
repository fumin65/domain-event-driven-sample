package dev.fumin.sample.eventdriven.domain.model.cloumn;

import javax.inject.Inject;

import dev.fumin.sample.eventdriven.domain.model.project.Project;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectRepository;

public class ColumnService {

    private final ProjectRepository projectRepository;
    private final ColumnRepository columnRepository;

    @Inject
    public ColumnService(
            ProjectRepository projectRepository,
            ColumnRepository columnRepository
    ) {
        this.projectRepository = projectRepository;
        this.columnRepository = columnRepository;
    }

    public Column create(ProjectId projectId, String name) {
        Project project = projectRepository.projectOf(projectId)
                .orElseThrow(() -> new IllegalArgumentException("specified project is not found."));

        if (!project.isActive()) {
            throw new IllegalStateException("specified project is not active.");
        }

        ColumnId columnId = columnRepository.newId();
        Column column = Column.create(projectId, columnId, name);

        columnRepository.create(column);

        return column;
    }

}
