package com.shopsphere.repository;

import com.shopsphere.entity.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderRepository
        extends JpaRepository<ShopOrder, Long> {
}