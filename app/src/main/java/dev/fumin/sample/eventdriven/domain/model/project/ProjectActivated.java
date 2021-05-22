package dev.fumin.sample.eventdriven.domain.model.project;

import lombok.Value;

@Value
public class ProjectActivated implements ProjectEvent {
    ProjectId projectId;
}
