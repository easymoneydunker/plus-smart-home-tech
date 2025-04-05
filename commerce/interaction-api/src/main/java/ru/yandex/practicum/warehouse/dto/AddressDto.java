package ru.yandex.practicum.warehouse.dto;

import java.util.Objects;

public class AddressDto {
    private static final String DEFAULT_COUNTRY = "default country";
    private static final String DEFAULT_CITY = "default city";
    private static final String DEFAULT_STREET = "default street";
    private static final String DEFAULT_HOUSE = "default house";
    private static final String DEFAULT_FLAT = "default flat";

    private String country;
    private String city;
    private String street;
    private String house;
    private String flat;

    public AddressDto(String country, String city, String street, String house, String flat) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    public AddressDto() {
    }

    public static AddressDto getDefaultAddress() {
        return new AddressDto(
                DEFAULT_COUNTRY,
                DEFAULT_CITY,
                DEFAULT_STREET,
                DEFAULT_HOUSE,
                DEFAULT_FLAT
        );
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return this.house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return this.flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AddressDto other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$country = this.getCountry();
        final Object other$country = other.getCountry();
        if (!Objects.equals(this$country, other$country)) return false;
        final Object this$city = this.getCity();
        final Object other$city = other.getCity();
        if (!Objects.equals(this$city, other$city)) return false;
        final Object this$street = this.getStreet();
        final Object other$street = other.getStreet();
        if (!Objects.equals(this$street, other$street)) return false;
        final Object this$house = this.getHouse();
        final Object other$house = other.getHouse();
        if (!Objects.equals(this$house, other$house)) return false;
        final Object this$flat = this.getFlat();
        final Object other$flat = other.getFlat();
        return Objects.equals(this$flat, other$flat);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AddressDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $country = this.getCountry();
        result = result * PRIME + ($country == null ? 43 : $country.hashCode());
        final Object $city = this.getCity();
        result = result * PRIME + ($city == null ? 43 : $city.hashCode());
        final Object $street = this.getStreet();
        result = result * PRIME + ($street == null ? 43 : $street.hashCode());
        final Object $house = this.getHouse();
        result = result * PRIME + ($house == null ? 43 : $house.hashCode());
        final Object $flat = this.getFlat();
        result = result * PRIME + ($flat == null ? 43 : $flat.hashCode());
        return result;
    }

    public String toString() {
        return "AddressDto(country=" + this.getCountry() + ", city=" + this.getCity() + ", street=" + this.getStreet() + ", house=" + this.getHouse() + ", flat=" + this.getFlat() + ")";
    }
}