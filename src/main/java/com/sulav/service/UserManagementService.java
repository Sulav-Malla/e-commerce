package com.sulav.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sulav.dto.UserDTO;
import com.sulav.entity.UserProfile;
import com.sulav.model.RegisterRequest;
import com.sulav.repository.UserRepo;

@Service
public class UserManagementService {

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private PasswordEncoder pwdEncoder;
	

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
	public UserDTO createUser(RegisterRequest registerDto) {

		if (userRepository.existsByEmail(registerDto.getEmail())) {
	        throw new IllegalArgumentException("Email is already in use");
	    }

	   
	    UserProfile user = new UserProfile();
	    user.setFirstName(registerDto.getFirstName());
	    user.setLastName(registerDto.getLastName());
	    user.setUsername(registerDto.getUsername());
	    user.setEmail(registerDto.getEmail());

	    // Encode password
	    user.setPassword(pwdEncoder.encode(registerDto.getPassword()));

	    
	    if ("admin@123".equals(registerDto.getRoleCode())) {
	        user.setRole(UserProfile.RoleName.ADMIN);
	    } else if ("seller@123".equals(registerDto.getRoleCode())) {
	        user.setRole(UserProfile.RoleName.SELLER);
	    } else {
	        user.setRole(UserProfile.RoleName.CUSTOMER); 
	    }

	   
	    UserProfile newUser = userRepository.save(user);

	    
	    return convertToDTO(newUser);
	}
	
	
	// checks if user exists (login)
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
	public UserDTO updateProfile(Long userId, RegisterRequest registerDto) {

		UserProfile usr = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("Invalid User!"));

	    if (registerDto.getFirstName() != null) {
	        usr.setFirstName(registerDto.getFirstName());
	    }
	    if (registerDto.getLastName() != null) {
	        usr.setLastName(registerDto.getLastName());
	    }
	    if (registerDto.getEmail() != null) {
	        usr.setEmail(registerDto.getEmail());
	    }
	    if (registerDto.getUsername() != null) {
	        usr.setUsername(registerDto.getUsername());
	    }
	    if (registerDto.getPassword() != null && !registerDto.getPassword().isEmpty()) {
	        usr.setPassword(pwdEncoder.encode(registerDto.getPassword())); // Encrypt password
	    }

	    // Save and return updated user
	    return convertToDTO(userRepository.save(usr));

	}

}
