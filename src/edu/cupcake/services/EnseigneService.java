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
import edu.cupcake.entities.Enseigne;
import edu.cupcake.utils.Connexion;

/**
 *
 * @author El Kamel
 */
public class EnseigneService {
    
         Connection cn = Connexion.getInstance().getConnection();

    
        public void AddEnseigne(Enseigne e) {

        String requete = "INSERT INTO enseigne(user_id,Name,Address,PhoneNumber,Fax,Email,Website,Logo,CodeRC,Description,nbClaims) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try {

            PreparedStatement st = cn.prepareStatement(requete);
             
            st.setInt(1,e.getUser_id());
            st.setString(2,e.getName());
            st.setString(3,e.getAddress());
            st.setInt(4,e.getPhoneNumber());
            st.setInt(5,e.getFax());
            st.setString(6,e.getEmail());
            st.setString(7,e.getWebsite());
            st.setString(8,e.getLogo());
            st.setString(9,e.getCodeRC());
            st.setString(10,e.getDescription());
            st.setInt(11,e.getNbClaims());
            

            st.executeUpdate();
            System.out.println("Enseigne ajoutée");

        } catch (SQLException ex) {
            Logger.getLogger(EnseigneService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void EditEnseigne(Enseigne e ,int id) throws SQLException {
        
        
        String req = "UPDATE enseigne SET user_id=?,Name=?,Address=?,PhoneNumber=?,Fax=?,Email=?,Website=?,Logo=?,CodeRC=?,Description=?,nbClaims=? WHERE id = ?";
        PreparedStatement st = cn.prepareStatement(req);
        
        st.setInt(1,e.getUser_id());
        st.setString(2,e.getName());
        st.setString(3,e.getAddress());
        st.setInt(4,e.getPhoneNumber());
        st.setInt(5,e.getFax());
        st.setString(6,e.getEmail());
        st.setString(7,e.getWebsite());
        st.setString(8,e.getLogo());
        st.setString(9,e.getCodeRC());
        st.setString(10,e.getDescription());
        st.setInt(11,e.getNbClaims());
        st.setInt(12, id);
        st.executeUpdate();

    }
    
    public Enseigne FindByUserId(int id)  {
           
        Enseigne e = new Enseigne();
        
        try { 
        String req = "SELECT * FROM `enseigne` WHERE user_id = ?";
        PreparedStatement pre;
        
        pre = cn.prepareStatement(req);
        
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {

            e.setId(rs.getInt("id"));
            e.setAddress(rs.getString("Address"));
            e.setCodeRC(rs.getString("CodeRC"));
            e.setEmail(rs.getString("Email"));
            e.setFax(rs.getInt("Fax"));
            e.setLogo(rs.getString("Logo"));
            e.setName(rs.getString("Name"));
            e.setNbClaims(rs.getInt("nbClaims"));
            e.setPhoneNumber(rs.getInt("PhoneNumber"));
            e.setUser_id(rs.getInt("user_id"));
            e.setWebsite(rs.getString("Website"));
            e.setWebsite(rs.getString("Description"));

            
        }
        
       } catch (SQLException ex) {
            Logger.getLogger(EnseigneService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return e;
    }
    
    public List<Enseigne> FindAll() {

        List<Enseigne> listens = new ArrayList<>();

        try {

            String req = "SELECT * FROM enseigne";
            PreparedStatement st = cn.prepareStatement(req);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listens.add(new Enseigne(
                                
                               rs.getInt("id"),
                               rs.getInt("user_id"),
                               rs.getString("Name"),             
                               rs.getString("Address"),
                               rs.getInt("PhoneNumber"),        
                               rs.getInt("Fax"),   
                               rs.getString("Email"), 
                               rs.getString("Website"),   
                               rs.getString("Logo"),            
                               rs.getString("CodeRC"),
                               rs.getString("Description"),            
                               rs.getInt("nbClaims")                   
                        
                ));

            }

            System.out.println(listens.toString());

        } catch (SQLException ex) {
            Logger.getLogger(EnseigneService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listens;
    }  
    
    public List<String> GetNameENS() throws SQLException {
        
        List<String> listEns = new ArrayList<>();
        String req = "SELECT Name FROM  enseigne";
        PreparedStatement ste = cn.prepareStatement(req);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            listEns.add(
                    rs.getString("Name")
            );
        }
        return listEns;
    }
     
    public Enseigne FindByName(String name)  {
           
        Enseigne e = new Enseigne();
        
        try { 
        String req = "SELECT * FROM `enseigne` WHERE Name = ?";
        PreparedStatement pre;
        
        pre = cn.prepareStatement(req);    
        pre.setString(1,name);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {

            e.setId(rs.getInt("id"));
            e.setAddress(rs.getString("Address"));
            e.setCodeRC(rs.getString("CodeRC"));
            e.setEmail(rs.getString("Email"));
            e.setFax(rs.getInt("Fax"));
            e.setLogo(rs.getString("Logo"));
            e.setName(rs.getString("Name"));
            e.setNbClaims(rs.getInt("nbClaims"));
            e.setPhoneNumber(rs.getInt("PhoneNumber"));
            e.setUser_id(rs.getInt("user_id"));
            e.setWebsite(rs.getString("Website"));
            e.setWebsite(rs.getString("Description"));

            
        }
        
       } catch (SQLException ex) {
            Logger.getLogger(EnseigneService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return e;
    } 
    
        public Enseigne FindById(int id) throws SQLException {
        
        Enseigne e = new Enseigne(); 

        String req = "SELECT * FROM `enseigne` WHERE id = ?";
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1, id);
        
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {

            e.setId(rs.getInt("id"));
            e.setName(rs.getString("name"));


        }

        return e ;
    }
    
        
    
    
}
