package ru.yandex.practicum.hub.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;


public abstract class HubEventHandler<T> {
    private static final Logger log = LoggerFactory.getLogger(HubEventHandler.class);

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