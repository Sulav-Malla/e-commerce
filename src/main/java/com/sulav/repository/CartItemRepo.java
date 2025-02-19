package com.sulav.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sulav.entity.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
