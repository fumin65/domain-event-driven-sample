package dev.fumin.sample.pubsub.emulator;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.api.gax.rpc.NotFoundException;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;

import java.util.HashMap;
import java.util.Map;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class EmulatorInitializer {

    public static void main(String[] args) throws Exception {
        String port = System.getenv("PUBSUB_EMULATOR_HOST");
        System.out.println("emulator port : " + port);
        ManagedChannel channel = ManagedChannelBuilder.forTarget(port)
                .usePlaintext()
                .build();

        TransportChannelProvider channelProvider = FixedTransportChannelProvider.create(
                GrpcTransportChannel.create(channel));
        CredentialsProvider credentialsProvider = NoCredentialsProvider.create();

        try {
            TopicAdminClient topicAdminClient = TopicAdminClient.create(
                    TopicAdminSettings.newBuilder()
                            .setTransportChannelProvider(channelProvider)
                            .setCredentialsProvider(credentialsProvider)
                            .build());
            SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(
                    SubscriptionAdminSettings.newBuilder()
                            .setTransportChannelProvider(channelProvider)
                            .setCredentialsProvider(credentialsProvider)
                            .build());

            String projectId = System.getenv("GOOGLE_CLOUD_PROJECT");
            TopicName proxyTopicName = TopicName.of(projectId, "proxy");
            TopicName noteCreatedTopicName = TopicName.of(projectId, "note_created");

            Map<TopicName, String> subscriptions = new HashMap<>();
            subscriptions.put(proxyTopicName, "http://localhost:8080/event/proxy");
            subscriptions.put(noteCreatedTopicName, "http://localhost:8080/event/receiver/note-created");

            System.out.println("check topic");
            try {
                topicAdminClient.getTopic(proxyTopicName);
                System.out.println("topic already exists.");
            } catch (NotFoundException e) {
                subscriptions.forEach((key, value) -> {
                    Topic topic = topicAdminClient.createTopic(key);
                    System.out.println("Topic is created: " + topic);

                    PushConfig pushConfig = PushConfig.newBuilder()
                            .setPushEndpoint(value)
                            .build();

                    String[] paths = value.split("/");
                    String name = paths[paths.length - 1];
                    ProjectSubscriptionName subscriptionName =
                            ProjectSubscriptionName.of(key.getProject(), name);
                    Subscription subscription = subscriptionAdminClient.createSubscription(
                            subscriptionName, key, pushConfig, 60);

                    System.out.println("Subscription is created : " + subscription);
                });
            }

        } finally {
            channel.shutdown();
        }
    }

}
