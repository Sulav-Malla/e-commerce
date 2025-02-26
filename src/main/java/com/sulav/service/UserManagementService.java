package com.sulav.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sulav.dto.UserDTO;
import com.sulav.entity.UserProfile;
import com.sulav.repository.UserRepo;

@Service
public class UserManagementService {

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private PasswordEncoder pwdEncoder;


	// authentication
	

	// change user to userDTO
	private UserDTO convertToDTO(UserProfile user) {
		return new UserDTO(user.getUID(), user.getUsername(), user.getEmail(), user.getRole().name());

	}

	// view all users
	public List<UserDTO> viewAllUsers() {
		List<UserProfile> users = userRepository.findAll();
		return users.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	// registers user
	public UserDTO createUser(UserProfile user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email is already in use");
		}
		String encodedPwd = pwdEncoder.encode(user.getPassword());
		user.setPassword(encodedPwd);
		UserProfile newUser = userRepository.save(user);
		return convertToDTO(newUser);
	}

	public UserDTO findUser(String email, String password) {
		return userRepository.findByEmail(email)
		        .filter(user -> pwdEncoder.matches(password, user.getPassword())) 
		        .map(this::convertToDTO)
		        .orElseThrow(() -> new RuntimeException("Invalid email or password!"));
	}

	// forgot password
	public String findPassword(String email) {
		return userRepository.findByEmail(email).map(UserProfile::getPassword)
				.orElseThrow(() -> new RuntimeException("Invalid email!"));
	}

	// resets password
	public String resetPassword(String email, String newPass) {
		UserProfile usr = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid User!"));
		usr.setPassword(pwdEncoder.encode(newPass));
		userRepository.save(usr);
		
		return "Reset Successful";
	}

	// update user profile
	public UserDTO updateProfile(Long userId, UserProfile user) {
		UserProfile usr = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Invalid User!"));
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
