package dev.fumin.sample.eventdriven.domain.model.note;

import dev.fumin.sample.eventdriven.domain.model.cloumn.ColumnId;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;
import lombok.Value;

@Value
public class NoteCreated implements NoteEvent {

    ProjectId projectId;
    ColumnId columnId;
    NoteId noteId;

}
