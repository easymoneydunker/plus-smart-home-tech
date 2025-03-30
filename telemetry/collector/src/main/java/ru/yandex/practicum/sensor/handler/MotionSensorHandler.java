package ru.yandex.practicum.sensor.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.MotionSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.sensor.kafka.SensorProducer;

import java.time.Instant;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MotionSensorHandler implements SensorHandler {

    private static final Logger logger = LoggerFactory.getLogger(MotionSensorHandler.class);
    final SensorProducer producer;

    @Autowired
    public MotionSensorHandler(SensorProducer producer) {
        this.producer = producer;
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        logger.debug("Getting message type: {}", SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT);
        return SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto eventProto) {
        logger.info("Handling motion sensor event with ID: {}", eventProto.getId());

        MotionSensorEvent motionSensorEvent = eventProto.getMotionSensorEvent();
        logger.debug("Motion event details: Motion = {}, LinkQuality = {}, Voltage = {}",
                motionSensorEvent.getMotion(),
                motionSensorEvent.getLinkQuality(),
                motionSensorEvent.getVoltage());

        SensorEventAvro eventAvro = SensorEventAvro.newBuilder()
                .setId(eventProto.getId())
                .setHubId(eventProto.getHubId())
                .setTimestamp(Instant.ofEpochSecond(
                        eventProto.getTimestamp().getSeconds(),
                        eventProto.getTimestamp().getNanos()
                ))
                .setPayload(MotionSensorAvro.newBuilder()
                        .setMotion(motionSensorEvent.getMotion())
                        .setLinkQuality(motionSensorEvent.getLinkQuality())
                        .setVoltage(motionSensorEvent.getVoltage())
                        .build()
                )
                .build();

        logger.debug("Created Avro event: {}", eventAvro);
        producer.sendMessage(eventAvro);
        logger.info("Message sent to producer for event ID: {}", eventProto.getId());
    }
}
