package dev.fumin.sample.eventdriven.infrastructure.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.ServletModule;

import java.util.Date;

import dev.fumin.sample.eventdriven.application.event.EventDispatcher;
import dev.fumin.sample.eventdriven.application.event.EventStore;
import dev.fumin.sample.eventdriven.application.persistence.Transactional;
import dev.fumin.sample.eventdriven.domain.model.cloumn.ColumnRepository;
import dev.fumin.sample.eventdriven.domain.model.google.tasks.TaskCreationService;
import dev.fumin.sample.eventdriven.domain.model.note.NoteRepository;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectRepository;
import dev.fumin.sample.eventdriven.infrastructure.event.MySqlConsumedEventStore;
import dev.fumin.sample.eventdriven.infrastructure.event.PubsubEventDispatcher;
import dev.fumin.sample.eventdriven.infrastructure.persistence.doma.DomaConfig;
import dev.fumin.sample.eventdriven.infrastructure.persistence.doma.DomaTransactionInterceptor;
import dev.fumin.sample.eventdriven.presentation.event.ConsumedEventStore;
import dev.fumin.sample.eventdriven.infrastructure.event.DomainEventResetFilter;
import dev.fumin.sample.eventdriven.infrastructure.event.MySqlEventStore;
import dev.fumin.sample.eventdriven.infrastructure.persistence.dao.ColumnDao;
import dev.fumin.sample.eventdriven.infrastructure.persistence.dao.ColumnDaoImpl;
import dev.fumin.sample.eventdriven.infrastructure.persistence.dao.ConsumedEventDao;
import dev.fumin.sample.eventdriven.infrastructure.persistence.dao.ConsumedEventDaoImpl;
import dev.fumin.sample.eventdriven.infrastructure.persistence.dao.EventDao;
import dev.fumin.sample.eventdriven.infrastructure.persistence.dao.EventDaoImpl;
import dev.fumin.sample.eventdriven.infrastructure.persistence.dao.NoteDao;
import dev.fumin.sample.eventdriven.infrastructure.persistence.dao.NoteDaoImpl;
import dev.fumin.sample.eventdriven.infrastructure.persistence.dao.ProjectDao;
import dev.fumin.sample.eventdriven.infrastructure.persistence.dao.ProjectDaoImpl;
import dev.fumin.sample.eventdriven.infrastructure.persistence.repository.MySqlColumnRepository;
import dev.fumin.sample.eventdriven.infrastructure.persistence.repository.MySqlNoteRepository;
import dev.fumin.sample.eventdriven.infrastructure.persistence.repository.MySqlProjectRepository;
import dev.fumin.sample.eventdriven.infrastructure.pubsub.LocalSafePublisher;
import dev.fumin.sample.eventdriven.infrastructure.pubsub.RemoteSafePublisher;
import dev.fumin.sample.eventdriven.infrastructure.pubsub.SafePublisher;
import dev.fumin.sample.eventdriven.infrastructure.serialization.DateTypeAdapter;
import dev.fumin.sample.eventdriven.infrastructure.service.DummyTaskCreationService;

public class InfrastructureModule extends ServletModule {

    @Override
    protected void configureServlets() {
        super.configureServlets();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
        bind(Gson.class).toInstance(gson);

        // pubsub
        String port = System.getenv("PUBSUB_EMULATOR_HOST");
        if (port != null) {
            System.out.println("emulator port : " + port);
            bind(SafePublisher.class).toInstance(new LocalSafePublisher(port));
        } else {
            bind(SafePublisher.class).to(RemoteSafePublisher.class);
        }

        // dao
        bind(ProjectDao.class).toInstance(new ProjectDaoImpl(DomaConfig.getInstance()));
        bind(ColumnDao.class).toInstance(new ColumnDaoImpl(DomaConfig.getInstance()));
        bind(NoteDao.class).toInstance(new NoteDaoImpl(DomaConfig.getInstance()));
        bind(EventDao.class).toInstance(new EventDaoImpl(DomaConfig.getInstance()));
        bind(ConsumedEventDao.class).toInstance(new ConsumedEventDaoImpl(DomaConfig.getInstance()));

        // repository
        bind(ProjectRepository.class).to(MySqlProjectRepository.class);
        bind(ColumnRepository.class).to(MySqlColumnRepository.class);
        bind(NoteRepository.class).to(MySqlNoteRepository.class);

        // interceptor
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transactional.class),
                new DomaTransactionInterceptor(DomaConfig.getInstance()));

        // event
        bind(EventStore.class).to(MySqlEventStore.class);
        bind(EventDispatcher.class).to(PubsubEventDispatcher.class);
        bind(ConsumedEventStore.class).to(MySqlConsumedEventStore.class);

        // service
        bind(TaskCreationService.class).to(DummyTaskCreationService.class);


        // filter
        filter("/*").through(DomainEventResetFilter.class);

    }
}
