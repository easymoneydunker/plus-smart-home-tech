package ru.yandex.practicum.sensor.handler;

import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

public interface SensorHandler {
    SensorEventProto.PayloadCase getMessageType();

    void handle(SensorEventProto eventProto);
}