package com.felipepossari.quota.common.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${kafka.user.topic}")
    private String userTopic;

    @Value("${kafka.quota.topic}")
    private String quotaTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        return new KafkaAdmin(Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress));
    }

    @Bean
    public NewTopic userTopic() {
        return new NewTopic(userTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic quotaTopic() {
        return new NewTopic(quotaTopic, 1, (short) 1);
    }

    @Bean
    public ProducerFactory<String, String> getProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getProducerMapConfig());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(getProducerFactory());
    }

    private Map<String, Object> getProducerMapConfig() {
        return Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    }
}
