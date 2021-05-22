package dev.fumin.sample.eventdriven.domain.model.cloumn;

import dev.fumin.sample.eventdriven.domain.common.ConcurrencyEntity;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;
import lombok.Getter;

@Getter
public final class Column extends ConcurrencyEntity {

    private final ColumnId id;
    private final ProjectId projectId;
    private String name;
    private boolean active;

    public static Column create(ProjectId projectId, ColumnId id, String name) {
        return new Column(id, projectId, name, true);
    }

    public Column(ColumnId id, ProjectId projectId, String name, boolean active) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.active = active;
    }

    public void deactivate() {
        active = false;
    }

    public void activate() {
        active = true;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be null or blank.");
        }
        this.name = name;
    }

}
