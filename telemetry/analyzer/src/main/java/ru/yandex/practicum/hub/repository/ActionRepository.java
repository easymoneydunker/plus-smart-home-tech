package ru.yandex.practicum.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.hub.model.Action;

public interface ActionRepository extends JpaRepository<Action, Long> {
}