package com.sulav.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sulav.dto.OrderDTO;
import com.sulav.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("/{userId}/history")
	public ResponseEntity<List<OrderDTO>> viewOrderHistoryUser(@PathVariable Long userId){
		return ResponseEntity.ok(orderService.getOrderHistory(userId));
	}
	
	@GetMapping("/confirmation/{orderId}")
	public ResponseEntity<?> getOrderConfirmation(@PathVariable Long orderId){
		OrderDTO orderDTO = orderService.getConfirmation(orderId);
	    if (orderDTO == null) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("Payment not completed. Order is not confirmed.");
	    }

	    
	    return ResponseEntity.ok(orderDTO);
	}
}
