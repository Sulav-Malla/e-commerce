package com.sulav.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sulav.entity.Review;

public interface ReviewRepo extends JpaRepository<Review, Long> {

	
}
