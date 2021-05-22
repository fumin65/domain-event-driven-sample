package dev.fumin.sample.eventdriven.infrastructure.pubsub;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.TopicName;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import dev.fumin.sample.eventdriven.common.function.ThrowableConsumer;

public class LocalSafePublisher implements SafePublisher {

    private final String port;

    public LocalSafePublisher(String port) {
        this.port = port;
    }

    @Override
    public void publish(TopicName topicName, ThrowableConsumer<Publisher> publisherConsumer) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(port).usePlaintext().build();
        TransportChannelProvider channelProvider = FixedTransportChannelProvider.create(
                GrpcTransportChannel.create(channel));
        CredentialsProvider credentialsProvider = NoCredentialsProvider.create();
        try {
            Publisher publisher = Publisher.newBuilder(topicName)
                    .setChannelProvider(channelProvider)
                    .setCredentialsProvider(credentialsProvider)
                    .build();
            publisherConsumer.accept(publisher);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            channel.shutdown();
        }
    }

}
