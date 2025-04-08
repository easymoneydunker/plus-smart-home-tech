package ru.yandex.practicum.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    private String country;
    private String city;
    private String street;
    private String house;
    private String flat;

    public static AddressDto getDefaultAddress() {
        return AddressDto.builder()
                .country(AddressDefaults.COUNTRY.value())
                .city(AddressDefaults.CITY.value())
                .street(AddressDefaults.STREET.value())
                .house(AddressDefaults.HOUSE.value())
                .flat(AddressDefaults.FLAT.value())
                .build();
    }
}
