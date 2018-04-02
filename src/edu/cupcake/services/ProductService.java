/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.entities.Product;
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
public class ProductService {
      Connection con = Connexion.getInstance().getConnection();
    private Statement stmt;

    public ProductService() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public List<Product> getProducts(){
        String query = "select * from product";
        List<Product> products = new ArrayList<Product>();
        
        try {
            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery(query);
            
            while(rs.next()){
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getDouble("rating"),
                        rs.getDouble("reduction"),
                        rs.getString("image_name"),
                        rs.getInt("sales"),
                        rs.getDate("updated_at"),
                        rs.getDate("added_at"),
                        rs.getInt("subcategory_id"),
                        rs.getInt("enseigne_id")
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return products;
    }
    
    public List<Product> getProductsByBrand(int id){
        List<Product> products = new ArrayList<Product>();
        
        try {
            //String query = "select * from product where `Enseigne_id`=?;";
            String query = "select * from product where `Enseigne_id`="+id;
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            //ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(query);
            
            while(rs.next()){
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getDouble("rating"),
                        rs.getDouble("reduction"),
                        rs.getString("image_name"),
                        rs.getInt("sales"),
                        rs.getDate("updated_at"),
                        rs.getDate("added_at"),
                        rs.getInt("subcategory_id"),
                        rs.getInt("enseigne_id")
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return products;
    }
    
    public List<Product> getProductsNotStocked(int id){
        
        List<Product> products = new ArrayList<Product>();
        
        try {
            String query = "select * from product where `Enseigne_id`="+id+" and id not in (select product_id from stock where `Enseigne_id`="+id+")";
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery(query);
            
            while(rs.next()){
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getDouble("rating"),
                        rs.getDouble("reduction"),
                        rs.getString("image_name"),
                        rs.getInt("sales"),
                        rs.getDate("updated_at"),
                        rs.getDate("added_at"),
                        rs.getInt("subcategory_id"),
                        rs.getInt("enseigne_id")
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return products;
    }
    
    public Product getProductById(int id){
        
        
        Product p = new Product();
        
        try {
            String query = "SELECT * FROM product where id = ?";
             PreparedStatement st2 = con.prepareStatement(query);
            st2.setInt(1, id);
            ResultSet rs = st2.executeQuery();
            
            while(rs.next()){
                p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getDouble("rating"),
                        rs.getDouble("reduction"),
                        rs.getString("image_name"),
                        rs.getInt("sales"),
                        rs.getDate("updated_at"),
                        rs.getDate("added_at"),
                        rs.getInt("subcategory_id"),
                        rs.getInt("enseigne_id")
                );
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return p;
    }
    
    public void addProduct(Product product){
        String query = "insert into product (name,"
                + "price,description,rating,reduction,"
                + "image_name,sales, updated_at,added_at,"
                + "subcategory_id,enseigne_id) values (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getRating());
            ps.setDouble(5, product.getReduction());
            ps.setString(6, product.getImage_name());
            ps.setInt(7, product.getSales());
            ps.setDate(8, product.getUpdated_at());
            ps.setDate(9, (Date) product.getAdded_at());
            ps.setInt(10, product.getSubcategory_id());
            ps.setInt(11, product.getEnseigne_id());
            ps.executeUpdate();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void editProduct(Product product){
        String query = "update product set name=?, price=?,description=?,rating=?,"
                + "reduction=?,image_name=?,sales=?, updated_at=?,"
                + "added_at=?,subcategory_id=?,enseigne_id=? where id = ?";
        try {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getRating());
            ps.setDouble(5, product.getReduction());
            ps.setString(6, product.getImage_name());
            ps.setInt(7, product.getSales());
            ps.setDate(8, product.getUpdated_at());
            ps.setDate(9, (Date) product.getAdded_at());
            ps.setInt(10, product.getSubcategory_id());
            ps.setInt(11, product.getEnseigne_id());
            ps.setInt(12, product.getId());
            ps.executeUpdate();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void deleteProduct(int id){
        String query = "delete from product where id = ?";
        try {
            com.mysql.jdbc.PreparedStatement ps = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
