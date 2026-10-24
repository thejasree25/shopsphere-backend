
package com.shopsphere.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopsphere.entity.Payment;
import com.shopsphere.entity.ShopOrder;
import com.shopsphere.enums.OrderStatus;
import com.shopsphere.enums.PaymentStatus;
import com.shopsphere.repository.PaymentRepository;
import com.shopsphere.repository.ShopOrderRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ShopOrderRepository shopOrderRepository;

    @Value("${app.payment.demo-enabled:false}")
    private boolean demoPaymentEnabled;

    public PaymentService(
            PaymentRepository paymentRepository,
            ShopOrderRepository shopOrderRepository) {
        this.paymentRepository = paymentRepository;
        this.shopOrderRepository = shopOrderRepository;
    }

    @Transactional
    public Payment processDemoPayment(Long orderReferenceId) {

        if (!demoPaymentEnabled) {
            throw new IllegalStateException(
                    "Demo payments are disabled.");
        }

        if (orderReferenceId == null || orderReferenceId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid order ID.");
        }

        ShopOrder order = shopOrderRepository.findById(orderReferenceId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Order not found: " + orderReferenceId));

        if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
            throw new IllegalArgumentException(
                    "Order is not waiting for payment.");
        }

        BigDecimal amount = order.getTotalAmount();

        if (amount == null) {
            throw new IllegalArgumentException(
                    "Order amount is missing.");
        }

        amount = amount.setScale(2, RoundingMode.HALF_UP);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Order amount must be greater than zero.");
        }

        Payment payment = new Payment();
        payment.setOrderReferenceId(order.getId());
        payment.setRazorpayOrderId(
                "DEMO_ORDER_" + UUID.randomUUID());
        payment.setRazorpayPaymentId(
                "DEMO_PAYMENT_" + UUID.randomUUID());
        payment.setAmount(amount);
        payment.setCurrency("INR");
        payment.setStatus(PaymentStatus.SUCCESS);

        Payment savedPayment = paymentRepository.save(payment);

        order.setStatus(OrderStatus.PAID);
        shopOrderRepository.save(order);

        return savedPayment;
    }
}
