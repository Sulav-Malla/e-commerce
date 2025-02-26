package com.sulav.model;

import lombok.Data;

@Data
public class ProductRequest {

	private String productName;
    private String description;
    private Double price;
    private Integer quantity;
    private Long categoryId; 
    private Long sellerId;
    private String productImgPath = "";
}
