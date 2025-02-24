package com.sulav.dto;

public class CartItemDTO {

	private Long cartItemId;
	private String productName;
    private int quantity;
    private Double price;
    
	public Long getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
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
	
	public CartItemDTO(Long cartItemId, String productName, int quantity, Double price) {
		this.cartItemId = cartItemId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}
    
	
}
