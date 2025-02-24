package com.sulav.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	@OneToOne(mappedBy = "order")
	@JsonBackReference
    private Payment payment;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
    @JsonBackReference
	private User user;
	
	@OneToMany(mappedBy = "order", cascade= CascadeType.ALL)
	@JsonManagedReference
	List<OrderItem> orderItems;
	
	private Double totalAmount;
}
