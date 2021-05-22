package dev.fumin.sample.eventdriven.application.event;

import java.util.Optional;

import dev.fumin.sample.eventdriven.domain.event.DomainEvent;

public interface EventStore {

    <T extends DomainEvent> StoredEvent<T> save(StoredEvent<T> event);

    <T extends DomainEvent> Optional<StoredEvent<T>> fetchById(long id);

    void delete(StoredEvent<?> event);

}
