package dev.fumin.sample.eventdriven.presentation.event;

import java.util.Date;

import lombok.Value;

@Value
public class ConsumedEvent {
    long eventId;
    String receiver;
    Date receivedAt;
}
