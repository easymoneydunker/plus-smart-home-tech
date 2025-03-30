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
    private final Consumer<String, SensorEventAvro> consumer;
    private final SnapshotProducer producer;
    private final SnapshotHandler handler;

    public void start() {
        try {
            log.info("Получение данных");
            while (true) {
                ConsumerRecords<String, SensorEventAvro> records = consumer.poll(Duration.ofMillis(500));

                for (ConsumerRecord<String, SensorEventAvro> record : records) {
                    Optional<SensorsSnapshotAvro> sensorsSnapshotAvro = handler.handleKafkaMessage(record.value());
                    sensorsSnapshotAvro.ifPresent(producer::sendMessage);
                }
            }
        } catch (WakeupException e) {
            log.info("Kafka consumer wakeup signal received. Procceding to shutdown");
        } catch (Exception e) {
            log.error("Exception while processing sensor event: ", e);
        } finally {
            try {
                producer.flush();
                consumer.commitSync();
            } finally {
                log.info("Closing consumer");
                consumer.close();
                log.info("Closing producer");
                producer.close();
            }
        }
    }
}