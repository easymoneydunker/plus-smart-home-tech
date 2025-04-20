package ru.yandex.practicum.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.entity.Order;
import ru.yandex.practicum.order.dto.OrderDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OrderMapper {
    OrderDto toOrderDto(Order order);
}