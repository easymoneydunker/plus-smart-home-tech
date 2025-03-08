package ru.yandex.practicum.snapshot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.snapshot.handler.SnapshotHandler;
import ru.yandex.practicum.snapshot.kafka.SnapshotProducer;

import java.time.Duration;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class AggregatorStarter {
    Logger log = org.slf4j.LoggerFactory.getLogger(AggregatorStarter.class);
    final Consumer<String, SensorEventAvro> consumer;
    final SnapshotProducer producer;
    final SnapshotHandler handler;

    public AggregatorStarter(Consumer<String, SensorEventAvro> consumer, SnapshotProducer producer, SnapshotHandler handler) {
        this.consumer = consumer;
        this.producer = producer;
        this.handler = handler;
    }

    public void start() {
        try {            ConsumerRecords<String, SensorEventAvro> records = consumer.poll(Duration.ofMillis(500));

            for (ConsumerRecord<String, SensorEventAvro> record: records) {
                Optional<SensorsSnapshotAvro> sensorsSnapshotAvro = handler.handleKafkaMessage(record.value());
                sensorsSnapshotAvro.ifPresent(producer::sendMessage);
            }
        } catch (WakeupException e) {

        } catch (Exception e) {
            log.error("Сбой обработки события сенсора", e);
        } finally {
            try {
                producer.flush();
                consumer.commitSync();
            } finally {
                log.info("Закрываем консьюмер");
                consumer.close();
                log.info("Закрываем продюсер");
                producer.close();
            }
        }
    }
}