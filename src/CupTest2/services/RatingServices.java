/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

/**
 *
 * @author Emir
 */


import edu.cupcake.entities.Enseigne;
import edu.cupcake.entities.Rating;
import edu.cupcake.utils.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RatingServices {
    Connection con = Connexion.getInstance().getConnection();
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
    public double CalculerMoyRatring(int id) throws SQLException
    {
        double c=0;
        int a=0;
      String req = "SELECT count(*) as nb FROM rating where enseigne_id=?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setInt(1, id);
        ResultSet rs = ste.executeQuery(); 
        while(rs.next()){
         a=rs.getInt("nb");
        }
        
        
        double b=0;
      String req2 = "SELECT sum(note) as total FROM rating where enseigne_id=?";
        PreparedStatement ste2 = con.prepareStatement(req2);
        ste2.setInt(1, id);
        ResultSet rs2 = ste2.executeQuery(); 
        while(rs2.next()){
         b=rs2.getDouble("total");
        }
        if(a ==0)
        {
        c= 0;
        }
        else
        {
        c=b/a;}
       return Math.round(c * 100.0) / 100.0;
    }
    public int NbRating(int id) throws SQLException
    {
         int a=0;
      String req = "SELECT count(*) as nb FROM rating where enseigne_id=?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setInt(1, id);
        ResultSet rs = ste.executeQuery(); 
        while(rs.next()){
         a=rs.getInt("nb");
        }
        return a;
    }
    
}
