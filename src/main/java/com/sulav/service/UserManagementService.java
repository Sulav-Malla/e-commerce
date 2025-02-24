package com.sulav.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sulav.dto.UserDTO;
import com.sulav.entity.User;
import com.sulav.repository.UserRepo;

@Service
public class UserManagementService {

	@Autowired
	private UserRepo userRepository;

	// change user to userDTO
	private UserDTO convertToDTO(User user) {
		return new UserDTO(user.getUID(), user.getUsername(), user.getEmail(), user.getRole().getName().name());

	}

	// view all users
	public List<UserDTO> viewAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	// registers user
	public UserDTO createUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email is already in use");
		}
		User newUser = userRepository.save(user);
		return convertToDTO(newUser);
	}

	// login user
	public UserDTO findUser(String username, String password) {
		User foundUser = userRepository.findByUsername(username).filter(user -> user.getPassword().equals(password))
				.orElseThrow(() -> new RuntimeException("Invalid User!"));
		return convertToDTO(foundUser);
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

	// update user profile
	public UserDTO updateProfile(Long userId, User user) {
		User usr = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Invalid User!"));
		if (user.getFirstName() != null) {
			usr.setFirstName(user.getFirstName());

		}
		if (user.getLastName() != null) {
			usr.setLastName(user.getLastName());
		}

		if (user.getEmail() != null) {
			usr.setEmail(user.getEmail());

		}
		if (user.getUsername() != null) {
			usr.setUsername(user.getUsername());
		}
		if (user.getPassword() != null) {
			usr.setPassword(user.getPassword());
		}

		return convertToDTO(userRepository.save(usr));

	}

}
