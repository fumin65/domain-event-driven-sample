package dev.fumin.sample.eventdriven.domain.model.cloumn;

import java.util.Optional;

import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;

public interface ColumnRepository {

    ColumnId newId();

    void create(Column column);

    void update(Column column);

    Optional<Column> columnFrom(ProjectId projectId, ColumnId columnId);

}
