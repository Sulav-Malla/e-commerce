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
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	private String productName;
	
	private String pDescription;
	
	private Double price;
	
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
    @JoinColumn(name = "seller_id")
	@JsonBackReference
    private User seller;  
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	@JsonManagedReference
    private List<Review> reviews;

    @OneToMany(mappedBy = "product")
	@JsonBackReference
    private List<OrderItem> orders;

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private List<CartItem> carts;
    
    private String productImgPath;
}
