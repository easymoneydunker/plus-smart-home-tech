package ru.yandex.practicum.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
}