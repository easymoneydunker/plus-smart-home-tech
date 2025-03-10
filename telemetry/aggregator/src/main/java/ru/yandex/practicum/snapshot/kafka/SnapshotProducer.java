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
        log.info("Отправление снапшота хаба {}", eventAvro.getHubId());
        ProducerRecord<String, SensorsSnapshotAvro> producerRecord = new ProducerRecord<>(topic, eventAvro);
        producer.send(producerRecord);
    }


    public void flush() {
        producer.flush();
    }

    public void close() {
        producer.close();
    }
}