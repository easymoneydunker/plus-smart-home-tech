package ru.yandex.practicum.warehouse.model;

import jakarta.persistence.*;
import ru.yandex.practicum.dimension.model.Dimension;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "warehouse_products")
public class WarehouseProduct {

    @Id
    private UUID productId;

    @Column(nullable = false)
    private boolean fragile;

    @Embedded
    private Dimension dimension;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private int quantityAvailable;

    public WarehouseProduct(UUID productId, boolean fragile, Dimension dimension, double weight, int quantityAvailable) {
        this.productId = productId;
        this.fragile = fragile;
        this.dimension = dimension;
        this.weight = weight;
        this.quantityAvailable = quantityAvailable;
    }

    public WarehouseProduct() {
    }

    public static WarehouseProductBuilder builder() {
        return new WarehouseProductBuilder();
    }

    public UUID getProductId() {
        return this.productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public boolean isFragile() {
        return this.fragile;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getQuantityAvailable() {
        return this.quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof WarehouseProduct other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$productId = this.getProductId();
        final Object other$productId = other.getProductId();
        if (!Objects.equals(this$productId, other$productId)) return false;
        if (this.isFragile() != other.isFragile()) return false;
        final Object this$dimension = this.getDimension();
        final Object other$dimension = other.getDimension();
        if (!Objects.equals(this$dimension, other$dimension)) return false;
        if (Double.compare(this.getWeight(), other.getWeight()) != 0) return false;
        return this.getQuantityAvailable() == other.getQuantityAvailable();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WarehouseProduct;
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
        result = result * PRIME + this.getQuantityAvailable();
        return result;
    }

    public String toString() {
        return "WarehouseProduct(productId=" + this.getProductId() + ", fragile=" + this.isFragile() + ", dimension=" + this.getDimension() + ", weight=" + this.getWeight() + ", quantityAvailable=" + this.getQuantityAvailable() + ")";
    }

    public static class WarehouseProductBuilder {
        private UUID productId;
        private boolean fragile;
        private Dimension dimension;
        private double weight;
        private int quantityAvailable;

        WarehouseProductBuilder() {
        }

        public WarehouseProductBuilder productId(UUID productId) {
            this.productId = productId;
            return this;
        }

        public WarehouseProductBuilder fragile(boolean fragile) {
            this.fragile = fragile;
            return this;
        }

        public WarehouseProductBuilder dimension(Dimension dimension) {
            this.dimension = dimension;
            return this;
        }

        public WarehouseProductBuilder weight(double weight) {
            this.weight = weight;
            return this;
        }

        public WarehouseProductBuilder quantityAvailable(int quantityAvailable) {
            this.quantityAvailable = quantityAvailable;
            return this;
        }

        public WarehouseProduct build() {
            return new WarehouseProduct(this.productId, this.fragile, this.dimension, this.weight, this.quantityAvailable);
        }

        public String toString() {
            return "WarehouseProduct.WarehouseProductBuilder(productId=" + this.productId + ", fragile=" + this.fragile + ", dimension=" + this.dimension + ", weight=" + this.weight + ", quantityAvailable=" + this.quantityAvailable + ")";
        }
    }
}