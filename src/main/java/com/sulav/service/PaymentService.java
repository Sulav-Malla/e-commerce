package com.sulav.service;

import org.springframework.stereotype.Service;

import com.sulav.dto.PaymentDTO;
import com.sulav.entity.Order;
import com.sulav.entity.Payment;
import com.sulav.repository.OrderRepo;
import com.sulav.repository.PaymentRepo;

@Service
public class PaymentService {

	private final PaymentRepo paymentRepository;
	private final OrderRepo orderRepository;

	public PaymentService(PaymentRepo paymentRepository, OrderRepo orderRepository) {
		this.paymentRepository = paymentRepository;
		this.orderRepository = orderRepository;
	}

	private PaymentDTO convertToPaymentDTO(Payment payment) {
		return new PaymentDTO(payment.getPaymentId(), payment.getOrder().getOrderId(), payment.getPaymentMethod(),
				payment.getPaymentStatus());
	}

	// pay for the order using card
	public PaymentDTO payByCard(Long orderId, Double amount) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
		Payment payment = new Payment();
		payment.setOrder(order);
		payment.setAmount(amount);
		payment.setPaymentMethod("Credit/Debit");
		payment.setPaymentStatus("Completed");
		return convertToPaymentDTO(paymentRepository.save(payment));
	}
}
