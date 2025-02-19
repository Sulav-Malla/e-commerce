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

import com.sulav.entity.User;
import com.sulav.service.UserManagementService;

@RestController
@RequestMapping("/user")
public class UserManagementController {

	@Autowired
	private UserManagementService userService;
	
	@GetMapping("/view")
	public ResponseEntity<List<User>> viewAllUsers(){
		return ResponseEntity.ok(userService.viewAllUsers());
	}
	// registration for new user
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user){
		return ResponseEntity.ok(userService.createUser(user));
	}
	
	// login existing user
	@GetMapping("/login")
	public ResponseEntity<User> loginUser(@RequestParam String username, @RequestParam String password){
		return ResponseEntity.ok(userService.findUser(username, password));
	}
	
	// help user get back password
	@GetMapping("/forgot-pass")
	public ResponseEntity<String> forgotPassword(@RequestParam String email){
		return ResponseEntity.ok(userService.findPassword(email));
	}
	
	@PutMapping("/reset-pass/{email}")
	public ResponseEntity<String> resetPassword(@PathVariable String email, @RequestParam String newpass){
		return ResponseEntity.ok(userService.resetPassword(email, newpass));
	}
}
