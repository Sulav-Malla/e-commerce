package com.sulav.dto;

public class ReviewDTO {

	private Long reviewId;
	private String comment;
	private int rating;
	private String username;
	private String productName;
	
	public Long getReviewId() {
		return reviewId;
	}
	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public ReviewDTO(Long reviewId, String comment, int rating, String username, String productName) {
		this.reviewId = reviewId;
		this.comment = comment;
		this.rating = rating;
		this.username = username;
		this.productName = productName;
	}
	
	
}
