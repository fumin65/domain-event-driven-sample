package dev.fumin.sample.eventdriven.application.usecase.note;

import javax.inject.Inject;

import dev.fumin.sample.eventdriven.application.event.EnqueueEvent;
import dev.fumin.sample.eventdriven.application.persistence.Transactional;
import dev.fumin.sample.eventdriven.domain.model.cloumn.ColumnId;
import dev.fumin.sample.eventdriven.domain.model.note.Note;
import dev.fumin.sample.eventdriven.domain.model.note.NoteCreated;
import dev.fumin.sample.eventdriven.domain.model.note.NoteService;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;

public class CreateNoteUseCase {

    private final NoteService noteService;

    @Inject
    public CreateNoteUseCase(NoteService noteService) {
        this.noteService = noteService;
    }

    @Transactional
    @EnqueueEvent(NoteCreated.class)
    public String handle(CreateNoteCommand command) {
        ProjectId projectId = new ProjectId(command.getProjectId());
        ColumnId columnId = new ColumnId(command.getColumnId());
        Note note = noteService.createNote(projectId, columnId, command.getDescription());
        return note.getId().getValue();
    }

}
