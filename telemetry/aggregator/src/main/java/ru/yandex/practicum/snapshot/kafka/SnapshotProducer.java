package ru.yandex.practicum.snapshot.kafka;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.snapshot.AggregatorStarter;

import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SnapshotProducer {
    private static final Logger log = LoggerFactory.getLogger(SnapshotProducer.class);
    final Producer<String, SensorsSnapshotAvro> producer;
    final String topic;

    public SnapshotProducer(String topic, Producer<String, SensorsSnapshotAvro> producer) {
        this.producer = producer;
        this.topic = topic;
    }

    public void sendMessage(SensorsSnapshotAvro eventAvro) {
        log.info("Sending snapshot for hub: {}", eventAvro.getHubId());
        ProducerRecord<String, SensorsSnapshotAvro> producerRecord = new ProducerRecord<>(topic, eventAvro);
        producer.send(producerRecord, (metadata, exception) -> {
            if (Objects.nonNull(exception)) {
                log.error("Failed to send snapshot for hub: {}", eventAvro.getHubId(), exception);
            } else {
                log.info("Snapshot sent successfully for hub: {} to topic: {}, partition: {}, offset: {}",
                        eventAvro.getHubId(), metadata.topic(), metadata.partition(), metadata.offset());
            }
        });
    }

    public void flush() {
        log.info("Flushing producer for topic: {}", topic);
        producer.flush();
    }

    public void close() {
        log.info("Closing producer for topic: {}", topic);
        producer.close();
    }
}
