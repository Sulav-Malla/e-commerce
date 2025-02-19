package com.sulav.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sulav.entity.User;
import com.sulav.repository.UserRepo;

@Service
public class UserManagementService {

	@Autowired
	private UserRepo userRepository;

	// view all users
	public List<User> viewAllUsers() {
		return userRepository.findAll();
	}
	
	// registers user
	public User createUser(User user) {
		return userRepository.save(user);
	}

	// login user
	public User findUser(String username, String password) {
		return userRepository.findByUsername(username).filter(user -> user.getPassword().equals(password))
				.orElseThrow(() -> new RuntimeException("Invalid User!"));
	}

	
	// forgot password
	public String findPassword(String email) {
		return userRepository.findByEmail(email).map(User::getPassword)
				.orElseThrow(() -> new RuntimeException("Invalid email!"));
	}
	
	// resets password
	public String resetPassword(String email, String newPass) {
		User usr = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid User!"));
		usr.setPassword(newPass);
		userRepository.save(usr);
		return "Reset Successful";
	}

}
