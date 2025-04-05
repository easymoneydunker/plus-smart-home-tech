package ru.yandex.practicum.product.model;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;
import ru.yandex.practicum.shoppingstore.enums.ProductCategory;
import ru.yandex.practicum.shoppingstore.enums.ProductState;
import ru.yandex.practicum.shoppingstore.enums.QuantityState;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String description;

    private String imageSrc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuantityState quantityState;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductState productState;

    @Column(nullable = false)
    private double rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory productCategory;

    @Column(nullable = false)
    private BigDecimal price;

    public Product() {
    }

    public Product(UUID productId, String productName, String description, String imageSrc, QuantityState quantityState, ProductState productState, double rating, ProductCategory productCategory, BigDecimal price) {
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

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Product product = (Product) o;
        return getProductId() != null && Objects.equals(getProductId(), product.getProductId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public UUID getProductId() {
        return this.productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageSrc() {
        return this.imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public QuantityState getQuantityState() {
        return this.quantityState;
    }

    public void setQuantityState(QuantityState quantityState) {
        this.quantityState = quantityState;
    }

    public ProductState getProductState() {
        return this.productState;
    }

    public void setProductState(ProductState productState) {
        this.productState = productState;
    }

    public double getRating() {
        return this.rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ProductCategory getProductCategory() {
        return this.productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String toString() {
        return "Product(productId=" + this.getProductId() + ", productName=" + this.getProductName() + ", description=" + this.getDescription() + ", imageSrc=" + this.getImageSrc() + ", quantityState=" + this.getQuantityState() + ", productState=" + this.getProductState() + ", rating=" + this.getRating() + ", productCategory=" + this.getProductCategory() + ", price=" + this.getPrice() + ")";
    }

    public static class ProductBuilder {
        private UUID productId;
        private String productName;
        private String description;
        private String imageSrc;
        private QuantityState quantityState;
        private ProductState productState;
        private double rating;
        private ProductCategory productCategory;
        private BigDecimal price;

        ProductBuilder() {
        }

        public ProductBuilder productId(UUID productId) {
            this.productId = productId;
            return this;
        }

        public ProductBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder imageSrc(String imageSrc) {
            this.imageSrc = imageSrc;
            return this;
        }

        public ProductBuilder quantityState(QuantityState quantityState) {
            this.quantityState = quantityState;
            return this;
        }

        public ProductBuilder productState(ProductState productState) {
            this.productState = productState;
            return this;
        }

        public ProductBuilder rating(double rating) {
            this.rating = rating;
            return this;
        }

        public ProductBuilder productCategory(ProductCategory productCategory) {
            this.productCategory = productCategory;
            return this;
        }

        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Product build() {
            return new Product(this.productId, this.productName, this.description, this.imageSrc, this.quantityState, this.productState, this.rating, this.productCategory, this.price);
        }

        public String toString() {
            return "Product.ProductBuilder(productId=" + this.productId + ", productName=" + this.productName + ", description=" + this.description + ", imageSrc=" + this.imageSrc + ", quantityState=" + this.quantityState + ", productState=" + this.productState + ", rating=" + this.rating + ", productCategory=" + this.productCategory + ", price=" + this.price + ")";
        }
    }
}