package dev.fumin.sample.eventdriven.domain.model.project;

import java.util.Optional;

public interface ProjectRepository {

    ProjectId newId();

    void create(Project project);

    void update(Project project);

    Optional<Project> projectOf(ProjectId id);

}
