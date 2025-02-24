package com.sulav.dto;

public class PaymentDTO {

	private Long paymentId;
	private Long orderId;
	private String paymentMethod;
	private String paymentStatus;
	
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public PaymentDTO(Long paymentId, Long orderId, String paymentMethod, String paymentStatus) {
		this.paymentId = paymentId;
		this.orderId = orderId;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
	}
	
	
}
