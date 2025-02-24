package com.sulav.dto;

public class OrderItemDTO {

	private Long orderItemId;
	private String productName;
	private int quantity;
	private Double price;

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getProduct() {
		return productName;
	}

	public void setProduct(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}
	

	public void setPrice(Double price) {
		this.price = price;
	}

	public OrderItemDTO(Long orderItemId, String productName, int quantity, Double price) {
		this.orderItemId = orderItemId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}
	
	
}
