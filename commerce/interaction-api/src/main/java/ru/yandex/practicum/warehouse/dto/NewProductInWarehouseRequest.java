package ru.yandex.practicum.warehouse.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

public class NewProductInWarehouseRequest {
    @NotNull(message = "id must be not empty")
    private UUID productId;

    @NotNull(message = "fragile must be not empty")
    private boolean fragile;

    @NotNull(message = "dimension must be not empty")
    private DimensionDto dimension;

    @DecimalMin(value = "1.0", message = "weight must be greater 0")
    private double weight;

    public NewProductInWarehouseRequest(@NotNull(message = "id must be not empty") UUID productId, @NotNull(message = "fragile must be not empty") boolean fragile, @NotNull(message = "dimension must be not empty") DimensionDto dimension, @DecimalMin(value = "1.0", message = "weight must be greater 0") double weight) {
        this.productId = productId;
        this.fragile = fragile;
        this.dimension = dimension;
        this.weight = weight;
    }

    public NewProductInWarehouseRequest() {
    }

    public static NewProductInWarehouseRequestBuilder builder() {
        return new NewProductInWarehouseRequestBuilder();
    }

    public @NotNull(message = "id must be not empty") UUID getProductId() {
        return this.productId;
    }

    public void setProductId(@NotNull(message = "id must be not empty") UUID productId) {
        this.productId = productId;
    }

    public @NotNull(message = "fragile must be not empty") boolean isFragile() {
        return this.fragile;
    }

    public void setFragile(@NotNull(message = "fragile must be not empty") boolean fragile) {
        this.fragile = fragile;
    }

    public @NotNull(message = "dimension must be not empty") DimensionDto getDimension() {
        return this.dimension;
    }

    public void setDimension(@NotNull(message = "dimension must be not empty") DimensionDto dimension) {
        this.dimension = dimension;
    }

    public @DecimalMin(value = "1.0", message = "weight must be greater 0") double getWeight() {
        return this.weight;
    }

    public void setWeight(@DecimalMin(value = "1.0", message = "weight must be greater 0") double weight) {
        this.weight = weight;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof NewProductInWarehouseRequest other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$productId = this.getProductId();
        final Object other$productId = other.getProductId();
        if (!Objects.equals(this$productId, other$productId)) return false;
        if (this.isFragile() != other.isFragile()) return false;
        final Object this$dimension = this.getDimension();
        final Object other$dimension = other.getDimension();
        if (!Objects.equals(this$dimension, other$dimension)) return false;
        return Double.compare(this.getWeight(), other.getWeight()) == 0;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof NewProductInWarehouseRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $productId = this.getProductId();
        result = result * PRIME + ($productId == null ? 43 : $productId.hashCode());
        result = result * PRIME + (this.isFragile() ? 79 : 97);
        final Object $dimension = this.getDimension();
        result = result * PRIME + ($dimension == null ? 43 : $dimension.hashCode());
        final long $weight = Double.doubleToLongBits(this.getWeight());
        result = result * PRIME + (int) ($weight >>> 32 ^ $weight);
        return result;
    }

    public String toString() {
        return "NewProductInWarehouseRequest(productId=" + this.getProductId() + ", fragile=" + this.isFragile() + ", dimension=" + this.getDimension() + ", weight=" + this.getWeight() + ")";
    }

    public static class NewProductInWarehouseRequestBuilder {
        private @NotNull(message = "id must be not empty") UUID productId;
        private @NotNull(message = "fragile must be not empty") boolean fragile;
        private @NotNull(message = "dimension must be not empty") DimensionDto dimension;
        private @DecimalMin(value = "1.0", message = "weight must be greater 0") double weight;

        NewProductInWarehouseRequestBuilder() {
        }

        public NewProductInWarehouseRequestBuilder productId(@NotNull(message = "id must be not empty") UUID productId) {
            this.productId = productId;
            return this;
        }

        public NewProductInWarehouseRequestBuilder fragile(@NotNull(message = "fragile must be not empty") boolean fragile) {
            this.fragile = fragile;
            return this;
        }

        public NewProductInWarehouseRequestBuilder dimension(@NotNull(message = "dimension must be not empty") DimensionDto dimension) {
            this.dimension = dimension;
            return this;
        }

        public NewProductInWarehouseRequestBuilder weight(@DecimalMin(value = "1.0", message = "weight must be greater 0") double weight) {
            this.weight = weight;
            return this;
        }

        public NewProductInWarehouseRequest build() {
            return new NewProductInWarehouseRequest(this.productId, this.fragile, this.dimension, this.weight);
        }

        public String toString() {
            return "NewProductInWarehouseRequest.NewProductInWarehouseRequestBuilder(productId=" + this.productId + ", fragile=" + this.fragile + ", dimension=" + this.dimension + ", weight=" + this.weight + ")";
        }
    }
}