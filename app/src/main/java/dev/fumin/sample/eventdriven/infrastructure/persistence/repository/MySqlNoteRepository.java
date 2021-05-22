package dev.fumin.sample.eventdriven.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import dev.fumin.sample.eventdriven.domain.model.note.Note;
import dev.fumin.sample.eventdriven.domain.model.note.NoteId;
import dev.fumin.sample.eventdriven.domain.model.note.NoteRepository;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;
import dev.fumin.sample.eventdriven.infrastructure.persistence.dao.NoteDao;
import dev.fumin.sample.eventdriven.infrastructure.persistence.translation.NoteTranslator;

public class MySqlNoteRepository implements NoteRepository {

    private final NoteDao dao;
    private final NoteTranslator translator;

    @Inject
    public MySqlNoteRepository(NoteDao dao, NoteTranslator translator) {
        this.dao = dao;
        this.translator = translator;
    }

    @Override
    public NoteId newId() {
        return new NoteId(UUID.randomUUID().toString());
    }

    @Override
    public void create(Note note) {
        dao.insert(translator.toDto(note));
    }

    @Override
    public void update(Note note) {
        dao.update(translator.toDto(note));
    }

    @Override
    public Optional<Note> noteFrom(ProjectId projectId, NoteId noteId) {
        return dao.selectById(noteId.getValue(), projectId.getValue()).map(translator::toModel);
    }

}
