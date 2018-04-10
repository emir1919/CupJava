/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.entities.Subcategory;
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
public class SubcategoryService {
    Connection conn = Connexion.getInstance().getConnection();   
    
    public List<Subcategory> getSubcategories(){
        String query = "select * from sub_category";
        List<Subcategory> subcategories = new ArrayList<Subcategory>();
        
        try {
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(query);
            
            while(rs.next()){
                subcategories.add(new Subcategory(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("image_name"),
                        rs.getDate("updated_at"),
                        rs.getInt("category_id")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return subcategories;
    }
    
    public void addSubCategory(Subcategory subcategory){
        String query = "insert into sub_category (name, image_name, updated_at, category_id) values (?,?,?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setString(1, subcategory.getName());
            ps.setString(2, subcategory.getImage_name());
            ps.setDate(3, (Date) subcategory.getUpdated_at());
            ps.setInt(4, subcategory.getCategory_id());
            ps.executeUpdate();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void editSubCategory(Subcategory subcategory){
        String query = "update sub_category set name=?, image_name=?, updated_at=?, category_id=? where id = ?";
        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setString(1, subcategory.getName());
            ps.setString(2, subcategory.getImage_name());
            ps.setDate(3, (Date) subcategory.getUpdated_at());
            ps.setInt(4, subcategory.getCategory_id());
            ps.setInt(5, subcategory.getId());
            ps.executeUpdate();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void deleteSubCategory(int id){
        String query = "delete from sub_category where id = ?";
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
