package com.sulav.dto;

public class UserDTO {

	private Long id;
	private String userName;
	private String email;
	private String role;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public UserDTO(Long id, String userName, String email, String role) {
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.role = role;
	}
	
	
	
	
	
	
}
