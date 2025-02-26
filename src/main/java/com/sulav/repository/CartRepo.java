package com.sulav.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sulav.entity.Cart;
import com.sulav.entity.UserProfile;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

	Optional<Cart> findByUser(UserProfile user);
}
