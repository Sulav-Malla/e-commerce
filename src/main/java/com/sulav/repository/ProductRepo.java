package com.sulav.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sulav.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

	Optional<Product> findByProductName(String name);
}
