package com.sulav.dto;

import java.util.List;

public class CartDTO {

	private Long cartId;
	private Double totalPrice;
	private List<CartItemDTO> cartItems;
	
	public Long getCartId() {
		return cartId;
	}
	public CartDTO(Long cartId, Double totalPrice, List<CartItemDTO> cartItems) {
		this.cartId = cartId;
		this.totalPrice = totalPrice;
		this.cartItems = cartItems;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<CartItemDTO> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItemDTO> cartItems) {
		this.cartItems = cartItems;
	}
	
	
}
