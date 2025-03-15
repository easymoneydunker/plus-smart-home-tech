package ru.yandex.practicum.snapshot.kafka;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class SnapshotProducer {
    final Producer<String, SensorsSnapshotAvro> producer;
    final String topic;

    public void sendMessage(SensorsSnapshotAvro eventAvro) {
        log.info("Sending snapshot for hub: {}", eventAvro.getHubId());
        ProducerRecord<String, SensorsSnapshotAvro> producerRecord = new ProducerRecord<>(topic, eventAvro);
        producer.send(producerRecord, (metadata, exception) -> {
            if (exception != null) {
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
