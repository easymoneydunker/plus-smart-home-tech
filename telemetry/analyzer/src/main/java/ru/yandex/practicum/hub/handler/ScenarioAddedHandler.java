package ru.yandex.practicum.hub.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.hub.model.*;
import ru.yandex.practicum.hub.repository.ScenarioRepository;
import ru.yandex.practicum.hub.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class ScenarioAddedHandler extends HubEventHandler<ScenarioAddedEventAvro> {
    private static final Logger log = LoggerFactory.getLogger(ScenarioAddedHandler.class);
    final SensorRepository sensorRepository;
    final ScenarioRepository scenarioRepository;

    public ScenarioAddedHandler(SensorRepository sensorRepository, ScenarioRepository scenarioRepository) {
        this.sensorRepository = sensorRepository;
        this.scenarioRepository = scenarioRepository;
    }

    @Override
    public Class<ScenarioAddedEventAvro> getType() {
        return ScenarioAddedEventAvro.class;
    }

    @Override
    public void handle(HubEventAvro hubEventAvro) {
        ScenarioAddedEventAvro eventAvro = instance(hubEventAvro.getPayload(), ScenarioAddedEventAvro.class);

        if (eventAvro != null) {
            log.info("Добавление сценария хаба {}", hubEventAvro.getHubId());
            log.debug("Добавление сценария {}", hubEventAvro);

            Scenario scenario = mapToScenario(eventAvro, hubEventAvro.getHubId());

            scenarioRepository.save(scenario);

            log.info("Добавлен сценарий хаба {}", hubEventAvro.getHubId());
        }
    }

    private Scenario mapToScenario(ScenarioAddedEventAvro eventAvro, String hubId) {
        Scenario scenario = new Scenario();
        scenario.setName(eventAvro.getName());
        scenario.setHubId(hubId);
        scenario.setActions(mapActions(eventAvro.getActions(), hubId));
        scenario.setConditions(mapConditions(eventAvro.getConditions(), hubId));

        return scenario;
    }

    private List<Action> mapActions(List<DeviceActionAvro> actions, String hubId) {
        List<Action> result = new ArrayList<>();

        for (DeviceActionAvro deviceAction : actions) {
            Sensor performer = sensorRepository.findByIdAndHubId(deviceAction.getSensorId(), hubId).orElse(null);

            if (performer != null) {
                Action action = new Action();
                action.setType(ActionType.valueOf(deviceAction.getType().name()));
                action.setValue(deviceAction.getValue());
                action.setActionPerformer(performer);
                result.add(action);
            }
        }

        return result;
    }

    private List<Condition> mapConditions(List<ScenarioConditionAvro> conditions, String hubId) {
        List<Condition> result = new ArrayList<>();

        for (ScenarioConditionAvro scenarioCondition : conditions) {
            Sensor source = sensorRepository.findByIdAndHubId(scenarioCondition.getSensorId(), hubId).orElse(null);

            if (source != null) {
                Condition condition = new Condition();
                condition.setType(ConditionType.valueOf(scenarioCondition.getType().name()));
                condition.setOperation(ConditionOperationType.valueOf(scenarioCondition.getOperation().name()));

                Object value = scenarioCondition.getValue();
                if (value instanceof Integer) {
                    condition.setValue((Integer) value);
                } else if (value instanceof Boolean) {
                    condition.setValue((Boolean) value ? 1 : 0);
                }

                condition.setConditionSource(source);
                result.add(condition);
            }
        }

        return result;
    }
}