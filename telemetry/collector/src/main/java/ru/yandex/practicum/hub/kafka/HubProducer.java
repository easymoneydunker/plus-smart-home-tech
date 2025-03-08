package ru.yandex.practicum.hub.kafka;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class HubProducer {
    final String topic;
    final Producer<String, HubEventAvro> producer;

    public HubProducer(String topic, Producer<String, HubEventAvro> producer) {
        this.topic = topic;
        this.producer = producer;
    }

    public void sendMessage(HubEventAvro event) {

        ProducerRecord<String, HubEventAvro> producerRecord = new ProducerRecord<>(topic, event);

        producer.send(producerRecord);
        producer.flush();
    }
}