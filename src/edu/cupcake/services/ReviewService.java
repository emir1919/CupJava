/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.entities.Review;
import edu.cupcake.utils.Connexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author yassi
 */
public class ReviewService {
    Connection conn = Connexion.getInstance().getConnection();   
    
    public List<Review> getReviews(){
        String query = "select * from review";
        List<Review> reviews = new ArrayList<Review>();
        
        try {
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(query);
            
            while(rs.next()){
                reviews.add(new Review(rs.getInt("id"),
                        rs.getString("reviewText"),
                        rs.getDate("reviewedAt"),
                        rs.getInt("rating"),
                        rs.getInt("product_id"),
                        rs.getInt("user_id")
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return reviews;
    }
    
    public List<Review> getProductReviews(int id){
        String query = "select * from review where Product_id = ?";
        List<Review> reviews = new ArrayList<Review>();
        
        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setInt(1,  id);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                reviews.add(new Review(rs.getInt("id"),
                        rs.getString("reviewText"),
                        rs.getDate("reviewedAt"),
                        rs.getInt("rating"),
                        rs.getInt("product_id"),
                        rs.getInt("user_id")
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return reviews;
    }
    
    public void addReview(Review review){
        String query = "insert into review (reviewText, reviewedAt, rating, product_id, user_id) values (?,?,?,?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setString(1, review.getReviewText());
            ps.setDate(2,(Date) review.getReviewedAt());
            ps.setInt(3,  review.getRating());
            ps.setInt(4, review.getProduct_id());
            ps.setInt(5, review.getUser_id());
            ps.executeUpdate();
            ProductService aux = new ProductService();
            aux.updateRatingProduct(calculateRating(review.getProduct_id()), review.getProduct_id());
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public double calculateRating(int id){
        
        List<Review> l = this.getProductReviews(id);
        double result = l.stream().collect(Collectors.averagingDouble(Review::getRating));
        return result;
    }
    
    public void editReview(Review review){
        String query = "update review set reviewText=?, reviewedAt=?, rating=?, product_id=?, user_id=? where id = ?";
        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setString(1, review.getReviewText());
            ps.setDate(2,(Date) review.getReviewedAt());
            ps.setInt(3,  review.getRating());
            ps.setInt(4, review.getProduct_id());
            ps.setInt(5, review.getUser_id());
            ps.setInt(6, review.getId());
            ps.executeUpdate();
            ProductService aux = new ProductService();
            aux.updateRatingProduct(calculateRating(review.getProduct_id()), review.getProduct_id());
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void deleteReview(int id){
        String query = "delete from review where id = ?";
        try {
            com.mysql.jdbc.PreparedStatement ps = (com.mysql.jdbc.PreparedStatement) conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            ProductService aux = new ProductService();
            aux.updateRatingProduct(calculateRating(id), id);
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
