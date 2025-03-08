package ru.yandex.practicum.sensor.kafka;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class SensorProducer {
    final Producer<String, SensorEventAvro> producer;
    final String topic;

    public SensorProducer(Producer<String, SensorEventAvro> producer, String topic) {
        this.producer = producer;
        this.topic = topic;
    }

    public void sendMessage(SensorEventAvro eventAvro) {
        ProducerRecord<String, SensorEventAvro> producerRecord = new ProducerRecord<>(topic, eventAvro);

        producer.send(producerRecord);
        producer.flush();
    }
}
