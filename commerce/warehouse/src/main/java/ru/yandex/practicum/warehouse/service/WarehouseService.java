package ru.yandex.practicum.warehouse.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.booking.mapper.BookingMapper;
import ru.yandex.practicum.booking.model.Booking;
import ru.yandex.practicum.booking.repository.BookingRepository;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.shoppingcart.dto.ShoppingCartDto;
import ru.yandex.practicum.shoppingstore.dto.ProductDto;
import ru.yandex.practicum.shoppingstore.dto.SetProductQuantityStateRequest;
import ru.yandex.practicum.shoppingstore.enums.QuantityState;
import ru.yandex.practicum.shoppingstore.feign.ShoppingStoreClient;
import ru.yandex.practicum.warehouse.dto.*;
import ru.yandex.practicum.warehouse.mapper.WarehouseMapper;
import ru.yandex.practicum.warehouse.model.WarehouseProduct;
import ru.yandex.practicum.warehouse.repository.WarehouseRepository;

import java.util.Map;
import java.util.UUID;

import static ru.yandex.practicum.warehouse.dto.AddressDto.getDefaultAddress;

@Service
public class WarehouseService {
    private static final int LIMIT_COUNT = 5;
    private static final int ENOUGH_COUNT = 20;
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(WarehouseService.class);
    private final WarehouseRepository warehouseProductRepository;
    private final BookingRepository bookingRepository;
    private final ShoppingStoreClient shoppingStoreClient;
    private final WarehouseMapper warehouseMapper;
    private final BookingMapper bookingMapper;

    public WarehouseService(WarehouseRepository warehouseProductRepository, BookingRepository bookingRepository, ShoppingStoreClient shoppingStoreClient, WarehouseMapper warehouseMapper, BookingMapper bookingMapper) {
        this.warehouseProductRepository = warehouseProductRepository;
        this.bookingRepository = bookingRepository;
        this.shoppingStoreClient = shoppingStoreClient;
        this.warehouseMapper = warehouseMapper;
        this.bookingMapper = bookingMapper;
    }

    public void addNewProduct(NewProductInWarehouseRequest request) {
        log.info("addNewProduct {}", request);

        if (warehouseProductRepository.existsById(request.getProductId())) {
            throw new SpecifiedProductAlreadyInWarehouseException("Product already exist");
        }

        ProductDto productDto = shoppingStoreClient.getProduct(request.getProductId());
        if (productDto == null) {
            throw new RuntimeException("product not found");
        }

        WarehouseProduct newProduct = warehouseMapper.toWarehouseProduct(request);
        newProduct.setQuantityAvailable(0);

        warehouseProductRepository.save(newProduct);
    }

    @Transactional
    public void acceptReturn(Map<UUID, Integer> products) {
        log.info("acceptReturn {}", products);

        products.forEach((productId, quantity) -> {
            WarehouseProduct product = warehouseProductRepository.findById(productId)
                    .orElseThrow(() -> new NoSpecifiedProductInWarehouseException("product not found"));
            product.setQuantityAvailable(product.getQuantityAvailable() + quantity);
            warehouseProductRepository.save(product);
        });
    }

    @Transactional
    public BookedProductsDto bookProductForShoppingCart(ShoppingCartDto shoppingCart) {
        log.info("bookProductForShoppingCart {}", shoppingCart);

        double totalWeight = 0;
        double totalVolume = 0;
        boolean fragile = false;

        for (Map.Entry<UUID, Integer> entry : shoppingCart.getProducts().entrySet()) {
            UUID productId = entry.getKey();
            int requestedQuantity = entry.getValue();

            WarehouseProduct product = warehouseProductRepository.findById(productId)
                    .orElseThrow(() -> new NoSpecifiedProductInWarehouseException("product not found " + productId));

            if (product.getQuantityAvailable() < requestedQuantity) {
                throw new ProductInShoppingCartLowQuantityInWarehouse("not enough product " + productId);
            }

            product.setQuantityAvailable(product.getQuantityAvailable() - requestedQuantity);
            warehouseProductRepository.save(product);

            QuantityState newState = determineState(product.getQuantityAvailable());
            shoppingStoreClient.setProductQuantityState(
                    SetProductQuantityStateRequest.builder()
                            .productId(productId)
                            .quantityState(newState)
                            .build()
            );

            totalWeight += product.getWeight() * requestedQuantity;
            totalVolume += product.getDimension().getWidth()
                    * product.getDimension().getHeight()
                    * product.getDimension().getDepth() * requestedQuantity;

            fragile |= product.isFragile();
        }

        Booking booking = Booking.builder()
                .shoppingCartId(shoppingCart.getShoppingCartId())
                .products(shoppingCart.getProducts())
                .deliveryWeight(totalWeight)
                .deliveryVolume(totalVolume)
                .fragile(fragile)
                .build();

        bookingRepository.save(booking);

        return bookingMapper.toBookedProductDto(booking);
    }

    @Transactional(readOnly = true)
    public BookedProductsDto assemblyProductForOrderFromShoppingCart(AssemblyProductForOrderFromShoppingCartRequest request) {
        log.info("assemblyProductForOrderFromShoppingCart {} {}", request.getShoppingCartId(), request.getOrderId());

        Booking booking = bookingRepository.findById(request.getShoppingCartId())
                .orElseThrow(() -> new NoSpecifiedProductInWarehouseException("booking " + request.getShoppingCartId() + " not found"));

        if (booking.getProducts() == null || booking.getProducts().isEmpty()) {
            throw new NoSpecifiedProductInWarehouseException("cart not found");
        }

        return bookingMapper.toBookedProductDto(booking);
    }

    @Transactional
    public void addProductQuantity(AddProductToWarehouseRequest request) {
        log.info("addProductQuantity {}", request);

        WarehouseProduct product = warehouseProductRepository.findById(request.getProductId())
                .orElseThrow(() -> new NoSpecifiedProductInWarehouseException("product not found"));

        product.setQuantityAvailable(product.getQuantityAvailable() + request.getQuantity());
        WarehouseProduct updatedProduct = warehouseProductRepository.save(product);

        QuantityState newState = determineState(updatedProduct.getQuantityAvailable());
        shoppingStoreClient.setProductQuantityState(
                SetProductQuantityStateRequest.builder()
                        .productId(request.getProductId())
                        .quantityState(newState)
                        .build()
        );
    }

    public AddressDto getWarehouseAddress() {
        return getDefaultAddress();
    }

    private QuantityState determineState(int quantity) {
        if (quantity == 0) {
            return QuantityState.ENDED;
        }
        if (quantity > 0 && quantity < LIMIT_COUNT) {
            return QuantityState.FEW;
        }
        if (quantity >= LIMIT_COUNT && quantity <= ENOUGH_COUNT) {
            return QuantityState.ENOUGH;
        }
        return QuantityState.MANY;
    }
}