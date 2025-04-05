package ru.yandex.practicum.shoppingcart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;


public class ChangeProductQuantityRequest {

    @NotNull(message = "Product id must be not empty")
    private UUID productId;

    @Min(value = 0, message = "newQuantity must greater then 0")
    private int newQuantity;

    public ChangeProductQuantityRequest(@NotNull(message = "Product id must be not empty") UUID productId, @Min(value = 0, message = "newQuantity must greater then 0") int newQuantity) {
        this.productId = productId;
        this.newQuantity = newQuantity;
    }

    public ChangeProductQuantityRequest() {
    }

    public static ChangeProductQuantityRequestBuilder builder() {
        return new ChangeProductQuantityRequestBuilder();
    }

    public @NotNull(message = "Product id must be not empty") UUID getProductId() {
        return this.productId;
    }

    public @Min(value = 0, message = "newQuantity must greater then 0") int getNewQuantity() {
        return this.newQuantity;
    }

    public void setProductId(@NotNull(message = "Product id must be not empty") UUID productId) {
        this.productId = productId;
    }

    public void setNewQuantity(@Min(value = 0, message = "newQuantity must greater then 0") int newQuantity) {
        this.newQuantity = newQuantity;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ChangeProductQuantityRequest)) return false;
        final ChangeProductQuantityRequest other = (ChangeProductQuantityRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$productId = this.getProductId();
        final Object other$productId = other.getProductId();
        if (this$productId == null ? other$productId != null : !this$productId.equals(other$productId)) return false;
        if (this.getNewQuantity() != other.getNewQuantity()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ChangeProductQuantityRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $productId = this.getProductId();
        result = result * PRIME + ($productId == null ? 43 : $productId.hashCode());
        result = result * PRIME + this.getNewQuantity();
        return result;
    }

    public String toString() {
        return "ChangeProductQuantityRequest(productId=" + this.getProductId() + ", newQuantity=" + this.getNewQuantity() + ")";
    }

    public static class ChangeProductQuantityRequestBuilder {
        private @NotNull(message = "Product id must be not empty") UUID productId;
        private @Min(value = 0, message = "newQuantity must greater then 0") int newQuantity;

        ChangeProductQuantityRequestBuilder() {
        }

        public ChangeProductQuantityRequestBuilder productId(@NotNull(message = "Product id must be not empty") UUID productId) {
            this.productId = productId;
            return this;
        }

        public ChangeProductQuantityRequestBuilder newQuantity(@Min(value = 0, message = "newQuantity must greater then 0") int newQuantity) {
            this.newQuantity = newQuantity;
            return this;
        }

        public ChangeProductQuantityRequest build() {
            return new ChangeProductQuantityRequest(this.productId, this.newQuantity);
        }

        public String toString() {
            return "ChangeProductQuantityRequest.ChangeProductQuantityRequestBuilder(productId=" + this.productId + ", newQuantity=" + this.newQuantity + ")";
        }
    }
}