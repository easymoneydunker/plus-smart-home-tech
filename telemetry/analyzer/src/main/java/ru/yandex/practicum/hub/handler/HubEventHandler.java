package ru.yandex.practicum.hub.handler;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Slf4j
public abstract class HubEventHandler<T> {
    public abstract Class<T> getType();

    public abstract void handle(HubEventAvro hubEventAvro);

    public T instance(Object o, Class<T> tClass) {
        if (o.getClass() != tClass) {
            log.warn("Полученная сущность не является " + tClass + ". Переданный объект " + o.getClass());
            return null;
        }

        return (T) o;
    }
}