package dev.fumin.sample.eventdriven.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import dev.fumin.sample.eventdriven.domain.model.project.Project;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectId;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectRepository;
import dev.fumin.sample.eventdriven.infrastructure.persistence.dao.ProjectDao;
import dev.fumin.sample.eventdriven.infrastructure.persistence.translation.ProjectTranslator;

public class MySqlProjectRepository implements ProjectRepository {

    private final ProjectDao dao;
    private final ProjectTranslator translator;

    @Inject
    public MySqlProjectRepository(ProjectDao dao, ProjectTranslator translator) {
        this.dao = dao;
        this.translator = translator;
    }

    @Override
    public ProjectId newId() {
        return new ProjectId(UUID.randomUUID().toString());
    }

    @Override
    public void create(Project project) {
        dao.insert(translator.toDto(project));
    }

    @Override
    public void update(Project project) {
        dao.update(translator.toDto(project));
    }

    @Override
    public Optional<Project> projectOf(ProjectId id) {
        return dao.selectById(id.getValue()).map(translator::toModel);
    }

}
