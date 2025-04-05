package ru.yandex.practicum.warehouse.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

public class AssemblyProductForOrderFromShoppingCartRequest {
    @NotNull(message = "shoppingCartId must be not empty")
    private UUID shoppingCartId;

    @NotNull(message = "orderId must be not empty")
    private UUID orderId;

    public AssemblyProductForOrderFromShoppingCartRequest(@NotNull(message = "shoppingCartId must be not empty") UUID shoppingCartId, @NotNull(message = "orderId must be not empty") UUID orderId) {
        this.shoppingCartId = shoppingCartId;
        this.orderId = orderId;
    }

    public AssemblyProductForOrderFromShoppingCartRequest() {
    }

    public static AssemblyProductForOrderFromShoppingCartRequestBuilder builder() {
        return new AssemblyProductForOrderFromShoppingCartRequestBuilder();
    }

    public @NotNull(message = "shoppingCartId must be not empty") UUID getShoppingCartId() {
        return this.shoppingCartId;
    }

    public void setShoppingCartId(@NotNull(message = "shoppingCartId must be not empty") UUID shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public @NotNull(message = "orderId must be not empty") UUID getOrderId() {
        return this.orderId;
    }

    public void setOrderId(@NotNull(message = "orderId must be not empty") UUID orderId) {
        this.orderId = orderId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AssemblyProductForOrderFromShoppingCartRequest other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$shoppingCartId = this.getShoppingCartId();
        final Object other$shoppingCartId = other.getShoppingCartId();
        if (!Objects.equals(this$shoppingCartId, other$shoppingCartId))
            return false;
        final Object this$orderId = this.getOrderId();
        final Object other$orderId = other.getOrderId();
        return Objects.equals(this$orderId, other$orderId);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AssemblyProductForOrderFromShoppingCartRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $shoppingCartId = this.getShoppingCartId();
        result = result * PRIME + ($shoppingCartId == null ? 43 : $shoppingCartId.hashCode());
        final Object $orderId = this.getOrderId();
        result = result * PRIME + ($orderId == null ? 43 : $orderId.hashCode());
        return result;
    }

    public String toString() {
        return "AssemblyProductForOrderFromShoppingCartRequest(shoppingCartId=" + this.getShoppingCartId() + ", orderId=" + this.getOrderId() + ")";
    }

    public static class AssemblyProductForOrderFromShoppingCartRequestBuilder {
        private @NotNull(message = "shoppingCartId must be not empty") UUID shoppingCartId;
        private @NotNull(message = "orderId must be not empty") UUID orderId;

        AssemblyProductForOrderFromShoppingCartRequestBuilder() {
        }

        public AssemblyProductForOrderFromShoppingCartRequestBuilder shoppingCartId(@NotNull(message = "shoppingCartId must be not empty") UUID shoppingCartId) {
            this.shoppingCartId = shoppingCartId;
            return this;
        }

        public AssemblyProductForOrderFromShoppingCartRequestBuilder orderId(@NotNull(message = "orderId must be not empty") UUID orderId) {
            this.orderId = orderId;
            return this;
        }

        public AssemblyProductForOrderFromShoppingCartRequest build() {
            return new AssemblyProductForOrderFromShoppingCartRequest(this.shoppingCartId, this.orderId);
        }

        public String toString() {
            return "AssemblyProductForOrderFromShoppingCartRequest.AssemblyProductForOrderFromShoppingCartRequestBuilder(shoppingCartId=" + this.shoppingCartId + ", orderId=" + this.orderId + ")";
        }
    }
}