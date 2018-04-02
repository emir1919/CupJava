/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.entities.Line_Order;
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
    
}
