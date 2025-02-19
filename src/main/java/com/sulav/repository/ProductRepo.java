package com.sulav.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sulav.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
