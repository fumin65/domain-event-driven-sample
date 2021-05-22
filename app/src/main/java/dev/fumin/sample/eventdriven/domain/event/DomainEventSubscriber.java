package dev.fumin.sample.eventdriven.domain.event;

@FunctionalInterface
public interface DomainEventSubscriber<T extends DomainEvent> {
    void handle(T event);
}
