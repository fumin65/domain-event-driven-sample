package dev.fumin.sample.eventdriven.domain.model.project;

import java.util.Optional;

import dev.fumin.sample.eventdriven.domain.common.ConcurrencyEntity;
import dev.fumin.sample.eventdriven.domain.model.cloumn.Column;
import dev.fumin.sample.eventdriven.domain.model.cloumn.ColumnId;
import lombok.Getter;

@Getter
public final class Project extends ConcurrencyEntity {

    private final ProjectId id;
    private String name;
    private boolean active;

    public static Project create(ProjectId id, String name) {
        return new Project(id, name, true);
    }

    public Project(ProjectId id, String name, boolean active) {
        this.id = id;
        this.active = active;
        setName(name);
    }

    public Optional<ProjectDeactivated> deactivate() {
        if (isActive()) {
            active = false;
            return Optional.of(new ProjectDeactivated(this.id));
        }
        return Optional.empty();
    }

    public Optional<ProjectActivated> activate() {
        if (!isActive()) {
            active = true;
            return Optional.of(new ProjectActivated(this.id));
        }
        return Optional.empty();
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be null or blank.");
        }
        this.name = name;
    }

    public Column addColumn(ColumnId columnId, String name) {
        if (!isActive()) {
            throw new IllegalStateException("project is not active");
        }
        return Column.create(this.id, columnId, name);
    }

}
