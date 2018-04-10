/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.entities.Favorite;
import edu.cupcake.entities.Users;
import edu.cupcake.utils.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yassi
 */
public class FavoriteService {
    Connection conn = Connexion.getInstance().getConnection();   
    
    public List<Favorite> getFavorites(){
        String query = "select * from favorite";
        List<Favorite> favorites = new ArrayList<Favorite>();
        
        try {
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(query);
            
            while(rs.next()){
                favorites.add(new Favorite(
                        rs.getInt("user_id"),
                        rs.getInt("subcategory_id")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return favorites;
    }
    
    public List<Favorite> getCurrentFavorites(int id){
        String query = "select * from favorite where user_id = ?";
        List<Favorite> favorites = new ArrayList<Favorite>();
        
        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                favorites.add(new Favorite(
                        rs.getInt("user_id"),
                        rs.getInt("subcategory_id")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return favorites;
    }
    
    public void addFavorite(Favorite favorite){
        String query = "insert into favorite (user_id,subcategory_id) values (?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setInt(1, favorite.getUser_id());
            ps.setInt(2, favorite.getSubcategory_id());
            ps.executeUpdate();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void editFavorite(Favorite favorite){
        
    }
    
    public void deleteFavorite(int user_id, int subcategory_id){
        String query = "delete from favorite where user_id = ? and subcategory_id = ?";
        try {
            com.mysql.jdbc.PreparedStatement ps = (com.mysql.jdbc.PreparedStatement) conn.prepareStatement(query);
            ps.setInt(1, user_id);
            ps.setInt(2, subcategory_id);
            ps.executeUpdate();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public ArrayList<String> getUsersByFav(int id){
        String query = "select u.PhoneNumber from users u JOIN favorite f "
                + "on f.user_id = u.id JOIN sub_category s on s.id = f.SubCategory_id "
                + "where f.SubCategory_id = ?";
        
        ArrayList<String> numbers = new ArrayList<>();
        
        try {
            com.mysql.jdbc.PreparedStatement ps = (com.mysql.jdbc.PreparedStatement) conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                numbers.add(rs.getString("PhoneNumber"));
            }
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return numbers;
    }
}
