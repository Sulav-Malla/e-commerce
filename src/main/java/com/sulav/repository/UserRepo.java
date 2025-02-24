package com.sulav.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sulav.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String userName);
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
}
