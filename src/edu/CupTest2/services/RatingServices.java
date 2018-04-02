/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.services;

/**
 *
 * @author Emir
 */


import edu.CupTest2.entities.Rating;
import edu.CupTest2.utils.MyConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RatingServices {
    Connection con = MyConnexion.getInstance().getConection();
    private Statement stmt;

    public RatingServices() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public void ajouterRating(Rating r) throws SQLException{
            String requete = "insert into rating(enseigne_id, user_id,note) values(?, ?,?)";
            PreparedStatement st = con.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, r.getEnseigne_id());
            st.setInt(2, r.getUser_id());
            st.setFloat(3, r.getNote());
            st.executeUpdate();
             ResultSet generatedKeys = st.getGeneratedKeys();
        if (generatedKeys.next()) {
            r.setId(generatedKeys.getInt(1));
        }
        System.out.println("Rating ajouté");
        
        
    }
    
}
