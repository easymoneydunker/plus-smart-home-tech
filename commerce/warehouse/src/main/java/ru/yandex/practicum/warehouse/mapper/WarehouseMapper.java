package ru.yandex.practicum.warehouse.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.dimension.mapper.DimensionMapper;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.model.WarehouseProduct;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {DimensionMapper.class}
)
public interface WarehouseMapper {
    @Mapping(target = "quantityAvailable", constant = "0")
    WarehouseProduct toWarehouseProduct(NewProductInWarehouseRequest request);
}