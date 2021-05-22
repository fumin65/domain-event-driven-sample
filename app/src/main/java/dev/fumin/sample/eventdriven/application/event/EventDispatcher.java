package dev.fumin.sample.eventdriven.application.event;

import dev.fumin.sample.eventdriven.domain.event.DomainEvent;

public interface EventDispatcher {

    void dispatchToProxy(long eventId);

    void dispatch(long eventId, DomainEvent event);

}
