package ru.yandex.practicum.sensor.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.ClimateSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.sensor.kafka.SensorProducer;

import java.time.Instant;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClimateSensorHandler implements SensorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClimateSensorHandler.class);
    final SensorProducer producer;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        logger.debug("Getting message type: {}", SensorEventProto.PayloadCase.CLIMATE_SENSOR_EVENT);
        return SensorEventProto.PayloadCase.CLIMATE_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto eventProto) {
        logger.info("Handling climate sensor event with ID: {}", eventProto.getId());

        ClimateSensorEvent climateSensorProto = eventProto.getClimateSensorEvent();
        logger.debug("Climate event details: Humidity = {}, CO2 Level = {}, Temperature (C) = {}",
                climateSensorProto.getHumidity(),
                climateSensorProto.getCo2Level(),
                climateSensorProto.getTemperatureC());

        SensorEventAvro eventAvro = SensorEventAvro.newBuilder()
                .setId(eventProto.getId())
                .setHubId(eventProto.getHubId())
                .setTimestamp(Instant.ofEpochSecond(
                        eventProto.getTimestamp().getSeconds(),
                        eventProto.getTimestamp().getNanos()
                ))
                .setPayload(ClimateSensorAvro.newBuilder()
                        .setHumidity(climateSensorProto.getHumidity())
                        .setCo2Level(climateSensorProto.getCo2Level())
                        .setTemperatureC(climateSensorProto.getTemperatureC())
                        .build()
                )
                .build();

        logger.debug("Created Avro event: {}", eventAvro);
        producer.sendMessage(eventAvro);
        logger.info("Message sent to producer for event ID: {}", eventProto.getId());
    }
}
