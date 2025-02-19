package com.sulav.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sulav.entity.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long> {

}
