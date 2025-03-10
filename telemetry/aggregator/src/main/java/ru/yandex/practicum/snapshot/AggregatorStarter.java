package ru.yandex.practicum.snapshot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.snapshot.handler.SnapshotHandler;
import ru.yandex.practicum.snapshot.kafka.SnapshotProducer;

import java.time.Duration;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Component
@Slf4j
public class AggregatorStarter {
    final Consumer<String, SensorEventAvro> consumer;
    final SnapshotProducer producer;
    final SnapshotHandler handler;

    public void start() {
        try {
            while (true) {
                log.info("Получение данных");

                ConsumerRecords<String, SensorEventAvro> records = consumer.poll(Duration.ofMillis(2000));

                for (ConsumerRecord<String, SensorEventAvro> record : records) {
                    Optional<SensorsSnapshotAvro> sensorsSnapshotAvro = handler.handleKafkaMessage(record.value());
                    sensorsSnapshotAvro.ifPresent(producer::sendMessage);
                }
                consumer.commitSync();
            }
        } catch (WakeupException e) {
            // Ожидаемое исключение при остановке
        } catch (Exception e) {
            log.error("Сбой обработки события сенсора", e);
        } finally {
            shutdown();
        }
    }
    public void shutdown() {
        try {
            producer.flush();
            consumer.commitSync();
        } finally {
            producer.close();
            consumer.close();
        }
    }
}