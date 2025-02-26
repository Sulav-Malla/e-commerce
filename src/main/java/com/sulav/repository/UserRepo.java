package com.sulav.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sulav.entity.UserProfile;

@Repository
public interface UserRepo extends JpaRepository<UserProfile, Long> {

	Optional<UserProfile> findByUsername(String userName);
	Optional<UserProfile> findByEmail(String email);
	boolean existsByEmail(String email);
}
