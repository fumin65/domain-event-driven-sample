package dev.fumin.sample.eventdriven.infrastructure.pubsub;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.TopicName;

import dev.fumin.sample.eventdriven.common.function.ThrowableConsumer;

public class RemoteSafePublisher implements SafePublisher {

    @Override
    public void publish(TopicName topicName, ThrowableConsumer<Publisher> publisherConsumer) {
        Publisher publisher = null;
        try {
            publisher = Publisher.newBuilder(topicName).build();
            publisherConsumer.accept(publisher);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (publisher != null) {
                publisher.shutdown();
            }
        }
    }

}
