package ru.yandex.practicum.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private String id;

    @NotBlank(message = "Product name must not be blank")
    private String name;

    @NotBlank(message = "Product description must not be blank")
    private String description;

    @Positive(message = "Product price must be positive")
    @Min(value = 1, message = "Product price must be greater than 0")
    private Double price;

    private String imageSrc;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Product state must be specified")
    private ProductState productState;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Quantity state must be specified")
    private QuantityState quantityState;
}