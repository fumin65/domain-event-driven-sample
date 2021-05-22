package dev.fumin.sample.eventdriven.domain.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainEventPublisher {

    private static final ThreadLocal<Map<Class<? extends DomainEvent>, List<DomainEventSubscriber<?>>>>
            SUBSCRIBERS = ThreadLocal.withInitial(HashMap::new);

    public <T extends DomainEvent> void subscribe(
            Class<T> subscribeTo,
            DomainEventSubscriber<T> subscriber
    ) {
        // ドメインイベントの型をキーにサブスクライバを登録する
        List<DomainEventSubscriber<?>> domainEventSubscribers = SUBSCRIBERS.get()
                .computeIfAbsent(subscribeTo, key -> new ArrayList<>());
        domainEventSubscribers.add(subscriber);
    }

    @SuppressWarnings("unchecked")
    public void publish(DomainEvent event) {
        Class<? extends DomainEvent> key = event.getClass();
        List<DomainEventSubscriber<?>> subscribers = SUBSCRIBERS.get().get(key);

        if (subscribers == null) {
            return;
        }

        subscribers.forEach(subscriber -> ((DomainEventSubscriber<DomainEvent>) subscriber)
                .handle(event));
    }

    public void reset() {
        SUBSCRIBERS.get().clear();
    }

}
