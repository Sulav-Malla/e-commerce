package com.sulav.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sulav.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
