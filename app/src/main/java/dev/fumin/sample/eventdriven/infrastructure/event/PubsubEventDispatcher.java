package dev.fumin.sample.eventdriven.infrastructure.event;

import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dev.fumin.sample.eventdriven.application.event.EventDispatcher;
import dev.fumin.sample.eventdriven.domain.model.note.NoteCreated;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectActivated;
import dev.fumin.sample.eventdriven.domain.model.project.ProjectDeactivated;
import dev.fumin.sample.eventdriven.domain.event.DomainEvent;
import dev.fumin.sample.eventdriven.infrastructure.pubsub.SafePublisher;

public class PubsubEventDispatcher implements EventDispatcher {

    private static final String PROJECT_ID = System.getenv("GOOGLE_CLOUD_PROJECT");
    private static final Map<Class<? extends DomainEvent>, String> TOPICS = new HashMap<>() {
        {
            put(NoteCreated.class, "note_created");
            put(ProjectDeactivated.class, "project_deactivated");
            put(ProjectActivated.class, "project_activated");
        }
    };

    private final SafePublisher safePublisher;
    private final Gson gson;

    @Inject
    public PubsubEventDispatcher(SafePublisher safePublisher, Gson gson) {
        this.safePublisher = safePublisher;
        this.gson = gson;
    }

    @Override
    public void dispatchToProxy(long eventId) {
        TopicName topicName = TopicName.of(PROJECT_ID, "proxy");
        safePublisher.publish(topicName, publisher -> {
            PubsubMessage message = PubsubMessage.newBuilder()
                    .putAttributes("eventId", "" + eventId)
                    .build();
            publisher.publish(message).get();
        });
    }

    @Override
    public void dispatch(long eventId, DomainEvent event) {
        String name = TOPICS.get(event.getClass());
        TopicName topicName = TopicName.of(PROJECT_ID, name);
        safePublisher.publish(topicName, publisher -> {
            String payload = gson.toJson(event);
            ByteString data = ByteString.copyFromUtf8(payload);
            PubsubMessage message = PubsubMessage.newBuilder()
                    .putAttributes("eventId", "" + eventId)
                    .setData(data)
                    .build();
            publisher.publish(message).get();
        });
    }

}
