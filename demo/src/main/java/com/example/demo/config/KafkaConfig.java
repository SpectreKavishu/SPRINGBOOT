package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.demo.model.UserCreatedEvent;

@Configuration
public class KafkaConfig {

    @Bean
    KafkaTemplate<String, UserCreatedEvent> kafkaTemplate(ProducerFactory<String, UserCreatedEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    ProducerFactory<String, UserCreatedEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        final String username = "";
        final String password = "";
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "creppfp2b32l8feglrkg.any.eu-central-1.mpx.prd.cloud.redpanda.com:9092");
        configProps.put(SaslConfigs.SASL_MECHANISM, "<SCRAM-SHA-256 or SCRAM-SHA-512>");
        configProps.put(SaslConfigs.SASL_JAAS_CONFIG, String.format("org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",
                username, password));
        configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return new DefaultKafkaProducerFactory<>(configProps);
    }
}

