package com.sulav.dto;

import java.util.List;

public class ProductDTO {

	private Long productId;
	private String name;
	private String description;
	private Double price;
	private List<ReviewDTO> reviews;
	private String productImgPath;
	private int quantity;
	private String sellerName;
	private String category;
	
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public List<ReviewDTO> getReviews() {
		return reviews;
	}
	public void setReviews(List<ReviewDTO> reviews) {
		this.reviews = reviews;
	}
	public String getProductImgPath() {
		return productImgPath;
	}
	public void setProductImgPath(String productImgPath) {
		this.productImgPath = productImgPath;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public ProductDTO(Long productId, String name, String description, Double price, List<ReviewDTO> reviews,
			String productImgPath, int quantity, String sellerName, String category) {
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.reviews = reviews;
		this.productImgPath = productImgPath;
		this.quantity = quantity;
		this.sellerName = sellerName;
		this.category = category;
	}
	
	
}
