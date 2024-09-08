package com.example.demo;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.errors.TopicExistsException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class Main {
    static Properties clientProperties() {
        // Set up producer and consumer properties
        final String username = "<username>";
        final String password = "<password>";
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "creppfp2b32l8feglrkg.any.eu-central-1.mpx.prd.cloud.redpanda.com:9092");
        props.put(SaslConfigs.SASL_MECHANISM, "<SCRAM-SHA-256 or SCRAM-SHA-512>");
        props.put(SaslConfigs.SASL_JAAS_CONFIG, String.format("org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",
                username, password));
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return props;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        createTopic("demo-topic");
    }

    static void createTopic(String topicName) throws InterruptedException, ExecutionException {
        try (AdminClient adminClient = AdminClient.create(clientProperties())) {
            adminClient.createTopics(Collections.singleton(new NewTopic(topicName, Optional.of(1), Optional.empty())))
                    .all()
                    .get();
            System.out.println("Created topic");
        } catch (ExecutionException | InterruptedException e) {
            if (!(e.getCause() instanceof TopicExistsException)) {
                throw e;
            }
            System.out.println("Topic already exists");
        }
    }
}
