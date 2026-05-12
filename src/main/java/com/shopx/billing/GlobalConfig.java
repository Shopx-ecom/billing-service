package com.shopx.billing;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sameer Shaikh
 * @date 10-05-2026
 * @description
 */

@Configuration
public class GlobalConfig {

  /*  @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {

        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        ProducerFactory<String, Object> producerFactory =
                new DefaultKafkaProducerFactory<>(config);

        return new KafkaTemplate<>(producerFactory);
    }*/

    @Bean
    public KafkaTemplate<String, PaymentEvent> kafkaTemplate() {

        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        ProducerFactory<String, PaymentEvent> producerFactory =
                new DefaultKafkaProducerFactory<>(config);

        return new KafkaTemplate<>(producerFactory);
    }
}
