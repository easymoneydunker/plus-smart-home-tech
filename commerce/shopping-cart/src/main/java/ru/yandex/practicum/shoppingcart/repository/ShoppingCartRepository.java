package ru.yandex.practicum.shoppingcart.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.entity.ShoppingCart;

import java.util.Optional;
import java.util.UUID;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID> {
    Optional<ShoppingCart> findByUsernameAndActive(String username, boolean active);
}