package com.sulav.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sulav.entity.Product;
import com.sulav.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/view/all")
	public ResponseEntity<List<Product>> viewAllProducts(){
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@GetMapping("/view/{id}")
	public ResponseEntity<Product> viewProductById(@PathVariable Long id){
		return ResponseEntity.ok(productService.getProductById(id));
	}
	
	@GetMapping("/view/category/{id}")
	public ResponseEntity<List<Product>> viewProductsInCategory(@PathVariable Long id){
		return ResponseEntity.ok(productService.getProductInCategory(id));
	}
}
