package com.sulav.entity;

import java.util.ArrayList;
import java.util.List;



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
	
	private String description;
	
	private Double price;
	
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
    @JoinColumn(name = "seller_id")
    private UserProfile seller;  
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orders = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<CartItem> carts = new ArrayList<>();
    
    private String productImgPath;
}
