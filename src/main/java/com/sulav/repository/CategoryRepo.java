package com.sulav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sulav.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

}
