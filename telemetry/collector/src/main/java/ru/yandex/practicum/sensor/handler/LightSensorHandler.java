package ru.yandex.practicum.sensor.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.LightSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.sensor.kafka.SensorProducer;

import java.time.Instant;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LightSensorHandler implements SensorHandler {

    private static final Logger logger = LoggerFactory.getLogger(LightSensorHandler.class);
    final SensorProducer producer;

    public LightSensorHandler(SensorProducer producer) {
        this.producer = producer;
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        logger.debug("Getting message type: {}", SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT);
        return SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto eventProto) {
        logger.info("Handling light sensor event with ID: {}", eventProto.getId());

        LightSensorEvent lightSensorProto = eventProto.getLightSensorEvent();
        logger.debug("Light event details: LinkQuality = {}, Luminosity = {}",
                lightSensorProto.getLinkQuality(),
                lightSensorProto.getLuminosity());

        SensorEventAvro eventAvro = SensorEventAvro.newBuilder()
                .setId(eventProto.getId())
                .setHubId(eventProto.getHubId())
                .setTimestamp(Instant.ofEpochSecond(
                        eventProto.getTimestamp().getSeconds(),
                        eventProto.getTimestamp().getNanos()
                ))
                .setPayload(LightSensorAvro.newBuilder()
                        .setLinkQuality(lightSensorProto.getLinkQuality())
                        .setLuminosity(lightSensorProto.getLuminosity())
                        .build()
                )
                .build();

        logger.debug("Created Avro event: {}", eventAvro);
        producer.sendMessage(eventAvro);
        logger.info("Message sent to producer for event ID: {}", eventProto.getId());
    }
}
