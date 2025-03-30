package ru.yandex.practicum.snapshot;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.snapshot.handler.SnapshotHandler;

import java.time.Duration;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class SnapshotProcessor implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(SnapshotProcessor.class);
    final Consumer<String, SensorsSnapshotAvro> consumer;
    final SnapshotHandler handler;

    public SnapshotProcessor(Consumer<String, SensorsSnapshotAvro> consumer, SnapshotHandler handler) {
        this.consumer = consumer;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            log.info("Получение данных");

            while (true) {
                ConsumerRecords<String, SensorsSnapshotAvro> records = consumer.poll(Duration.ofMillis(500));

                for (ConsumerRecord<String, SensorsSnapshotAvro> record : records) {
                    handler.handle(record.value());
                }
            }
        } catch (Exception e) {
            log.error("Сбой обработки события", e);
        } finally {
            try {
                consumer.commitSync();
            } finally {
                log.info("Закрываем консьюмер");
                consumer.close();
            }
        }
    }
}