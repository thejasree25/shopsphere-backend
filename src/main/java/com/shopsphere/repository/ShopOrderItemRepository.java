package com.shopsphere.repository;

import com.shopsphere.entity.ShopOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderItemRepository
        extends JpaRepository<ShopOrderItem, Long> {
}