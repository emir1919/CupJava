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
import edu.cupcake.utils.Connexion;

/**
 *
 * @author El Kamel
 */
public class TypeClaimService {
    
    
         Connection cn = Connexion.getInstance().getConnection();


    public void AddTypeClaim(TypeClaim tc) {

        String requete = "INSERT INTO type_claim(name,nbClaims) VALUES (?,?)";

        try {

            PreparedStatement st = cn.prepareStatement(requete);
            st.setString(1, tc.getName());
            st.setInt(2, tc.getNbClaims());
            

            st.executeUpdate();
            System.out.println("Type Claim ajoutée");

        } catch (SQLException ex) {
            Logger.getLogger(TypeClaimService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void EditTypeClaim(TypeClaim tc ,int id) throws SQLException {
        
        
        String req = "UPDATE type_claim SET name=?,nbClaims=? WHERE id = ?";
        PreparedStatement st = cn.prepareStatement(req);
        
        st.setString(1,tc.getName());
        st.setInt(2,tc.getNbClaims());
        st.setInt(3, id);
        st.executeUpdate();

    }

    public void DeleteTypeClaim(int id) throws SQLException {
        String req = "DELETE FROM type_claim WHERE id=?";
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1, id);
        pre.executeUpdate();
        
    }

    public TypeClaim FindById(int id) throws SQLException {
        TypeClaim tc = new TypeClaim();

        String req = "SELECT * FROM `type_claim` WHERE id = ?";
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {

            tc.setId(rs.getInt("id"));
            tc.setName(rs.getString("name"));
            tc.setNbClaims(rs.getInt("nbClaims"));
           
                        
        }

        return tc;
    }

    public List<TypeClaim> FindAll() {

        List<TypeClaim> listTC = new ArrayList<>();

        try {

            String req = "SELECT * FROM type_claim";
            PreparedStatement st = cn.prepareStatement(req);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listTC.add(new TypeClaim(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("nbClaims")
                      
                ));

            }

            System.out.println(listTC.toString());

        } catch (SQLException ex) {
            Logger.getLogger(TypeClaimService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTC;
    }
    
    public List<String> GetNameTypes() throws SQLException {
        
        List<String> listTypes = new ArrayList<>();
        String req = "SELECT name FROM  type_claim";
        PreparedStatement ste = cn.prepareStatement(req);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            listTypes.add(
                    rs.getString("name")
            );
        }
        return listTypes;
    }
    
    public TypeClaim FindByName(String name)  {
        
        
            
        TypeClaim tc = new TypeClaim();
        
        try {
        String req = "SELECT * FROM `type_claim` WHERE name = ?";
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setString(1, name);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {

            tc.setId(rs.getInt("id"));
            tc.setName(rs.getString("name"));
            tc.setNbClaims(rs.getInt("nbClaims"));          
                        
        }
        
              } catch (SQLException ex) {
            Logger.getLogger(TypeClaimService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tc;
    }
    
}
