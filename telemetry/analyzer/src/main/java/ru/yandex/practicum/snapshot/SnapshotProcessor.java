package ru.yandex.practicum.snapshot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.snapshot.handler.SnapshotHandler;

import java.time.Duration;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Component
@Slf4j
public class SnapshotProcessor implements Runnable {
    final Consumer<String, SensorsSnapshotAvro> consumer;
    final SnapshotHandler handler;

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
        }  catch (Exception e) {
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