package ru.yandex.practicum.sensor.kafka;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.hub.kafka.HubProducer;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@FieldDefaults(level = AccessLevel.PRIVATE)

public class SensorProducer {
    final Producer<String, SensorEventAvro> producer;
    final String topic;
    final Logger log = LoggerFactory.getLogger(SensorProducer.class);

    public SensorProducer(String topic, Producer<String, SensorEventAvro> producer) {
        this.topic = topic;
        this.producer = producer;
    }

    public void sendMessage(SensorEventAvro eventAvro) {
        log.info("Sending sensor event with ID: {}", eventAvro.getId());
        log.debug("Sending sensor event: {}", eventAvro);

        ProducerRecord<String, SensorEventAvro> producerRecord = new ProducerRecord<>(topic, eventAvro);

        try {
            producer.send(producerRecord, (metadata, exception) -> {
                if (exception == null) {
                    log.info("Successfully sent sensor event with ID: {}", eventAvro.getId());
                } else {
                    log.error("Error sending sensor event with ID: {}. Exception: {}", eventAvro.getId(), exception.getMessage(), exception);
                }
            });
            producer.flush();
        } catch (Exception e) {
            log.error("Exception occurred while sending sensor event with ID: {}", eventAvro.getId(), e);
        }
    }
}
