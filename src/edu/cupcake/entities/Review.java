/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.entities;

import java.sql.Date;

/**
 *
 * @author yassi
 */
public class Review {
    private int id;
    private String reviewText;
    private Date reviewedAt;
    private int rating;
    private int product_id;
    private int user_id;

    public Review(int id, String reviewText, Date reviewedAt, int rating, int product_id, int user_id) {
        this.id = id;
        this.reviewText = reviewText;
        this.reviewedAt = reviewedAt;
        this.rating = rating;
        this.product_id = product_id;
        this.user_id = user_id;
    }

    public Review(String reviewText, Date reviewedAt, int rating, int product_id, int user_id) {
        this.reviewText = reviewText;
        this.reviewedAt = reviewedAt;
        this.rating = rating;
        this.product_id = product_id;
        this.user_id = user_id;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(Date reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    
    
}
