package ru.yandex.practicum.snapshot.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.VoidDeserializer;
import org.apache.kafka.common.serialization.VoidSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.snapshot.AggregatorStarter;
import ru.yandex.practicum.snapshot.handler.SnapshotHandler;
import ru.yandex.practicum.snapshot.kafka.SensorAvroDeserializer;
import ru.yandex.practicum.snapshot.kafka.SnapshotProducer;
import ru.yandex.practicum.snapshot.kafka.SnapshotSerializer;

import java.util.List;
import java.util.Properties;

@Configuration
@ConfigurationProperties("aggregator.kafka")
public class SnapshotKafkaConfig {
    @Value("${kafka.constants.url}")
    private String kafkaUrl;
    @Value("${kafka.constants.sensor.topic}")
    private String sensorTopic;
    @Value("${kafka.constants.snapshot.topic}")
    private String snapshotTopic;
    @Value("${kafka.constants.consumer.group-id}")
    private String consumerGroupId;
    @Value("${kafka.constants.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Bean
    public AggregatorStarter aggregatorStarter(SnapshotProducer producer, SnapshotHandler handler) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, VoidDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SensorAvroDeserializer.class);

        Consumer<String, SensorEventAvro> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of(sensorTopic));

        return new AggregatorStarter(consumer, producer, handler);
    }

    @Bean
    public SnapshotProducer snapshotProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, VoidSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SnapshotSerializer.class);

        Producer<String, SensorsSnapshotAvro> producer = new KafkaProducer<>(properties);

        return new SnapshotProducer(snapshotTopic, producer);
    }
}
