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
import com.sulav.entity.Review;
import com.sulav.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@GetMapping("/view/all")
	public ResponseEntity<List<CartDTO>> viewAllCarts(){
		return ResponseEntity.ok(cartService.getAllCarts());
	}
	
	@GetMapping("/view/{id}")
	public ResponseEntity<CartDTO> viewUserCart(@PathVariable Long id){
		return ResponseEntity.ok(cartService.getCartByUserId(id));
	}
	
	
	@PutMapping("/add/{userId}/{productId}")
	public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long userId, @PathVariable Long productId, @RequestParam int quantity){
		return ResponseEntity.ok(cartService.addProductsToCart(userId, productId, quantity));
	}
	
	@DeleteMapping("/delete/{cartId}/{itemId}")
	public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam int quantity){
		return ResponseEntity.ok(cartService.deleteProductFromCart(cartId, itemId, quantity));
	}
	
	@PostMapping("/review/{userId}/{productId}")
	public ResponseEntity<ReviewDTO> reviewProduct(@PathVariable Long userId, @PathVariable Long productId, @RequestBody Review review){
		return ResponseEntity.ok(cartService.writeReviewForProduct(userId, productId, review));
	}
	
	@PostMapping("/checkout/{userId}/{cartId}")
	public ResponseEntity<String> cartCheckout(@PathVariable Long userId, @PathVariable Long cartId){
		return ResponseEntity.ok(cartService.checkoutProcess(userId, cartId));
	}
}
