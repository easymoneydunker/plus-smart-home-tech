package ru.yandex.practicum.booking.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.booking.model.Booking;
import ru.yandex.practicum.warehouse.dto.BookedProductsDto;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface BookingMapper {
    BookedProductsDto toBookedProductDto(Booking booking);
}