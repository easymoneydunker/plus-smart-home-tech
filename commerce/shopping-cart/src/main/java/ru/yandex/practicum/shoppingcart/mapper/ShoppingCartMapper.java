package ru.yandex.practicum.shoppingcart.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.shoppingcart.dto.ShoppingCartDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ShoppingCartMapper {
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "active", ignore = true)
    ru.yandex.practicum.entity.ShoppingCart toShoppingCart(final ShoppingCartDto productDto);

    ShoppingCartDto toShoppingCartDto(final ru.yandex.practicum.entity.ShoppingCart product);
}