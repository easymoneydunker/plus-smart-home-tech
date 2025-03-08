package ru.yandex.practicum.snapshot.handler;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;

@Component
public class MotionSensorHandler extends SensorHandler<MotionSensorAvro> {
    @Override
    public Class<MotionSensorAvro> getMessageType() {
        return MotionSensorAvro.class;
    }
}
