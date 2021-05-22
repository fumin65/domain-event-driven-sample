package dev.fumin.sample.eventdriven.application.event;

import java.util.Date;
import java.util.Optional;

import dev.fumin.sample.eventdriven.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class StoredEvent<T extends DomainEvent> {

    @Setter
    private Long id;

    @Getter
    private T event;

    @Getter
    private String eventName;

    @Getter
    private Date storedAt;

    @Getter
    private long version;

    public StoredEvent(T event) {
        this.event = event;
        this.storedAt = new Date();
        this.eventName = event.getClass().getName();
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

}
