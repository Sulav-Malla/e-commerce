package com.sulav.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sulav.dto.PaymentDTO;
import com.sulav.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/{orderId}")
	public ResponseEntity<PaymentDTO> payOrderByCard(@PathVariable Long orderId){
		return ResponseEntity.ok(paymentService.payByCard(orderId));
	}

}
