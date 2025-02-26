package com.sulav.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sulav.entity.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {

	
}
