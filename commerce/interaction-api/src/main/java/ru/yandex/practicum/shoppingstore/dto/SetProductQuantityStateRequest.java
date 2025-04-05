package ru.yandex.practicum.shoppingstore.dto;

import jakarta.validation.constraints.NotNull;
import ru.yandex.practicum.shoppingstore.enums.QuantityState;

import java.util.UUID;

public class SetProductQuantityStateRequest {
    @NotNull
    private UUID productId;

    @NotNull
    private QuantityState quantityState;

    public SetProductQuantityStateRequest(@NotNull UUID productId, @NotNull QuantityState quantityState) {
        this.productId = productId;
        this.quantityState = quantityState;
    }

    public SetProductQuantityStateRequest() {
    }

    public static SetProductQuantityStateRequestBuilder builder() {
        return new SetProductQuantityStateRequestBuilder();
    }

    public @NotNull UUID getProductId() {
        return this.productId;
    }

    public @NotNull QuantityState getQuantityState() {
        return this.quantityState;
    }

    public void setProductId(@NotNull UUID productId) {
        this.productId = productId;
    }

    public void setQuantityState(@NotNull QuantityState quantityState) {
        this.quantityState = quantityState;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SetProductQuantityStateRequest)) return false;
        final SetProductQuantityStateRequest other = (SetProductQuantityStateRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$productId = this.getProductId();
        final Object other$productId = other.getProductId();
        if (this$productId == null ? other$productId != null : !this$productId.equals(other$productId)) return false;
        final Object this$quantityState = this.getQuantityState();
        final Object other$quantityState = other.getQuantityState();
        if (this$quantityState == null ? other$quantityState != null : !this$quantityState.equals(other$quantityState))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SetProductQuantityStateRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $productId = this.getProductId();
        result = result * PRIME + ($productId == null ? 43 : $productId.hashCode());
        final Object $quantityState = this.getQuantityState();
        result = result * PRIME + ($quantityState == null ? 43 : $quantityState.hashCode());
        return result;
    }

    public String toString() {
        return "SetProductQuantityStateRequest(productId=" + this.getProductId() + ", quantityState=" + this.getQuantityState() + ")";
    }

    public static class SetProductQuantityStateRequestBuilder {
        private @NotNull UUID productId;
        private @NotNull QuantityState quantityState;

        SetProductQuantityStateRequestBuilder() {
        }

        public SetProductQuantityStateRequestBuilder productId(@NotNull UUID productId) {
            this.productId = productId;
            return this;
        }

        public SetProductQuantityStateRequestBuilder quantityState(@NotNull QuantityState quantityState) {
            this.quantityState = quantityState;
            return this;
        }

        public SetProductQuantityStateRequest build() {
            return new SetProductQuantityStateRequest(this.productId, this.quantityState);
        }

        public String toString() {
            return "SetProductQuantityStateRequest.SetProductQuantityStateRequestBuilder(productId=" + this.productId + ", quantityState=" + this.quantityState + ")";
        }
    }
}