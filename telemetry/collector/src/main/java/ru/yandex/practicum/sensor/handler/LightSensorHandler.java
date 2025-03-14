package ru.yandex.practicum.sensor.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    final SensorProducer producer;

    public LightSensorHandler(SensorProducer producer) {
        this.producer = producer;
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto eventProto) {
        LightSensorEvent lightSensorProto = eventProto.getLightSensorEvent();

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

        producer.sendMessage(eventAvro);
    }
}