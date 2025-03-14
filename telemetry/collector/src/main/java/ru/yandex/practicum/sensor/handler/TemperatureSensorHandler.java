package ru.yandex.practicum.sensor.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.TemperatureSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;
import ru.yandex.practicum.sensor.kafka.SensorProducer;

import java.time.Instant;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TemperatureSensorHandler implements SensorHandler {

    private static final Logger logger = LoggerFactory.getLogger(TemperatureSensorHandler.class);
    final SensorProducer producer;

    public TemperatureSensorHandler(SensorProducer producer) {
        this.producer = producer;
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        logger.debug("Getting message type: {}", SensorEventProto.PayloadCase.TEMPERATURE_SENSOR_EVENT);
        return SensorEventProto.PayloadCase.TEMPERATURE_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto eventProto) {
        logger.info("Handling temperature sensor event with ID: {}", eventProto.getId());

        TemperatureSensorEvent temperatureSensorEvent = eventProto.getTemperatureSensorEvent();
        logger.debug("Temperature event details: TemperatureC = {}, TemperatureF = {}",
                temperatureSensorEvent.getTemperatureC(), temperatureSensorEvent.getTemperatureF());

        SensorEventAvro eventAvro = SensorEventAvro.newBuilder()
                .setId(eventProto.getId())
                .setHubId(eventProto.getHubId())
                .setTimestamp(Instant.ofEpochSecond(
                        eventProto.getTimestamp().getSeconds(),
                        eventProto.getTimestamp().getNanos()
                ))
                .setPayload(TemperatureSensorAvro.newBuilder()
                        .setTemperatureC(temperatureSensorEvent.getTemperatureC())
                        .setTemperatureF(temperatureSensorEvent.getTemperatureF())
                        .build()
                )
                .build();

        logger.debug("Created Avro event: {}", eventAvro);
        producer.sendMessage(eventAvro);
        logger.info("Message sent to producer for event ID: {}", eventProto.getId());
    }
}
