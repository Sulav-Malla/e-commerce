package com.sulav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sulav.entity.CartItem;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
