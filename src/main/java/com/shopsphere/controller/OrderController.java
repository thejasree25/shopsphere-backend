package com.shopsphere.controller;

import com.shopsphere.dto.CreateOrderRequest;
import com.shopsphere.entity.ShopOrder;
import com.shopsphere.service.ShopOrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final ShopOrderService orderService;

    public OrderController(ShopOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestBody CreateOrderRequest request) {

        try {
            ShopOrder order = orderService.createOrder(request);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("id", order.getId());
            response.put("orderId", order.getId());
            response.put("customerName", order.getCustomerName());
            response.put("email", order.getEmail());
            response.put("subtotal", order.getSubtotal());
            response.put("shippingFee", order.getShippingFee());
            response.put("totalAmount", order.getTotalAmount());
            response.put("status", order.getStatus());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(response);

        } catch (IllegalArgumentException exception) {
            Map<String, String> error = new LinkedHashMap<>();
            error.put("message", exception.getMessage());

            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        try {
            ShopOrder order = orderService.getOrder(id);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("id", order.getId());
            response.put("customerName", order.getCustomerName());
            response.put("email", order.getEmail());
            response.put("subtotal", order.getSubtotal());
            response.put("shippingFee", order.getShippingFee());
            response.put("totalAmount", order.getTotalAmount());
            response.put("status", order.getStatus());
            response.put("createdAt", order.getCreatedAt());

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException exception) {
            Map<String, String> error = new LinkedHashMap<>();
            error.put("message", exception.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(error);
        }
    }
}