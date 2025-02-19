package com.sulav.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sulav.entity.Cart;

public interface CartRepo extends JpaRepository<Cart, Long> {

}
