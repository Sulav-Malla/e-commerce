package com.sulav.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sulav.dto.CategoryDTO;
import com.sulav.dto.ProductDTO;
import com.sulav.entity.Category;
import com.sulav.entity.Product;
import com.sulav.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/view/all")
	public ResponseEntity<List<ProductDTO>> viewAllProducts(){
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@GetMapping("/view/{id}")
	public ResponseEntity<ProductDTO> viewProductById(@PathVariable Long id){
		return ResponseEntity.ok(productService.getProductById(id));
	}
	
	@GetMapping("/view/{name}")
	public ResponseEntity<ProductDTO> searchProductName(@PathVariable String name){
		return ResponseEntity.ok(productService.getProductByName(name));
	}
	
	@GetMapping("/view/category/{id}")
	public ResponseEntity<CategoryDTO> viewProductsInCategory(@PathVariable Long id){
		return ResponseEntity.ok(productService.getProductInCategory(id));
	}
	
	@PostMapping("/add-product")
	public ResponseEntity<ProductDTO> addNewProduct(@RequestBody Product product){
		return ResponseEntity.ok(productService.createProduct(product));
	}
	
	@PostMapping("/add-category")
	public ResponseEntity<CategoryDTO> addNewCategory(@RequestBody Category category){
		return ResponseEntity.ok(productService.createCategory(category));
	}
	
	@PostMapping("/{productId}/upload-img")
	public ResponseEntity<String> uploadProductImage(@PathVariable Long productId, @RequestParam("file")  MultipartFile file){
		return ResponseEntity.ok(productService.uploadProductImg(productId, file));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Product product){
		return ResponseEntity.ok(productService.updateProduct(id, product));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		return ResponseEntity.ok(productService.deleteProduct(id));
	}
	
}
