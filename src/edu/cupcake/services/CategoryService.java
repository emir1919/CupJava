/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.entities.Category;
import edu.cupcake.utils.Connexion;
import java.sql.Connection;
import java.sql.Date;
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
public class CategoryService {
 Connection conn = Connexion.getInstance().getConnection();    //Connection conn = myConn.GetConnection();
    
    public List<Category> getCategories(){
        String query = "select * from category";
        List<Category> categories = new ArrayList<Category>();
        
        try {
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(query);
            
            while(rs.next()){
                categories.add(new Category(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("image_name"),
                        rs.getDate("updated_at")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return categories;
    }
    
    public void addCategory(Category category){
        String query = "insert into category (name, image_name, updated_at) values (?,?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setString(1, category.getName());
            ps.setString(2, category.getImage_name());
            ps.setDate(3, (Date) category.getUpdated_at());
            ps.executeUpdate();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void editCategory(Category category){
        String query = "update category set name=?, image_name=?, updated_at=? where id = ?";
        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setString(1, category.getName());
            ps.setString(2, category.getImage_name());
            ps.setDate(3, (Date) category.getUpdated_at());
            ps.setInt(4, category.getId());
            ps.executeUpdate();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void deleteCategory(int id){
        String query = "delete from category where id = ?";
        try {
            com.mysql.jdbc.PreparedStatement ps = (com.mysql.jdbc.PreparedStatement) conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
