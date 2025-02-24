package com.sulav.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sulav.entity.Cart;
import com.sulav.entity.User;

public interface CartRepo extends JpaRepository<Cart, Long> {

	Optional<Cart> findByUser(User user);
}
