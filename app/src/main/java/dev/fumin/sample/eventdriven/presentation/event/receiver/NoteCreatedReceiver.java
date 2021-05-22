package dev.fumin.sample.eventdriven.presentation.event.receiver;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.annotation.WebServlet;

import dev.fumin.sample.eventdriven.application.usecase.note.CopyNoteToTaskUseCase;
import dev.fumin.sample.eventdriven.presentation.event.Event;
import dev.fumin.sample.eventdriven.presentation.event.EventReceiver;

@Singleton
@WebServlet(value = "/event/receiver/note-created")
public class NoteCreatedReceiver extends EventReceiver {

    private final JsonParser jsonParser = new JsonParser();

    @Inject
    private CopyNoteToTaskUseCase useCase;

    @Override
    protected void onReceive(Event event) {
        JsonObject root = jsonParser.parse(event.getMessage().getData()).getAsJsonObject();
        String projectId = root.get("projectId").getAsJsonObject().get("value").getAsString();
        String noteId = root.get("noteId").getAsJsonObject().get("value").getAsString();
        useCase.handle(projectId, noteId);
    }

}
