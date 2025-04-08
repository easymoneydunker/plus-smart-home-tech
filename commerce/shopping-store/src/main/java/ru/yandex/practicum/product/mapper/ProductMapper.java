package ru.yandex.practicum.product.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.product.model.Product;
import ru.yandex.practicum.shoppingstore.dto.ProductDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {
    Product toProduct(final ProductDto productDto);

    ProductDto toProductDto(final Product product);
}