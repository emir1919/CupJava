/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.entities.ObservableStock;
import edu.cupcake.entities.Stock;
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
public class StockService {
    Connection conn = Connexion.getInstance().getConnection();   
    
    public List<Stock> getStocks(){
        String query = "select * from stock";
        List<Stock> stocks = new ArrayList<Stock>();
        
        try {
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(query);
            
            while(rs.next()){
                stocks.add(new Stock(
                        rs.getInt("qte"),
                        rs.getInt("product_id"),
                        rs.getInt("bakery_id"),
                        rs.getInt("sales")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return stocks;
    }
    
    public List<ObservableStock> getObsStocks(int id){
        String query = "SELECT s.qte, s.Product_id, s.Bakery_id, s.sales, p.Name FROM stock s JOIN product p on p.id = s.Product_id where s.Bakery_id = ?";
        List<ObservableStock> stocks = new ArrayList<ObservableStock>();
        
        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                stocks.add(new ObservableStock(
                        rs.getInt("qte"),
                        rs.getInt("Product_id"),
                        rs.getInt("Bakery_id"),
                        rs.getInt("sales"),
                        rs.getString("Name")
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
        
        return stocks;
    }
    
    public void addStock(Stock stock){
        String query = "insert into stock (qte, product_id, bakery_id, sales) values (?,?,?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setInt(1, stock.getQte());
            ps.setInt(2, stock.getProduct_id());
            ps.setInt(3, stock.getBakery_id());
            ps.setInt(4, stock.getSales());
            ps.executeUpdate();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void editStock(Stock stock){
        String query = "update stock set qte=?, sales=? where product_id = ? and bakery_id=?";
        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setInt(1, stock.getQte());
            ps.setInt(2, stock.getSales());
            ps.setInt(3, stock.getProduct_id());
            ps.setInt(4, stock.getBakery_id());
            ps.executeUpdate();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void deleteStock(int product_id, int bakery_id){
        String query = "delete from stock where product_id = ? and bakery_id = ?";
        try {
            com.mysql.jdbc.PreparedStatement ps = (com.mysql.jdbc.PreparedStatement) conn.prepareStatement(query);
            ps.setInt(1, product_id);
            ps.setInt(2, bakery_id);
            ps.executeUpdate();
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
