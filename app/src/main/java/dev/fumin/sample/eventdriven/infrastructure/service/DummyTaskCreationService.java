package dev.fumin.sample.eventdriven.infrastructure.service;

import java.util.UUID;

import dev.fumin.sample.eventdriven.domain.model.google.tasks.Task;
import dev.fumin.sample.eventdriven.domain.model.google.tasks.TaskCreationService;

public class DummyTaskCreationService implements TaskCreationService {

    @Override
    public Task createTask(String title) {
        return new Task(UUID.randomUUID().toString(), title);
    }

}
