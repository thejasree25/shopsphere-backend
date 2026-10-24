package com.shopsphere.service;

import com.shopsphere.dto.CreateOrderRequest;
import com.shopsphere.dto.OrderItemRequest;
import com.shopsphere.entity.Product;
import com.shopsphere.entity.ShopOrder;
import com.shopsphere.entity.ShopOrderItem;
import com.shopsphere.enums.OrderStatus;
import com.shopsphere.repository.ProductRepository;
import com.shopsphere.repository.ShopOrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class ShopOrderService {

    private static final BigDecimal FREE_SHIPPING_THRESHOLD =
            new BigDecimal("999.00");

    private static final BigDecimal SHIPPING_FEE =
            new BigDecimal("49.00");

    private final ShopOrderRepository orderRepository;
    private final ProductRepository productRepository;

    public ShopOrderService(
            ShopOrderRepository orderRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ShopOrder createOrder(CreateOrderRequest request) {

        if (request == null || request.getItems() == null
                || request.getItems().isEmpty()) {
            throw new IllegalArgumentException(
                    "Your shopping cart is empty.");
        }

        String customerName = required(
                request.getCustomerName(), "Customer name");
        String email = required(request.getEmail(), "Email");
        String phone = required(request.getPhone(), "Phone");
        String address = required(
                request.getShippingAddress(), "Shipping address");

        if (!Pattern.matches(
                "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", email)) {
            throw new IllegalArgumentException(
                    "Please enter a valid email address.");
        }

        if (!phone.matches("[0-9+() -]{7,20}")) {
            throw new IllegalArgumentException(
                    "Please enter a valid phone number.");
        }

        if (address.length() < 8) {
            throw new IllegalArgumentException(
                    "Please enter a complete shipping address.");
        }

        ShopOrder order = new ShopOrder();
        order.setCustomerName(customerName);
        order.setEmail(email);
        order.setPhone(phone);
        order.setShippingAddress(address);
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        BigDecimal subtotal = BigDecimal.ZERO;
        Set<Long> seenProductIds = new HashSet<>();

        for (OrderItemRequest itemRequest : request.getItems()) {

            if (itemRequest == null
                    || itemRequest.getProductId() == null
                    || itemRequest.getProductId() <= 0
                    || itemRequest.getQuantity() == null
                    || itemRequest.getQuantity() <= 0
                    || itemRequest.getQuantity() > 100) {
                throw new IllegalArgumentException(
                        "Your cart contains an invalid product or quantity.");
            }

            Long productId = itemRequest.getProductId();

            if (!seenProductIds.add(productId)) {
                throw new IllegalArgumentException(
                        "Duplicate products are not allowed in the order. "
                        + "Combine their quantities first.");
            }

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Product not found: " + productId));

            BigDecimal unitPrice;

            try {
                unitPrice = new BigDecimal(
                        String.valueOf(product.getPrice()))
                        .setScale(2, RoundingMode.UNNECESSARY);
            } catch (NumberFormatException
                    | ArithmeticException exception) {
                throw new IllegalArgumentException(
                        "The price for product " + productId
                        + " is invalid.");
            }

            if (unitPrice.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException(
                        "Product prices cannot be negative.");
            }

            BigDecimal lineTotal = unitPrice.multiply(
                    BigDecimal.valueOf(itemRequest.getQuantity()));

            ShopOrderItem orderItem = new ShopOrderItem();
            orderItem.setProductId(productId);
            orderItem.setProductName(product.getName());
            orderItem.setUnitPrice(unitPrice);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setLineTotal(lineTotal);

            order.addItem(orderItem);
            subtotal = subtotal.add(lineTotal);
        }

        subtotal = subtotal.setScale(2, RoundingMode.UNNECESSARY);

        BigDecimal shippingFee =
                subtotal.compareTo(FREE_SHIPPING_THRESHOLD) >= 0
                ? BigDecimal.ZERO.setScale(2)
                : SHIPPING_FEE;

        order.setSubtotal(subtotal);
        order.setShippingFee(shippingFee);
        order.setTotalAmount(subtotal.add(shippingFee));

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public ShopOrder getOrder(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid order ID.");
        }

        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Order not found."));
    }

    private String required(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    field + " is required.");
        }

        return value.trim();
    }
}