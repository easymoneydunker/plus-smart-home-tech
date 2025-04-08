package ru.yandex.practicum.warehouse.dto;

public enum AddressDefaults {
    COUNTRY("default country"),
    CITY("default city"),
    STREET("default street"),
    HOUSE("default house"),
    FLAT("default flat");

    private final String value;

    AddressDefaults(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
