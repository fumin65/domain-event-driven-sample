package dev.fumin.sample.eventdriven.infrastructure.pubsub;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.TopicName;

import dev.fumin.sample.eventdriven.common.function.ThrowableConsumer;

public interface SafePublisher {

    void publish(TopicName topicName, ThrowableConsumer<Publisher> publisherConsumer);

}
