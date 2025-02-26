package com.sulav.model;

import lombok.Data;

@Data
public class ReviewRequest {


    private String comment;

    private int rating; // Rating between 1 and 5
}
