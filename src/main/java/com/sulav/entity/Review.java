package com.sulav.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "reviews")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;

	private String comment;

	private int rating; // out of 5

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = true)
    @JsonBackReference
	private User user;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = true)
    @JsonBackReference
	private Product product;
}
