package com.sulav.dto;

import java.util.List;

public class OrderDTO {

	private Long orderId;
	private Double totalPrice;
	private String paymentMethod;
	private List<OrderItemDTO> orderItems;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}
	public OrderDTO(Long orderId, Double totalPrice, String paymentMethod, List<OrderItemDTO> orderItems) {
		this.orderId = orderId;
		this.totalPrice = totalPrice;
		this.paymentMethod = paymentMethod;
		this.orderItems = orderItems;
	}
	
	
	
	
	
}
