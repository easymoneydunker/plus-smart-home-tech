package ru.yandex.practicum.hub.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.hub.repository.ScenarioRepository;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class ScenarioRemovedHandler extends HubEventHandler<ScenarioRemovedEventAvro> {
    private static final Logger log = LoggerFactory.getLogger(ScenarioRemovedHandler.class);
    final ScenarioRepository repository;

    public ScenarioRemovedHandler(ScenarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<ScenarioRemovedEventAvro> getType() {
        return ScenarioRemovedEventAvro.class;
    }

    @Override
    public void handle(HubEventAvro hubEventAvro) {
        ScenarioRemovedEventAvro eventAvro = instance(hubEventAvro.getPayload(), ScenarioRemovedEventAvro.class);

        if (eventAvro != null) {
            log.info("Удаление сценария хаба {}", hubEventAvro.getHubId());

            repository.deleteByHubIdAndName(hubEventAvro.getHubId(), eventAvro.getName());

            log.info("Удалён сценарий хаба {}", hubEventAvro.getHubId());
        }
    }
}