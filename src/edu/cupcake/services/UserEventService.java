/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.cupcake.entities.TypeClaim;
import edu.cupcake.entities.UserEvent;
import edu.cupcake.utils.Connexion;

/**
 *
 * @author El Kamel
 */
public class UserEventService {
    
        Connection cn = Connexion.getInstance().getConnection();

    
    public void AddUV(UserEvent uv) {

        String requete = "INSERT INTO user_event(user_id,event_id,confirmed,scanned) VALUES (?,?,?,?)";

        try {

            PreparedStatement st = cn.prepareStatement(requete);
            st.setInt(1, uv.getUserId());
            st.setInt(2, uv.getEventId());
            st.setInt(3,uv.getScanned());
            st.setBoolean(4, uv.getConfirmed());

            st.executeUpdate();
            System.out.println("UserEvent ajoutée");

        } catch (SQLException ex) {
            Logger.getLogger(UserEventService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public List<UserEvent> FindByUserID(int id)  {
        
        
            
        List<UserEvent> listUV = new ArrayList<>();
        
        try {
        String req = "SELECT * FROM `user_event` WHERE user_id = ?";
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1,id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            
            
              listUV.add(new UserEvent(
                         rs.getInt("id"),
                         rs.getBoolean("confirmed"),
                         rs.getInt("scanned"),
                         rs.getInt("event_id"),
                         rs.getInt("user_id")

                      
                ));
     
        }
        
              } catch (SQLException ex) {
            Logger.getLogger(UserEventService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listUV;
    }
    
    public int FindIdByObject(UserEvent uv) throws SQLException {
        

        String req = "SELECT * FROM `user_event` WHERE user_id = ? and event_id = ?";
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1,uv.getUserId());
        pre.setInt(2,uv.getEventId());
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {

            uv.setId(rs.getInt("id"));


        }

        return uv.getId();
    }
    
}
