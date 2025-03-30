package ru.yandex.practicum.hub.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.hub.model.Sensor;
import ru.yandex.practicum.hub.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class DeviceAddedHandler extends HubEventHandler<DeviceAddedEventAvro> {
    private static final Logger log = LoggerFactory.getLogger(DeviceAddedHandler.class);
    final SensorRepository repository;

    public DeviceAddedHandler(SensorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<DeviceAddedEventAvro> getType() {
        return DeviceAddedEventAvro.class;
    }

    @Override
    public void handle(HubEventAvro hubEventAvro) {
        DeviceAddedEventAvro eventAvro = instance(hubEventAvro.getPayload(), DeviceAddedEventAvro.class);

        if (eventAvro != null) {
            log.info("Добавление датчика{}", eventAvro.getId());

            repository.save(new Sensor(eventAvro.getId(), hubEventAvro.getHubId()));

            log.info("Добавлен датчик {}", eventAvro.getId());
        }
    }
}