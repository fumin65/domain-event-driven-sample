package dev.fumin.sample.eventdriven.infrastructure.event;

import com.google.gson.Gson;

import javax.inject.Inject;

import dev.fumin.sample.eventdriven.domain.event.DomainEvent;

public class DomainEventSerializer {

    private final Gson gson;

    @Inject
    public DomainEventSerializer(Gson gson) {
        this.gson = gson;
    }

    public String serialize(DomainEvent event) {
        return gson.toJson(event);
    }

    @SuppressWarnings("unchecked")
    public <T extends DomainEvent> T deserialize(String source, String eventName) {
        try {
            Class<?> clazz = Class.forName(eventName);
            return (T) gson.fromJson(source, clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
