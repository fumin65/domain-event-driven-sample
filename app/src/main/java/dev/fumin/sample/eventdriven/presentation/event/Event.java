package dev.fumin.sample.eventdriven.presentation.event;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Value;

@Value
public class Event {

    long eventId;
    Message message;
    String subscription;

    public static class Message {

        @Getter
        private final String messageId;

        @Getter
        private final String data;

        @Getter
        private final Map<String, String> attributes = new HashMap<>();

        public Message(String messageId, String data) {
            this.messageId = messageId;
            this.data = data;
        }

    }

}
