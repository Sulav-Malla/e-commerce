package com.sulav.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sulav.dto.CartDTO;
import com.sulav.dto.CartItemDTO;
import com.sulav.dto.ReviewDTO;
import com.sulav.entity.Cart;
import com.sulav.entity.CartItem;
import com.sulav.entity.Order;
import com.sulav.entity.OrderItem;
import com.sulav.entity.Product;
import com.sulav.entity.Review;
import com.sulav.entity.UserProfile;
import com.sulav.model.ReviewRequest;
import com.sulav.repository.CartItemRepo;
import com.sulav.repository.CartRepo;
import com.sulav.repository.OrderRepo;
import com.sulav.repository.ProductRepo;
import com.sulav.repository.ReviewRepo;
import com.sulav.repository.UserRepo;

@Service
public class CartService {

	private final CartRepo cartRepository;
	private final CartItemRepo cartItemRepository;
	private final UserRepo userRepository;
	private final ProductRepo productRepository;
	private final OrderRepo orderRepository;
	private final ReviewRepo reviewRepository;

	public CartService(CartRepo cartRepository, CartItemRepo cartItemRepository, UserRepo userRepository,
			ProductRepo productRepository, OrderRepo orderRepository, ReviewRepo reviewRepository) {
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
		this.reviewRepository = reviewRepository;
	}

	private CartDTO convertToCartDTO(Cart cart) {
		List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream().map(this::convertToCartItemDTO)
				.collect(Collectors.toList());

		return new CartDTO(cart.getCartId(), cart.getTotalPrice(), cartItemDTOs);
	}

	private CartItemDTO convertToCartItemDTO(CartItem item) {
		return new CartItemDTO(item.getCartItemId(), item.getProduct().getProductName(), item.getQuantity(),
				item.getPrice()

		);

	}

	private ReviewDTO convertToReviewDTO(Review review) {
		return new ReviewDTO(review.getReviewId(), review.getComment(), review.getRating(),
				review.getUser().getUsername(), review.getProduct().getProductName());
	}

	// get all carts (all users)
	public List<CartDTO> getAllCarts() {
		List<Cart> carts = cartRepository.findAll();
		List<CartDTO> cartDetails = carts.stream().map(this::convertToCartDTO).collect(Collectors.toList());
		return cartDetails;
	}

	// get the cart for specific user
	public CartDTO getCartByUserId(Long id) {
		UserProfile user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
		Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found!"));
		return convertToCartDTO(cart);
	}

	// open new cart
	public Cart createCartForUser(Long userId) {
		UserProfile user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));

		Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
			Cart newCart = new Cart();
			newCart.setUser(user);
			newCart.setCartItems(new ArrayList<>());
			newCart.setTotalPrice(0.0);
			return cartRepository.save(newCart);

		});

		return cart;
	}

	// add products to cart
	public CartDTO addProductsToCart(Long userId, Long productId, int quantity) {
		Cart cart = createCartForUser(userId);
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found!"));
		if (product.getQuantity() < quantity) {
			throw new RuntimeException("Quantity more than what's available!");
		}
		product.setQuantity(product.getQuantity() - quantity);
	    productRepository.save(product);
		CartItem existingItem = cart.getCartItems().stream()
				.filter(item -> item.getProduct().getProductId().equals(productId)).findFirst().orElse(null);
		if (existingItem != null) {
			existingItem.setQuantity(existingItem.getQuantity() + quantity);
		} else {
			existingItem = new CartItem();
			existingItem.setCart(cart);
			existingItem.setProduct(product);
			existingItem.setQuantity(quantity);
			existingItem.setPrice(product.getPrice());
		}
		cart.getCartItems().add(existingItem);

		Double currentPrice = cart.getTotalPrice();
		cart.setTotalPrice(currentPrice + (existingItem.getPrice() * existingItem.getQuantity()));

		return convertToCartDTO(cartRepository.save(cart));

	}

	// delete specific quantity from cart
	public String deleteProductFromCart(Long cartId, Long itemId, int quantity) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found!"));
		CartItem item = cartItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found!"));
		int currentQuantity = item.getQuantity();
		Product product = item.getProduct();
		if (quantity > currentQuantity) {
			return "Cannot delete more than what's available";
		} else if (quantity == currentQuantity) {
			cart.getCartItems().remove(item);
			cartItemRepository.deleteById(itemId);

		} else {
			item.setQuantity(currentQuantity - quantity);
			cartItemRepository.save(item);

		}
		product.setQuantity(product.getQuantity() + quantity);
	    productRepository.save(product);
		Double newTotal = cart.getCartItems().stream()
				.mapToDouble(currentItem -> currentItem.getPrice() * currentItem.getQuantity()).sum();

		cart.setTotalPrice(newTotal);
		cartRepository.save(cart);
		return "Successfully deleted item(s) from cart!";
	}

	// delete the entire cart
	public String removeCart(Long cartId) {
		cartRepository.deleteById(cartId);
		return ("Cart has been deleted");
	}

	// lets user write review for a product
	public ReviewDTO writeReviewForProduct(Long userId, Long productId, ReviewRequest reviewRequestDTO) {
	    UserProfile user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
	    Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found!"));

	    
	    
	    Review review = new Review();
	    review.setComment(reviewRequestDTO.getComment());
	    review.setRating(reviewRequestDTO.getRating());
	    review.setUser(user);
	    review.setProduct(product);

	   
	    // update review list
	    user.getReviews().add(review);
	    product.getReviews().add(review);
	    
	    reviewRepository.save(review);

	   

	    return convertToReviewDTO(review);
	}


	// allows user to checkout cart (makes new open order)
	public String checkoutProcess(Long userId, Long cartId) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found!"));
		UserProfile user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));

		if (cart.getCartItems().isEmpty()) {
	        throw new RuntimeException("Cart is empty! Cannot proceed with checkout.");
	    }
		
		Order order = new Order();
		order.setUser(user);
		order.setTotalAmount(cart.getTotalPrice());
		order.setStatus("Open");

		List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> mapCartToOrder(cartItem, order))
				.collect(Collectors.toList());
		order.setOrderItems(orderItems);
		orderRepository.save(order);
	    
	    cart.getCartItems().clear();  
	    cartRepository.save(cart);
	    
		return "Order created with id: " + order.getOrderId();
	}

	public OrderItem mapCartToOrder(CartItem cartItem, Order order) {
		OrderItem orderItem = new OrderItem();
		orderItem.setProduct(cartItem.getProduct());
		orderItem.setPrice(cartItem.getPrice());
		orderItem.setQuantity(cartItem.getQuantity());
		orderItem.setOrder(order);
		
		return orderItem;
	}

}
