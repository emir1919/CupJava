/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.entities.Adresses;
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

    public List<Orders> getOrdersbyUserId(int id) throws SQLException {
        List<Orders> myList = new ArrayList<Orders>();
        try {

            String requete2 = "SELECT * from orders where utilisateur_id= ?";

            PreparedStatement st2 = con.prepareStatement(requete2);
            st2.setInt(1, id);
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
              
           AdressesService sr = new AdressesService();
          Adresses adresse = new Adresses();
          adresse=sr.getAdressebyOrder(a.getId());
                a.setAdresse(adresse.getAdresse());

// a.setAdresse(a.getAdresseByOrder(a.getId()));
                //System.out.println(a.getAdresseByOrder(110));
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
    
     public int addOrder(Orders order) throws SQLException {
        String req = "INSERT INTO orders (date,adresse_id,utilisateur_id,reference,paymentstate,amount) values(?,?,?,?,?,?)";
       
                String columnNames[] = new String[] { "id" };
        PreparedStatement pre = con.prepareStatement(req,columnNames);
        //   pre.setLong(1, user.getId());

        pre.setDate(1, order.getDate());
        pre.setInt(2, order.getAdresse_id());
        pre.setInt(3, order.getUtilisateur_id());
        pre.setString(4, order.getReference());

        pre.setString(5, order.getPaymentState());
        pre.setFloat(6, order.getAmount());
     
        int primkey=0;
        
         if (pre.executeUpdate() > 0) {
            // Retrieves any auto-generated keys created as a result of executing this Statement object
            java.sql.ResultSet generatedKeys = pre.getGeneratedKeys();
            if ( generatedKeys.next() ) {
                primkey = generatedKeys.getInt(1);
            }
        }
         return primkey;
    
     }
     public void OrderPaid(int id) throws SQLException
     {
          String req = "UPDATE orders SET paymentstate=? WHERE id = ?";
        PreparedStatement ste = con.prepareStatement(req);
                ste.setString(1,"paid");
                ste.setInt(2, id);
                ste.executeUpdate();


     }
    
}
