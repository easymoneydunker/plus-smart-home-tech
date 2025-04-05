package ru.yandex.practicum.entity;


import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID shoppingCartId;

    @Column(nullable = false)
    private String username;

    @ElementCollection
    @CollectionTable(name = "shopping_cart_items", joinColumns = @JoinColumn(name = "shopping_cart_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<UUID, Integer> products = new HashMap<>();

    @Column(nullable = false)
    private boolean active = true;

    public ShoppingCart() {
    }

    public ShoppingCart(UUID shoppingCartId, String username, Map<UUID, Integer> products, boolean active) {
        this.shoppingCartId = shoppingCartId;
        this.username = username;
        this.products = products;
        this.active = active;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ShoppingCart that = (ShoppingCart) o;
        return getShoppingCartId() != null && Objects.equals(getShoppingCartId(), that.getShoppingCartId());
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

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<UUID, Integer> getProducts() {
        return this.products;
    }

    public void setProducts(Map<UUID, Integer> products) {
        this.products = products;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String toString() {
        return "ShoppingCart(shoppingCartId=" + this.getShoppingCartId() + ", username=" + this.getUsername() + ", products=" + this.getProducts() + ", active=" + this.isActive() + ")";
    }
}