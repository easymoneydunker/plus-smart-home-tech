package ru.yandex.practicum.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartDto {

    private UUID shoppingCartId;

    private Map<UUID, Integer> products;
}
