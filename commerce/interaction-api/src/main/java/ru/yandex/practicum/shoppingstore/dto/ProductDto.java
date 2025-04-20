package ru.yandex.practicum.shoppingstore.dto;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.yandex.practicum.shoppingstore.enums.ProductCategory;
import ru.yandex.practicum.shoppingstore.enums.ProductState;
import ru.yandex.practicum.shoppingstore.enums.QuantityState;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductDto {
    private UUID productId;

    @NotBlank
    private String productName;
    @NotBlank
    private String description;

    private String imageSrc;

    @NotNull
    private QuantityState quantityState;

    @NotNull
    private ProductState productState;

    @DecimalMin(value = "1.0", message = "rate must be greater 0")
    @DecimalMax(value = "5.0", message = "rate must be less 5")
    private double rating;

    @NotNull(message = "Категория товара не должна быть пустой")
    private ProductCategory productCategory;

    @DecimalMin(value = "1.0", message = "Цена товара должна быть не меньше 1")
    private BigDecimal price;

    public ProductDto(UUID productId, @NotBlank String productName, @NotBlank String description, String imageSrc, QuantityState quantityState, ProductState productState, @DecimalMin(value = "1.0", message = "rate must be greater 0") @DecimalMax(value = "5.0", message = "rate must be less 5") double rating, ProductCategory productCategory, @DecimalMin(value = "1.0", message = "Цена товара должна быть не меньше 1") BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.imageSrc = imageSrc;
        this.quantityState = quantityState;
        this.productState = productState;
        this.rating = rating;
        this.productCategory = productCategory;
        this.price = price;
    }

    public ProductDto() {
    }

    public static ProductDtoBuilder builder() {
        return new ProductDtoBuilder();
    }

    public UUID getProductId() {
        return this.productId;
    }

    public @NotBlank String getProductName() {
        return this.productName;
    }

    public @NotBlank String getDescription() {
        return this.description;
    }

    public String getImageSrc() {
        return this.imageSrc;
    }

    public QuantityState getQuantityState() {
        return this.quantityState;
    }

    public ProductState getProductState() {
        return this.productState;
    }

    public @DecimalMin(value = "1.0", message = "rate must be greater 0") @DecimalMax(value = "5.0", message = "rate must be less 5") double getRating() {
        return this.rating;
    }

    public ProductCategory getProductCategory() {
        return this.productCategory;
    }

    public @DecimalMin(value = "1.0", message = "Цена товара должна быть не меньше 1") BigDecimal getPrice() {
        return this.price;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public void setProductName(@NotBlank String productName) {
        this.productName = productName;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public void setQuantityState(QuantityState quantityState) {
        this.quantityState = quantityState;
    }

    public void setProductState(ProductState productState) {
        this.productState = productState;
    }

    public void setRating(@DecimalMin(value = "1.0", message = "rate must be greater 0") @DecimalMax(value = "5.0", message = "rate must be less 5") double rating) {
        this.rating = rating;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public void setPrice(@DecimalMin(value = "1.0", message = "Цена товара должна быть не меньше 1") BigDecimal price) {
        this.price = price;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductDto)) return false;
        final ProductDto other = (ProductDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$productId = this.getProductId();
        final Object other$productId = other.getProductId();
        if (this$productId == null ? other$productId != null : !this$productId.equals(other$productId)) return false;
        final Object this$productName = this.getProductName();
        final Object other$productName = other.getProductName();
        if (this$productName == null ? other$productName != null : !this$productName.equals(other$productName))
            return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$imageSrc = this.getImageSrc();
        final Object other$imageSrc = other.getImageSrc();
        if (this$imageSrc == null ? other$imageSrc != null : !this$imageSrc.equals(other$imageSrc)) return false;
        final Object this$quantityState = this.getQuantityState();
        final Object other$quantityState = other.getQuantityState();
        if (this$quantityState == null ? other$quantityState != null : !this$quantityState.equals(other$quantityState))
            return false;
        final Object this$productState = this.getProductState();
        final Object other$productState = other.getProductState();
        if (this$productState == null ? other$productState != null : !this$productState.equals(other$productState))
            return false;
        if (Double.compare(this.getRating(), other.getRating()) != 0) return false;
        final Object this$productCategory = this.getProductCategory();
        final Object other$productCategory = other.getProductCategory();
        if (this$productCategory == null ? other$productCategory != null : !this$productCategory.equals(other$productCategory))
            return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProductDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $productId = this.getProductId();
        result = result * PRIME + ($productId == null ? 43 : $productId.hashCode());
        final Object $productName = this.getProductName();
        result = result * PRIME + ($productName == null ? 43 : $productName.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $imageSrc = this.getImageSrc();
        result = result * PRIME + ($imageSrc == null ? 43 : $imageSrc.hashCode());
        final Object $quantityState = this.getQuantityState();
        result = result * PRIME + ($quantityState == null ? 43 : $quantityState.hashCode());
        final Object $productState = this.getProductState();
        result = result * PRIME + ($productState == null ? 43 : $productState.hashCode());
        final long $rating = Double.doubleToLongBits(this.getRating());
        result = result * PRIME + (int) ($rating >>> 32 ^ $rating);
        final Object $productCategory = this.getProductCategory();
        result = result * PRIME + ($productCategory == null ? 43 : $productCategory.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        return result;
    }

    public String toString() {
        return "ProductDto(productId=" + this.getProductId() + ", productName=" + this.getProductName() + ", description=" + this.getDescription() + ", imageSrc=" + this.getImageSrc() + ", quantityState=" + this.getQuantityState() + ", productState=" + this.getProductState() + ", rating=" + this.getRating() + ", productCategory=" + this.getProductCategory() + ", price=" + this.getPrice() + ")";
    }

    public static class ProductDtoBuilder {
        private UUID productId;
        private @NotBlank String productName;
        private @NotBlank String description;
        private String imageSrc;
        private QuantityState quantityState;
        private ProductState productState;
        private @DecimalMin(value = "1.0", message = "rate must be greater 0")
        @DecimalMax(value = "5.0", message = "rate must be less 5") double rating;
        private ProductCategory productCategory;
        private @DecimalMin(value = "1.0", message = "Цена товара должна быть не меньше 1") BigDecimal price;

        ProductDtoBuilder() {
        }

        public ProductDtoBuilder productId(UUID productId) {
            this.productId = productId;
            return this;
        }

        public ProductDtoBuilder productName(@NotBlank String productName) {
            this.productName = productName;
            return this;
        }

        public ProductDtoBuilder description(@NotBlank String description) {
            this.description = description;
            return this;
        }

        public ProductDtoBuilder imageSrc(String imageSrc) {
            this.imageSrc = imageSrc;
            return this;
        }

        public ProductDtoBuilder quantityState(QuantityState quantityState) {
            this.quantityState = quantityState;
            return this;
        }

        public ProductDtoBuilder productState(ProductState productState) {
            this.productState = productState;
            return this;
        }

        public ProductDtoBuilder rating(@DecimalMin(value = "1.0", message = "rate must be greater 0") @DecimalMax(value = "5.0", message = "rate must be less 5") double rating) {
            this.rating = rating;
            return this;
        }

        public ProductDtoBuilder productCategory(ProductCategory productCategory) {
            this.productCategory = productCategory;
            return this;
        }

        public ProductDtoBuilder price(@DecimalMin(value = "1.0", message = "Цена товара должна быть не меньше 1") BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(this.productId, this.productName, this.description, this.imageSrc, this.quantityState, this.productState, this.rating, this.productCategory, this.price);
        }

        public String toString() {
            return "ProductDto.ProductDtoBuilder(productId=" + this.productId + ", productName=" + this.productName + ", description=" + this.description + ", imageSrc=" + this.imageSrc + ", quantityState=" + this.quantityState + ", productState=" + this.productState + ", rating=" + this.rating + ", productCategory=" + this.productCategory + ", price=" + this.price + ")";
        }
    }
}