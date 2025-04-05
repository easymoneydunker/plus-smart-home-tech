package ru.yandex.practicum.warehouse.dto;


import jakarta.validation.constraints.DecimalMin;

public class DimensionDto {
    @DecimalMin(value = "1.0", message = "must be greater 0.")
    private double width;

    @DecimalMin(value = "1.0", message = "must be greater 0.")
    private double height;

    @DecimalMin(value = "1.0", message = "must be greater 0.")
    private double depth;

    public DimensionDto(@DecimalMin(value = "1.0", message = "must be greater 0.") double width, @DecimalMin(value = "1.0", message = "must be greater 0.") double height, @DecimalMin(value = "1.0", message = "must be greater 0.") double depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public DimensionDto() {
    }

    public static DimensionDtoBuilder builder() {
        return new DimensionDtoBuilder();
    }

    public @DecimalMin(value = "1.0", message = "must be greater 0.") double getWidth() {
        return this.width;
    }

    public void setWidth(@DecimalMin(value = "1.0", message = "must be greater 0.") double width) {
        this.width = width;
    }

    public @DecimalMin(value = "1.0", message = "must be greater 0.") double getHeight() {
        return this.height;
    }

    public void setHeight(@DecimalMin(value = "1.0", message = "must be greater 0.") double height) {
        this.height = height;
    }

    public @DecimalMin(value = "1.0", message = "must be greater 0.") double getDepth() {
        return this.depth;
    }

    public void setDepth(@DecimalMin(value = "1.0", message = "must be greater 0.") double depth) {
        this.depth = depth;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DimensionDto other)) return false;
        if (!other.canEqual(this)) return false;
        if (Double.compare(this.getWidth(), other.getWidth()) != 0) return false;
        if (Double.compare(this.getHeight(), other.getHeight()) != 0) return false;
        return Double.compare(this.getDepth(), other.getDepth()) == 0;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DimensionDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $width = Double.doubleToLongBits(this.getWidth());
        result = result * PRIME + (int) ($width >>> 32 ^ $width);
        final long $height = Double.doubleToLongBits(this.getHeight());
        result = result * PRIME + (int) ($height >>> 32 ^ $height);
        final long $depth = Double.doubleToLongBits(this.getDepth());
        result = result * PRIME + (int) ($depth >>> 32 ^ $depth);
        return result;
    }

    public String toString() {
        return "DimensionDto(width=" + this.getWidth() + ", height=" + this.getHeight() + ", depth=" + this.getDepth() + ")";
    }

    public static class DimensionDtoBuilder {
        private @DecimalMin(value = "1.0", message = "must be greater 0.") double width;
        private @DecimalMin(value = "1.0", message = "must be greater 0.") double height;
        private @DecimalMin(value = "1.0", message = "must be greater 0.") double depth;

        DimensionDtoBuilder() {
        }

        public DimensionDtoBuilder width(@DecimalMin(value = "1.0", message = "must be greater 0.") double width) {
            this.width = width;
            return this;
        }

        public DimensionDtoBuilder height(@DecimalMin(value = "1.0", message = "must be greater 0.") double height) {
            this.height = height;
            return this;
        }

        public DimensionDtoBuilder depth(@DecimalMin(value = "1.0", message = "must be greater 0.") double depth) {
            this.depth = depth;
            return this;
        }

        public DimensionDto build() {
            return new DimensionDto(this.width, this.height, this.depth);
        }

        public String toString() {
            return "DimensionDto.DimensionDtoBuilder(width=" + this.width + ", height=" + this.height + ", depth=" + this.depth + ")";
        }
    }
}