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
import edu.cupcake.entities.Claims;
import edu.cupcake.utils.Connexion;

/**
 *
 * @author El Kamel
 */
public class ClaimsService {

        Connection cn = Connexion.getInstance().getConnection();


    public void AddClaim(Claims c) {

        String requete = "INSERT INTO claims(enseigne_id,date,Description,typeclaim_id,name,email,phone) VALUES (?,?,?,?,?,?,?)";

        try {

            PreparedStatement st = cn.prepareStatement(requete);
            st.setInt(1, c.getEnseigneId());
            st.setDate(2, c.getDate());
            st.setString(3, c.getDescription());
            st.setInt(4, c.getTypeclaimId());
            st.setString(5, c.getName());
            st.setString(6, c.getEmail());
            st.setInt(7, c.getPhone());

            st.executeUpdate();
            System.out.println("Claims ajoutée");

        } catch (SQLException ex) {
            Logger.getLogger(ClaimsService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void EditClaim(Claims c, int id) throws SQLException {

        String req = "UPDATE claims SET enseigne_id = ?,date = ?,Description = ?,typeclaim_id = ?,name = ?,email = ? , phone =? WHERE id = ?";
        PreparedStatement st = cn.prepareStatement(req);

        st.setInt(1, c.getEnseigneId());
        st.setDate(2, c.getDate());
        st.setString(3, c.getDescription());
        st.setInt(4, c.getTypeclaimId());
        st.setString(5, c.getName());
        st.setString(6, c.getEmail());
        st.setInt(7, c.getPhone());
        st.setInt(8, id);
        st.executeUpdate();

    }

    public void DeleteCalim(int id) throws SQLException {
        String req = "DELETE FROM claims WHERE id=?";
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1, id);
        pre.executeUpdate();

    }

    public Claims FindById(int id) throws SQLException {
        Claims c = new Claims();

        String req = "SELECT * FROM `claims` WHERE id = ?";
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {

            c.setId(rs.getInt("id"));
            c.setEnseigneId(rs.getInt("enseigne_id"));
            c.setDate(rs.getDate("date"));
            c.setDescription(rs.getString("Description"));
            c.setTypeclaimId(rs.getInt("typeclaim_id"));
            c.setName(rs.getString("name"));
            c.setEmail(rs.getString("email"));
            c.setPhone(rs.getInt("phone"));

        }

        return c;
    }

    public List<Claims> FindAll() {

        List<Claims> listClaims = new ArrayList<>();

        try {

            String req = "SELECT * FROM claims";
            PreparedStatement st = cn.prepareStatement(req);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listClaims.add(new Claims(
                rs.getInt("id"),
                rs.getDate("date"),
                rs.getString("Description"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("phone"),
                rs.getInt("typeclaim_id"),
                rs.getInt("enseigne_id")
                ));

            }

            System.out.println(listClaims.toString());

        } catch (SQLException ex) {
            Logger.getLogger(AddsService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listClaims;
    }
    
    public List<Claims> findBytypeclaim(int id){
        
                List<Claims> listClaims = new ArrayList<>();

        try {

            String req = "SELECT * FROM `claims` WHERE typeclaim_id = ?";
            
            PreparedStatement pre = cn.prepareStatement(req);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            
            while (rs.next()) {
                listClaims.add(new Claims(
                rs.getInt("id"),
                rs.getDate("date"),
                rs.getString("Description"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("phone"),
                rs.getInt("typeclaim_id"),
                rs.getInt("enseigne_id")
                ));

            }

            System.out.println(listClaims.toString());

        } catch (SQLException ex) {
            Logger.getLogger(AddsService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listClaims;
        
    }
    
    public List<Claims> findByenseneig(int id){
        
                List<Claims> listClaims = new ArrayList<>();

        try {

            String req = "SELECT * FROM `claims` WHERE enseigne_id = ?";
            
            PreparedStatement pre = cn.prepareStatement(req);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            
            while (rs.next()) {
                listClaims.add(new Claims(
                rs.getInt("id"),
                rs.getDate("date"),
                rs.getString("Description"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("phone"),
                rs.getInt("typeclaim_id"),
                rs.getInt("enseigne_id")
                ));

            }

            System.out.println(listClaims.toString());

        } catch (SQLException ex) {
            Logger.getLogger(AddsService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listClaims;
        
    }

}
