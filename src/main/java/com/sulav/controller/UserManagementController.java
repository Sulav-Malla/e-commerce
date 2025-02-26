package com.sulav.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sulav.dto.UserDTO;
import com.sulav.model.LoginRequest;
import com.sulav.model.RegisterRequest;
import com.sulav.service.UserManagementService;

@RestController
@RequestMapping("/user")
public class UserManagementController {

	@Autowired
	private UserManagementService userService;
	
	@GetMapping("/admin/view")
	public ResponseEntity<List<UserDTO>> viewAllUsers() {
		return ResponseEntity.ok(userService.viewAllUsers());
	}

	// registration for new user
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody RegisterRequest user) {

		return ResponseEntity.ok(userService.createUser(user));
	}

	// login existing user
	@PostMapping("/login")
	public ResponseEntity<UserDTO> loginUser(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(userService.findUser(loginRequest.getEmail(), loginRequest.getPassword()));
	}

	// help user get back password
	@GetMapping("/profile/forgot-pass")
	public ResponseEntity<String> forgotPassword(@RequestParam String email) {
		return ResponseEntity.ok(userService.findPassword(email));
	}

	@PutMapping("/profile/reset-pass")
	public ResponseEntity<String> resetPassword(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(userService.resetPassword(loginRequest.getEmail(), loginRequest.getPassword()));
	}

	@PutMapping("/profile/update/{userId}")
	public ResponseEntity<UserDTO> updateProfile(@PathVariable Long userId, @RequestBody RegisterRequest user) {
		return ResponseEntity.ok(userService.updateProfile(userId, user));
	}
}
