package com.sulav.dto;

import java.util.List;

public class CategoryDTO {

	private Long categoryId;
	private String categoryName;
	private List<ProductDTO> products;
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<ProductDTO> getProducts() {
		return products;
	}
	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
	public CategoryDTO(Long categoryId, String categoryName, List<ProductDTO> products) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.products = products;
	}
	
	
	
}
