package ru.yandex.practicum.hub;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.hub.handler.HubEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.time.Duration;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class HubEventProcessor implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(HubEventProcessor.class);
    final Map<Class<? extends SpecificRecord>, HubEventHandler> handlers;
    final Consumer<String, HubEventAvro> consumer;

    public HubEventProcessor(Map<Class<? extends SpecificRecord>, HubEventHandler> handlers, Consumer<String, HubEventAvro> consumer) {
        this.handlers = handlers;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        try {
            log.info("Получение данных");

            while (true) {
                ConsumerRecords<String, HubEventAvro> records = consumer.poll(Duration.ofMillis(500));

                for (ConsumerRecord<String, HubEventAvro> record : records) {
                    HubEventAvro value = record.value();

                    if (handlers.containsKey(value.getPayload().getClass())) {
                        handlers.get(value.getPayload().getClass()).handle(value);
                    } else {
                        throw new IllegalArgumentException("Не найден обработчик " + value.getPayload().getClass());
                    }
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