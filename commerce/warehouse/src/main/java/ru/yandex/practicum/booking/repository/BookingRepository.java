package ru.yandex.practicum.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.booking.model.Booking;

import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    Optional<Booking> findByShoppingCartId(UUID shoppingCartId);
}