/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

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
public class OrdersService {

    Connection con = Connexion.getInstance().getConnection();
    private Statement stmt;

    public OrdersService() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public Orders getOrderbyId(int id) throws SQLException {
        Orders a = null;
        String req = "SELECT * FROM `orders` WHERE id = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            a = new Orders();

            a.setId(rs.getInt("id"));
            a.setAmount(rs.getFloat("amount"));
            a.setDate((Date) rs.getDate("date"));
            a.setAdresse_id(rs.getInt("adresse_id"));
            a.setReference(rs.getString("reference"));
            a.setPaymentState(rs.getString("PaymentState"));
            a.setUtilisateur_id(rs.getInt("utilisateur_id"));

            System.out.println("Commande trouvé !");
        }
        return a;
    }

    public List<Orders> getOrdersbyUserId(long id) throws SQLException {
        List<Orders> myList = new ArrayList<Orders>();
        try {

            String requete2 = "SELECT * from orders where utilisateur_id= ?";

            PreparedStatement st2 = con.prepareStatement(requete2);
            st2.setLong(1, id);
            ResultSet rs = st2.executeQuery();
            AdressesService asr = new AdressesService();
            while (rs.next()) {
                Orders a = new Orders();

                a.setId(rs.getInt("id"));
                a.setAmount(rs.getFloat("amount"));
                a.setDate((Date) rs.getDate("date"));
                a.setAdresse_id(rs.getInt("adresse_id"));
                a.setReference(rs.getString("reference"));
                a.setPaymentState(rs.getString("PaymentState"));
                a.setUtilisateur_id(rs.getInt("utilisateur_id"));
                a.setAdresse(a.getAdresseByOrder( (int) id));
                
                if ("notpaid".equals(a.getPaymentState()) || "".equals(a.getPaymentState())) {
            a.setPaymentState("Non Payée");
        }
        else {
                   a.setPaymentState("Payée");

        }

                myList.add(a);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AdressesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myList;
    }
    
    
}
