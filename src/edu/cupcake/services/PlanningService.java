/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.entities.Planning;
import edu.cupcake.utils.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author berrahal
 */
public class PlanningService {
      Connection con = Connexion.getInstance().getConnection();
    private Statement stmt;

    public PlanningService() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
     public List<Planning> getPlanningsbyUserId(int id) throws SQLException {
        List<Planning> myList = new ArrayList<Planning>();
       

            String requete2 = "SELECT * from planning where utilisateur_id= ?";

            PreparedStatement st2 = con.prepareStatement(requete2);
            st2.setInt(1, id);
            ResultSet rs = st2.executeQuery();
            AdressesService asr = new AdressesService();
            while (rs.next()) {
                Planning a = new Planning();

                a.setId(rs.getInt("id"));
                a.setUtilisateur_id(rs.getInt("utilisateur_id"));
                a.setDatestart(rs.getTimestamp("datestart").toLocalDateTime());
                a.setDateend(rs.getTimestamp("dateend").toLocalDateTime());
                a.setLine_order(rs.getInt("lineorder_id"));
                                myList.add(a);


    }
    return myList;
}
     
      public void addOrder(Planning planning) throws SQLException {
        String req = "INSERT INTO planning (utilisateur_id,datestart,dateend,lineorder_id) values(?,?,?,?)";
       
                String columnNames[] = new String[] { "id" };
        PreparedStatement pre = con.prepareStatement(req,columnNames);
        //   pre.setLong(1, user.getId());

        pre.setInt(1, planning.getId());
        pre.setString(2, planning.getDatestart().toString());
        pre.setString(3, planning.getDateend().toString());
        pre.setInt(4, planning.getLine_order());

     
        
        pre.executeUpdate() ;
    
     }
     
     
}
