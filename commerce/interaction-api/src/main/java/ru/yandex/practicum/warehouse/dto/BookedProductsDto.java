package ru.yandex.practicum.warehouse.dto;

public class BookedProductsDto {
    private double deliveryWeight;
    private double deliveryVolume;
    private boolean fragile;

    public BookedProductsDto(double deliveryWeight, double deliveryVolume, boolean fragile) {
        this.deliveryWeight = deliveryWeight;
        this.deliveryVolume = deliveryVolume;
        this.fragile = fragile;
    }

    public BookedProductsDto() {
    }

    public static BookedProductsDtoBuilder builder() {
        return new BookedProductsDtoBuilder();
    }

    public double getDeliveryWeight() {
        return this.deliveryWeight;
    }

    public void setDeliveryWeight(double deliveryWeight) {
        this.deliveryWeight = deliveryWeight;
    }

    public double getDeliveryVolume() {
        return this.deliveryVolume;
    }

    public void setDeliveryVolume(double deliveryVolume) {
        this.deliveryVolume = deliveryVolume;
    }

    public boolean isFragile() {
        return this.fragile;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BookedProductsDto other)) return false;
        if (!other.canEqual(this)) return false;
        if (Double.compare(this.getDeliveryWeight(), other.getDeliveryWeight()) != 0) return false;
        if (Double.compare(this.getDeliveryVolume(), other.getDeliveryVolume()) != 0) return false;
        return this.isFragile() == other.isFragile();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BookedProductsDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $deliveryWeight = Double.doubleToLongBits(this.getDeliveryWeight());
        result = result * PRIME + (int) ($deliveryWeight >>> 32 ^ $deliveryWeight);
        final long $deliveryVolume = Double.doubleToLongBits(this.getDeliveryVolume());
        result = result * PRIME + (int) ($deliveryVolume >>> 32 ^ $deliveryVolume);
        result = result * PRIME + (this.isFragile() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "BookedProductsDto(deliveryWeight=" + this.getDeliveryWeight() + ", deliveryVolume=" + this.getDeliveryVolume() + ", fragile=" + this.isFragile() + ")";
    }

    public static class BookedProductsDtoBuilder {
        private double deliveryWeight;
        private double deliveryVolume;
        private boolean fragile;

        BookedProductsDtoBuilder() {
        }

        public BookedProductsDtoBuilder deliveryWeight(double deliveryWeight) {
            this.deliveryWeight = deliveryWeight;
            return this;
        }

        public BookedProductsDtoBuilder deliveryVolume(double deliveryVolume) {
            this.deliveryVolume = deliveryVolume;
            return this;
        }

        public BookedProductsDtoBuilder fragile(boolean fragile) {
            this.fragile = fragile;
            return this;
        }

        public BookedProductsDto build() {
            return new BookedProductsDto(this.deliveryWeight, this.deliveryVolume, this.fragile);
        }

        public String toString() {
            return "BookedProductsDto.BookedProductsDtoBuilder(deliveryWeight=" + this.deliveryWeight + ", deliveryVolume=" + this.deliveryVolume + ", fragile=" + this.fragile + ")";
        }
    }
}