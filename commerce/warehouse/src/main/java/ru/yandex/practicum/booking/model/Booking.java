package ru.yandex.practicum.booking.model;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID shoppingCartId;

    @ElementCollection
    @CollectionTable(name = "booking_products", joinColumns = @JoinColumn(name = "booking_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<UUID, Integer> products;

    @Column(nullable = false)
    private Double deliveryWeight;

    @Column(nullable = false)
    private Double deliveryVolume;

    @Column(nullable = false)
    private Boolean fragile;

    public Booking() {
    }

    public Booking(UUID shoppingCartId, Map<UUID, Integer> products, Double deliveryWeight, Double deliveryVolume, Boolean fragile) {
        this.shoppingCartId = shoppingCartId;
        this.products = products;
        this.deliveryWeight = deliveryWeight;
        this.deliveryVolume = deliveryVolume;
        this.fragile = fragile;
    }

    public static BookingBuilder builder() {
        return new BookingBuilder();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Booking booking = (Booking) o;
        return getShoppingCartId() != null && Objects.equals(getShoppingCartId(), booking.getShoppingCartId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public UUID getShoppingCartId() {
        return this.shoppingCartId;
    }

    public void setShoppingCartId(UUID shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Map<UUID, Integer> getProducts() {
        return this.products;
    }

    public void setProducts(Map<UUID, Integer> products) {
        this.products = products;
    }

    public Double getDeliveryWeight() {
        return this.deliveryWeight;
    }

    public void setDeliveryWeight(Double deliveryWeight) {
        this.deliveryWeight = deliveryWeight;
    }

    public Double getDeliveryVolume() {
        return this.deliveryVolume;
    }

    public void setDeliveryVolume(Double deliveryVolume) {
        this.deliveryVolume = deliveryVolume;
    }

    public Boolean getFragile() {
        return this.fragile;
    }

    public void setFragile(Boolean fragile) {
        this.fragile = fragile;
    }

    public String toString() {
        return "Booking(shoppingCartId=" + this.getShoppingCartId() + ", products=" + this.getProducts() + ", deliveryWeight=" + this.getDeliveryWeight() + ", deliveryVolume=" + this.getDeliveryVolume() + ", fragile=" + this.getFragile() + ")";
    }

    public static class BookingBuilder {
        private UUID shoppingCartId;
        private Map<UUID, Integer> products;
        private Double deliveryWeight;
        private Double deliveryVolume;
        private Boolean fragile;

        BookingBuilder() {
        }

        public BookingBuilder shoppingCartId(UUID shoppingCartId) {
            this.shoppingCartId = shoppingCartId;
            return this;
        }

        public BookingBuilder products(Map<UUID, Integer> products) {
            this.products = products;
            return this;
        }

        public BookingBuilder deliveryWeight(Double deliveryWeight) {
            this.deliveryWeight = deliveryWeight;
            return this;
        }

        public BookingBuilder deliveryVolume(Double deliveryVolume) {
            this.deliveryVolume = deliveryVolume;
            return this;
        }

        public BookingBuilder fragile(Boolean fragile) {
            this.fragile = fragile;
            return this;
        }

        public Booking build() {
            return new Booking(this.shoppingCartId, this.products, this.deliveryWeight, this.deliveryVolume, this.fragile);
        }

        public String toString() {
            return "Booking.BookingBuilder(shoppingCartId=" + this.shoppingCartId + ", products=" + this.products + ", deliveryWeight=" + this.deliveryWeight + ", deliveryVolume=" + this.deliveryVolume + ", fragile=" + this.fragile + ")";
        }
    }
}