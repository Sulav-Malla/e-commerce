package com.sulav.entity;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="carts")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;
	
	@OneToOne
	@JoinColumn(name="user_id", nullable = false)
	private UserProfile user;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	List<CartItem> cartItems;
	
	private Double totalPrice = 0.0;
	
	public Double getTotalPrice() {
	    return totalPrice != null ? totalPrice : 0.0;
	}
}

