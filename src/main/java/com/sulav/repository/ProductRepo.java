package com.sulav.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sulav.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

	Optional<Product> findByProductName(String name);
}
