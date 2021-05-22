package dev.fumin.sample.eventdriven.application.usecase.column;

import javax.inject.Inject;

import dev.fumin.sample.eventdriven.application.persistence.Transactional;
import dev.fumin.sample.eventdriven.domain.model.cloumn.Column;
import dev.fumin.sample.eventdriven.domain.model.cloumn.ColumnService;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;

public class CreateColumnUseCase {

    private final ColumnService columnService;

    @Inject
    public CreateColumnUseCase(ColumnService columnService) {
        this.columnService = columnService;
    }

    @Transactional
    public String handle(String projectId, String name) {
        Column column = columnService.create(new ProjectId(projectId), name);
        return column.getId().getValue();
    }

}
