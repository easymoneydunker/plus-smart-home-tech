package ru.yandex.practicum.shoppingcart.dto;

import java.util.Map;
import java.util.UUID;

public class ShoppingCartDto {

    private UUID shoppingCartId;

    private Map<UUID, Integer> products;

    public ShoppingCartDto(UUID shoppingCartId, Map<UUID, Integer> products) {
        this.shoppingCartId = shoppingCartId;
        this.products = products;
    }

    public ShoppingCartDto() {
    }

    public static ShoppingCartDtoBuilder builder() {
        return new ShoppingCartDtoBuilder();
    }

    public UUID getShoppingCartId() {
        return this.shoppingCartId;
    }

    public Map<UUID, Integer> getProducts() {
        return this.products;
    }

    public void setShoppingCartId(UUID shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public void setProducts(Map<UUID, Integer> products) {
        this.products = products;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ShoppingCartDto)) return false;
        final ShoppingCartDto other = (ShoppingCartDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$shoppingCartId = this.getShoppingCartId();
        final Object other$shoppingCartId = other.getShoppingCartId();
        if (this$shoppingCartId == null ? other$shoppingCartId != null : !this$shoppingCartId.equals(other$shoppingCartId))
            return false;
        final Object this$products = this.getProducts();
        final Object other$products = other.getProducts();
        if (this$products == null ? other$products != null : !this$products.equals(other$products)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ShoppingCartDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $shoppingCartId = this.getShoppingCartId();
        result = result * PRIME + ($shoppingCartId == null ? 43 : $shoppingCartId.hashCode());
        final Object $products = this.getProducts();
        result = result * PRIME + ($products == null ? 43 : $products.hashCode());
        return result;
    }

    public String toString() {
        return "ShoppingCartDto(shoppingCartId=" + this.getShoppingCartId() + ", products=" + this.getProducts() + ")";
    }

    public static class ShoppingCartDtoBuilder {
        private UUID shoppingCartId;
        private Map<UUID, Integer> products;

        ShoppingCartDtoBuilder() {
        }

        public ShoppingCartDtoBuilder shoppingCartId(UUID shoppingCartId) {
            this.shoppingCartId = shoppingCartId;
            return this;
        }

        public ShoppingCartDtoBuilder products(Map<UUID, Integer> products) {
            this.products = products;
            return this;
        }

        public ShoppingCartDto build() {
            return new ShoppingCartDto(this.shoppingCartId, this.products);
        }

        public String toString() {
            return "ShoppingCartDto.ShoppingCartDtoBuilder(shoppingCartId=" + this.shoppingCartId + ", products=" + this.products + ")";
        }
    }
}