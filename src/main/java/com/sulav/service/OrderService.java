package com.sulav.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sulav.dto.OrderDTO;
import com.sulav.dto.OrderItemDTO;
import com.sulav.entity.Order;
import com.sulav.entity.OrderItem;
import com.sulav.entity.Payment;
import com.sulav.repository.OrderRepo;

@Service
public class OrderService {

	@Autowired
	private OrderRepo orderRepository;

	private OrderDTO convertToOrderDTO(Order order) {
		List<OrderItemDTO> orderItems = order.getOrderItems().stream().map(this::convertToOrderItemDTO).collect(Collectors.toList());
		return new OrderDTO(
				order.getOrderId(),
				order.getTotalAmount(),
				order.getPayment().getPaymentMethod(),
				orderItems
				);
		
	}
	
	private OrderItemDTO convertToOrderItemDTO(OrderItem item) {
		return new OrderItemDTO(
				item.getOrderItemId(),
				item.getProduct().getProductName(),
				item.getQuantity(),
				item.getPrice()
				);
				
	}
	
	

	// get all orders for a user
	public List<OrderDTO> getOrderHistory(Long userId) {
		List<Order> orders = orderRepository.findByUser_uID(userId).orElseThrow(() -> new RuntimeException("User not found!"));
		List<OrderDTO> orderHistory = orders.stream().map(this::convertToOrderDTO).collect(Collectors.toList());
		return orderHistory;
	}

	// order confirmation
	public OrderDTO getConfirmation(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found!"));
		Payment payment = order.getPayment();
		if (payment != null) {
			if (payment.getPaymentStatus().equals("Completed")) {
				return convertToOrderDTO(order);
			}
		}
		return null;
	}
}
