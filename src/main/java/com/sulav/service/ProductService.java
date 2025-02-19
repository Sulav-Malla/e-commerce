package com.sulav.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sulav.entity.Category;
import com.sulav.entity.Product;
import com.sulav.entity.Review;
import com.sulav.repository.CategoryRepo;
import com.sulav.repository.ProductRepo;
import com.sulav.repository.ReviewRepo;

@Service
public class ProductService {

	
	private final ProductRepo productRepository;
	private final CategoryRepo categoryRepository;
	private final ReviewRepo reviewRepository;
	public ProductService(ProductRepo productRepository, CategoryRepo categoryRepository, ReviewRepo reviewRepository) {
		this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.reviewRepository = reviewRepository;
	}
	

	// view all products
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	
	// view specific product
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found!"));
	}
	
	// view all products in particular category
	public List<Product> getProductInCategory(Long categoryId){
		
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("Category not found!"));
		return category.getProducts();
	}
	
	
	
	
}
