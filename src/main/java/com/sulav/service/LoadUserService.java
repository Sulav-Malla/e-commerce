//package com.sulav.service;
//
//import java.util.Collections;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.sulav.entity.UserProfile;
//import com.sulav.repository.UserRepo;
//
//@Service
//public class LoadUserService implements UserDetailsService {
//
//	@Autowired
//	private UserRepo userRepository;
//	
//	// login user
////		public boolean authenticateUser(String email, String password) {
//////			UserProfile foundUser = userRepository.findByUsername(username).filter(user -> user.getPassword().equals(password))
//////					.orElseThrow(() -> new RuntimeException("Invalid User!"));
////			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
////
////			try {
////				Authentication authentication = authManager.authenticate(token);
////				return authentication.isAuthenticated();
////			} catch (Exception e) {
////				return false;
////			}
////
////		}
//	
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		UserProfile usr = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid User!"));
//		return new User(usr.getEmail(), usr.getPassword(),
//				Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usr.getRole().name())));
//	}
//}
