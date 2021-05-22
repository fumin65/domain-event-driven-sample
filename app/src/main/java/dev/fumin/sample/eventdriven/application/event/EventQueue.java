package dev.fumin.sample.eventdriven.application.event;

import java.util.Optional;

import javax.inject.Inject;

import dev.fumin.sample.eventdriven.domain.event.DomainEvent;

public class EventQueue {

    private final EventStore store;
    private final EventDispatcher dispatcher;

    @Inject
    public EventQueue(EventStore store, EventDispatcher dispatcher) {
        this.store = store;
        this.dispatcher = dispatcher;
    }

    public void push(DomainEvent event) {
        StoredEvent<DomainEvent> storedEvent = new StoredEvent<>(event);
        // DBへ保存
        storedEvent = store.save(storedEvent);
        // 保存したイベントのIDをプロクシへ送信
        dispatcher.dispatchToProxy(storedEvent.getId()
                .orElseThrow(() -> new IllegalStateException("event id is null.")));
    }

    public <T extends DomainEvent> Optional<T> pop(long id) {
        return store.<T>fetchById(id).map(stored -> {
            // DBから削除することで、送信済みとして扱う。
            store.delete(stored);
            return stored.getEvent();
        });
    }

}
