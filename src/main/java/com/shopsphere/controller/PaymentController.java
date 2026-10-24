
package com.shopsphere.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopsphere.entity.Payment;
import com.shopsphere.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/demo")
    public ResponseEntity<?> demoPayment(
            @RequestBody(required = false) DemoPaymentRequest request) {

        try {
            if (request == null ||
                    request.getOrderReferenceId() == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                                "message",
                                "Order reference ID is required."));
            }

            Payment payment = paymentService.processDemoPayment(
                    request.getOrderReferenceId());

            Map<String, Object> response = new HashMap<>();
            response.put("paymentId", payment.getId());
            response.put(
                    "orderReferenceId",
                    payment.getOrderReferenceId());
            response.put("amount", payment.getAmount());
            response.put("currency", payment.getCurrency());
            response.put("status", payment.getStatus().name());
            response.put(
                    "message",
                    "Demo payment successful. No real money was charged.");

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    public static class DemoPaymentRequest {

        private Long orderReferenceId;

        public Long getOrderReferenceId() {
            return orderReferenceId;
        }

        public void setOrderReferenceId(Long orderReferenceId) {
            this.orderReferenceId = orderReferenceId;
        }
    }
}
