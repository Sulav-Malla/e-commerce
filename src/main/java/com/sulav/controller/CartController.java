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

import com.sulav.dto.CartDTO;
import com.sulav.dto.ReviewDTO;
import com.sulav.model.ReviewRequest;
import com.sulav.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@GetMapping("/admin/view/all")
	public ResponseEntity<List<CartDTO>> viewAllCarts(){
		return ResponseEntity.ok(cartService.getAllCarts());
	}
	
	@GetMapping("/user/view/{id}")
	public ResponseEntity<CartDTO> viewUserCart(@PathVariable Long id){
		return ResponseEntity.ok(cartService.getCartByUserId(id));
	}
	
	
	@PutMapping("/user/add/{userId}/{productId}")
	public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long userId, @PathVariable Long productId, @RequestParam int quantity){
		return ResponseEntity.ok(cartService.addProductsToCart(userId, productId, quantity));
	}
	
	@DeleteMapping("/user/delete/{cartId}/{itemId}")
	public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam int quantity){
		return ResponseEntity.ok(cartService.deleteProductFromCart(cartId, itemId, quantity));
	}
	
	@PostMapping("/user/review/{userId}/{productId}")
	public ResponseEntity<ReviewDTO> reviewProduct(@PathVariable Long userId, @PathVariable Long productId, @RequestBody ReviewRequest review){
		return ResponseEntity.ok(cartService.writeReviewForProduct(userId, productId, review));
	}
	
	@PostMapping("/user/checkout/{userId}/{cartId}")
	public ResponseEntity<String> cartCheckout(@PathVariable Long userId, @PathVariable Long cartId){
		return ResponseEntity.ok(cartService.checkoutProcess(userId, cartId));
	}
}
