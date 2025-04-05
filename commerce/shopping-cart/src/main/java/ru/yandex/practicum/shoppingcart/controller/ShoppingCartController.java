package ru.yandex.practicum.shoppingcart.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shoppingcart.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.shoppingcart.dto.ShoppingCartDto;
import ru.yandex.practicum.shoppingcart.service.ShoppingCartService;
import ru.yandex.practicum.warehouse.dto.BookedProductsDto;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public ShoppingCartDto getShoppingCart(@RequestParam String username) {
        return shoppingCartService.getShoppingCart(username);
    }

    @PutMapping
    public ShoppingCartDto addProducts(@RequestParam String username, @RequestBody Map<UUID, Integer> products) {
        return shoppingCartService.addProducts(username, products);
    }

    @DeleteMapping
    public void deactivateShoppingCart(@RequestParam String username) {
        shoppingCartService.deactivateShoppingCart(username);
    }

    @PostMapping("/remove")
    public ShoppingCartDto removeProducts(
            @RequestParam String username, @RequestBody Map<UUID, Integer> products) {
        return shoppingCartService.removeProducts(username, products);
    }

    @PostMapping("/change-quantity")
    public ShoppingCartDto changeProductQuantity(
            @RequestParam String username, @Valid @RequestBody ChangeProductQuantityRequest request) {
        return shoppingCartService.changeProductQuantity(username, request);
    }

    @PostMapping("/booking")
    public BookedProductsDto bookProducts(@RequestParam String username) {
        return shoppingCartService.bookProducts(username);
    }
}