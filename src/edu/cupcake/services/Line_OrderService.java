/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.entities.Line_Order;
import edu.cupcake.entities.ObservableLineOrders;
import edu.cupcake.entities.Orders;
import edu.cupcake.utils.Connexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author berrahal
 */
public class Line_OrderService {
    
     Connection con = Connexion.getInstance().getConnection();
    private Statement stmt;

    public Line_OrderService() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    
    
    public List<Line_Order> getLineOrdersbyOrderId(int id) throws SQLException {
        List<Line_Order> myList = new ArrayList<Line_Order>();
        try {

            String requete2 = "SELECT * from line_order where commande_id= ?";

            PreparedStatement st2 = con.prepareStatement(requete2);
            st2.setInt(1, id);
            ResultSet rs = st2.executeQuery();
            while (rs.next()) {
                Line_Order a = new Line_Order();

                a.setId(rs.getInt("id"));
                a.setAffected(rs.getString("affected"));
                a.setProduct_id(rs.getInt("product_id"));
                a.setQte(rs.getInt("qte"));
                a.setCommande_id(rs.getInt("commande_id"));
               

                myList.add(a);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AdressesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myList;
    }
    
    public void addLineORder(Line_Order line) throws SQLException {
        String req = "INSERT INTO line_order (qte,commande_id,product_id,affected) values(?,?,?,?)";
       
        PreparedStatement pre = con.prepareStatement(req);

        pre.setInt(1, line.getQte());
        pre.setInt(2, line.getCommande_id());
        pre.setInt(3, line.getProduct_id());
        pre.setString(4, line.getAffected());

     
     
        pre.executeUpdate();
        
        
    
     }  
    
    
    
    public List<ObservableLineOrders> getLineOrdersbyBakeryId(int id) throws SQLException {
        List<ObservableLineOrders> myList = new ArrayList<ObservableLineOrders>();
        try {

            //String requete2 = "SELECT line_order.id as lineid,stock.Bakery_id,orders.Date,product.Name,orders.PaymentState,orders.id,line_order.affected,line_order.qte,line_order.product_id,orders.amount,line_order.commande_id FROM line_order INNER JOIN stock ON stock.Product_id=line_order.product_id INNER JOIN product ON product.id=line_order.product_id INNER JOIN bakery ON bakery.id=stock.Bakery_id INNER JOIN orders ON orders.id=line_order.commande_id WHERE bakery.id= ?";
            String requete2 = "SELECT DISTINCT line_order.id as lineid,orders.Date,product.Name,orders.PaymentState,orders.id,line_order.affected,line_order.qte,line_order.product_id,orders.amount,line_order.commande_id FROM line_order INNER JOIN stock ON stock.Product_id=line_order.product_id INNER JOIN product ON product.id=line_order.product_id INNER JOIN bakery ON bakery.id=stock.Bakery_id INNER JOIN orders ON orders.id=line_order.commande_id WHERE product.Enseigne_id= ?";
            PreparedStatement st2 = con.prepareStatement(requete2);
            st2.setInt(1, 3);
            ResultSet rs = st2.executeQuery();
            while (rs.next()) {
                myList.add(new ObservableLineOrders(
                rs.getInt("lineid"),
                        rs.getDate("Date"), 
                        rs.getString("Name"), 
                        rs.getFloat("amount"), 
                        rs.getString("paymentstate"), 
                        rs.getInt("qte"), 
                        rs.getInt("commande_id"), 
                        rs.getInt("product_id"), 
                        rs.getString("affected")));
            }
            
            
            //myList = (List<ObservableLineOrders>) myList.stream().filter((ObservableLineOrders o) -> o.getBakeryId() == id);
        } catch (SQLException ex) {
            Logger.getLogger(AdressesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myList;
    }
    
    public void EditAffectedLineorder(Line_Order u) throws SQLException {
        
        
        String req = "UPDATE line_order SET affected = ? WHERE id = ?";
        PreparedStatement st = con.prepareStatement(req);
        
        st.setString(1,u.getAffected());
        st.setInt(2,u.getId());
 
        st.executeUpdate();

    }
    
    
}
