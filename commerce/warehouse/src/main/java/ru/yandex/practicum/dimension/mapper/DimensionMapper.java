package ru.yandex.practicum.dimension.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.dimension.model.Dimension;
import ru.yandex.practicum.warehouse.dto.DimensionDto;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface DimensionMapper {
    Dimension toDimension(DimensionDto dto);
}