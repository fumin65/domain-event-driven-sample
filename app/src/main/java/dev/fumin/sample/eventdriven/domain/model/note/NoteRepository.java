package dev.fumin.sample.eventdriven.domain.model.note;

import java.util.Optional;

import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;

public interface NoteRepository {

    NoteId newId();

    void create(Note note);

    void update(Note note);

    Optional<Note> noteFrom(ProjectId projectId, NoteId noteId);

}
