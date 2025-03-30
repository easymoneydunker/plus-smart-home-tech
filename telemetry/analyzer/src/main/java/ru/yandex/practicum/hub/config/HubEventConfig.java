package ru.yandex.practicum.hub.config;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.VoidDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.hub.HubEventProcessor;
import ru.yandex.practicum.hub.handler.HubEventHandler;
import ru.yandex.practicum.hub.kafka.HubEventDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties("analyzer.hub.kafka")
public class HubEventConfig {
    @Value("${kafka.constants.url}")
    private String url;
    @Value("${kafka.constants.hub.topic}")
    private String topic;

    @Bean
    public HubEventProcessor hubEventProcessor(Set<HubEventHandler> handlers) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "hub");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, VoidDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, HubEventDeserializer.class);

        Consumer<String, HubEventAvro> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of(topic));

        return new HubEventProcessor(getHandlers(handlers), consumer);
    }

    private Map<Class<? extends SpecificRecord>, HubEventHandler> getHandlers(Set<HubEventHandler> handlers) {
        return handlers.stream()
                .collect(Collectors.toMap(
                        HubEventHandler::getType,
                        Function.identity()
                ));
    }
}