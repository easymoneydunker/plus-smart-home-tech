package ru.yandex.practicum.warehouse.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AddProductToWarehouseRequest {
    @NotNull(message = "id must be not empty")
    private UUID productId;

    @Min(value = 1, message = "quantity must be greater 0")
    private int quantity;

    public AddProductToWarehouseRequest(@NotNull(message = "id must be not empty") UUID productId, @Min(value = 1, message = "quantity must be greater 0") int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public AddProductToWarehouseRequest() {
    }

    public static AddProductToWarehouseRequestBuilder builder() {
        return new AddProductToWarehouseRequestBuilder();
    }

    public @NotNull(message = "id must be not empty") UUID getProductId() {
        return this.productId;
    }

    public @Min(value = 1, message = "quantity must be greater 0") int getQuantity() {
        return this.quantity;
    }

    public void setProductId(@NotNull(message = "id must be not empty") UUID productId) {
        this.productId = productId;
    }

    public void setQuantity(@Min(value = 1, message = "quantity must be greater 0") int quantity) {
        this.quantity = quantity;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AddProductToWarehouseRequest)) return false;
        final AddProductToWarehouseRequest other = (AddProductToWarehouseRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$productId = this.getProductId();
        final Object other$productId = other.getProductId();
        if (this$productId == null ? other$productId != null : !this$productId.equals(other$productId)) return false;
        if (this.getQuantity() != other.getQuantity()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AddProductToWarehouseRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $productId = this.getProductId();
        result = result * PRIME + ($productId == null ? 43 : $productId.hashCode());
        result = result * PRIME + this.getQuantity();
        return result;
    }

    public String toString() {
        return "AddProductToWarehouseRequest(productId=" + this.getProductId() + ", quantity=" + this.getQuantity() + ")";
    }

    public static class AddProductToWarehouseRequestBuilder {
        private @NotNull(message = "id must be not empty") UUID productId;
        private @Min(value = 1, message = "quantity must be greater 0") int quantity;

        AddProductToWarehouseRequestBuilder() {
        }

        public AddProductToWarehouseRequestBuilder productId(@NotNull(message = "id must be not empty") UUID productId) {
            this.productId = productId;
            return this;
        }

        public AddProductToWarehouseRequestBuilder quantity(@Min(value = 1, message = "quantity must be greater 0") int quantity) {
            this.quantity = quantity;
            return this;
        }

        public AddProductToWarehouseRequest build() {
            return new AddProductToWarehouseRequest(this.productId, this.quantity);
        }

        public String toString() {
            return "AddProductToWarehouseRequest.AddProductToWarehouseRequestBuilder(productId=" + this.productId + ", quantity=" + this.quantity + ")";
        }
    }
}