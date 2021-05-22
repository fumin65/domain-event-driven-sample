package dev.fumin.sample.eventdriven.domain.model.note;

import java.util.Objects;

import dev.fumin.sample.eventdriven.domain.common.ConcurrencyEntity;
import dev.fumin.sample.eventdriven.domain.model.google.tasks.TaskCreationService;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;
import dev.fumin.sample.eventdriven.domain.event.DomainResult;
import dev.fumin.sample.eventdriven.domain.model.cloumn.ColumnId;
import dev.fumin.sample.eventdriven.domain.model.google.tasks.Task;
import lombok.Getter;

@Getter
public final class Note extends ConcurrencyEntity {

    private final NoteId id;
    private final ProjectId projectId;
    private ColumnId columnId;
    private String description;

    public static DomainResult<Note, NoteEvent> create(
            NoteId id,
            ProjectId projectId,
            ColumnId columnId,
            String description
    ) {
        Note note = new Note(id, projectId, columnId, description);
        NoteCreated event = new NoteCreated(projectId, columnId, id);
        return new DomainResult<>(note, event);
    }

    public Note(NoteId id, ProjectId projectId, ColumnId columnId, String description) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.projectId = Objects.requireNonNull(projectId, "projectId must not be null");
        moveTo(columnId);
        setDescription(description);
    }

    public void moveTo(ColumnId columnId) {
        this.columnId = Objects.requireNonNull(columnId, "columnId must not be null");
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description must not be null or blank.");
        }
        this.description = description;
    }

    public Task copyToTasks(TaskCreationService service) {
        return service.createTask(description);
    }

}
