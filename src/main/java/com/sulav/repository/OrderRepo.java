package com.sulav.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sulav.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

	Optional<List<Order>> findByUser_uID(Long id);
}
