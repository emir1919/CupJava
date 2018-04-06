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
     
      public int addPlanningDisponible(Planning planning) throws SQLException {
        String req = "INSERT INTO planning (utilisateur_id,datestart,dateend) values(?,?,?)";
       
                String columnNames[] = new String[] { "id" };
        PreparedStatement pre = con.prepareStatement(req,columnNames);
        //   pre.setLong(1, user.getId());

        pre.setInt(1, planning.getUtilisateur_id());
        pre.setString(2, planning.getDatestart().toString());
        pre.setString(3, planning.getDateend().toString());
        //pre.setInt(4, planning.getLine_order());

     
        
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
      
      
       public void EditPlanningUser(Planning planning, int id) throws SQLException {
        String req = "UPDATE planning SET datestart = ? ,dateend = ? WHERE id = ?";
        PreparedStatement ste = con.prepareStatement(req);
        
        ste.setString(1, planning.getDatestart().toString());
        ste.setString(2, planning.getDateend().toString());
     
        
        ste.setInt(3, id);
        ste.executeUpdate();
        
    }
       
       
         public void DeletePlanning(int id) throws SQLException {
        String req = "DELETE FROM planning WHERE id =?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);
        pre.executeUpdate();
    }
     
     
}
