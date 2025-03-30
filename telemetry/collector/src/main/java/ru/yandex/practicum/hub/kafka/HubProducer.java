package ru.yandex.practicum.hub.kafka;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class HubProducer {
    final String topic;
    final Producer<String, HubEventAvro> producer;
    final Logger log = LoggerFactory.getLogger(HubProducer.class);

    public HubProducer(String topic, Producer<String, HubEventAvro> producer) {
        this.topic = topic;
        this.producer = producer;
    }

    public void sendMessage(HubEventAvro event) {
        log.info("Sending hub event with hub ID: {}", event.getHubId());
        log.debug("Sending hub event: {}", event);

        ProducerRecord<String, HubEventAvro> producerRecord = new ProducerRecord<>(topic, event);

        try {
            producer.send(producerRecord, (metadata, exception) -> {
                if (exception == null) {
                    log.info("Successfully sent hub event with hub ID: {}", event.getHubId());
                } else {
                    log.error("Error sending hub event with hub ID: {}. Exception: {}", event.getHubId(), exception.getMessage(), exception);
                }
            });
            producer.flush();
        } catch (Exception e) {
            log.error("Exception occurred while sending hub event with hub ID: {}", event.getHubId(), e);
        }
    }
}
