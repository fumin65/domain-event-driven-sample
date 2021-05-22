package dev.fumin.sample.eventdriven.application.usecase.note;

import javax.inject.Inject;

import dev.fumin.sample.eventdriven.application.persistence.Transactional;
import dev.fumin.sample.eventdriven.domain.model.google.tasks.TaskCreationService;
import dev.fumin.sample.eventdriven.domain.model.note.NoteId;
import dev.fumin.sample.eventdriven.domain.model.note.NoteRepository;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;

public class CopyNoteToTaskUseCase {

    private final NoteRepository noteRepository;
    private final TaskCreationService taskCreationService;

    @Inject
    public CopyNoteToTaskUseCase(NoteRepository noteRepository, TaskCreationService taskCreationService) {
        this.noteRepository = noteRepository;
        this.taskCreationService = taskCreationService;
    }

    @Transactional
    public void handle(String projectId, String noteId) {
        ProjectId pId = new ProjectId(projectId);
        NoteId nId = new NoteId(noteId);
        noteRepository.noteFrom(pId, nId)
                .map(note -> note.copyToTasks(taskCreationService))
                .ifPresent(task -> System.out.println("Task created : " + task.getId()));
    }

}
