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
		List<OrderItemDTO> orderItems = order.getOrderItems().stream().map(this::convertToOrderItemDTO)
				.collect(Collectors.toList());
		String paymentMethod = (order.getPayment() != null) ? order.getPayment().getPaymentMethod() : "Not Paid";
		return new OrderDTO(order.getOrderId(), order.getTotalAmount(), paymentMethod, orderItems, order.getStatus());

	}

	private OrderItemDTO convertToOrderItemDTO(OrderItem item) {
		return new OrderItemDTO(item.getOrderItemId(), item.getProduct().getProductName(), item.getQuantity(),
				item.getPrice());

	}

	// get all orders for a user
	public List<OrderDTO> getOrderHistory(Long userId) {
		List<Order> orders = orderRepository.findByUser_uID(userId)
				.orElseThrow(() -> new RuntimeException("User not found!"));
		List<OrderDTO> orderHistory = orders.stream().filter(order -> order.getStatus().equals("Finished"))
				.map(this::convertToOrderDTO).collect(Collectors.toList());
		return orderHistory;
	}

	// track open orders
	public List<OrderDTO> getOpenOrders(Long userId) {
		List<Order> orders = orderRepository.findByUser_uID(userId)
				.orElseThrow(() -> new RuntimeException("User not found!"));
		List<OrderDTO> orderHistory = orders.stream().filter(order -> order.getStatus().equals("Open"))
				.map(this::convertToOrderDTO).collect(Collectors.toList());
		return orderHistory;
	}

	// order confirmation
	public OrderDTO getConfirmation(Long userId, Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found!"));
		
		if (!order.getUser().getUID().equals(userId)) {
	        throw new RuntimeException("Unauthorized access! This order does not belong to the user.");
	    }
		Payment payment = order.getPayment();
		if (payment == null) {
			throw new RuntimeException("Payment not found for this order! Please complete the payment.");
		}

		if (payment.getPaymentStatus().equals("Completed")) {
			order.setStatus("Finished");
			orderRepository.save(order);
			return convertToOrderDTO(order);
		}

		throw new RuntimeException("Payment is still pending!");
	}
}
