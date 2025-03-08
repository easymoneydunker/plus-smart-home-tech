package ru.yandex.practicum.snapshot.kafka;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SnapshotProducer {
    final Producer<String, SensorsSnapshotAvro> producer;
    final String topic;

    public SnapshotProducer(Producer<String, SensorsSnapshotAvro> producer, String topic) {
        this.producer = producer;
        this.topic = topic;
    }

    public void sendMessage(SensorsSnapshotAvro eventAvro) {
        ProducerRecord<String, SensorsSnapshotAvro> producerRecord = new ProducerRecord<>(topic, eventAvro);

        producer.send(producerRecord);
        producer.flush();
    }

    public void flush() {
        producer.flush();
    }

    public void close() {
        producer.close();
    }
}