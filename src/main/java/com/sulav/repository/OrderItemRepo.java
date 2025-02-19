package com.sulav.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sulav.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

}
