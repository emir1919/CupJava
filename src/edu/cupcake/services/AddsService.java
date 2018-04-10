/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.entities.Adds;
import edu.cupcake.entities.Enseigne;
import edu.cupcake.utils.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author El Kamel
 */
public class AddsService {

     Connection cn = Connexion.getInstance().getConnection();


    public void AddAdds(Adds a) {

        String requete = "INSERT INTO adds(enseigne_id,Title,Image,Description,Rank) VALUES (?,?,?,?,?)";

        try {

            PreparedStatement st = cn.prepareStatement(requete);
            st.setInt(1, a.getEnseigne_id());
            st.setString(2, a.getTitle());
            st.setString(3, a.getImage());
            st.setString(4, a.getDescription());
            st.setInt(5, a.getRank());

            st.executeUpdate();
            System.out.println("Adds ajoutée");

        } catch (SQLException ex) {
            Logger.getLogger(AddsService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void EditAdds(Adds a1 ,int id) throws SQLException {
        
        
        String req = "UPDATE Adds SET Title=?,Image = ? ,Description = ? ,Rank=? WHERE id = ?";
        PreparedStatement st = cn.prepareStatement(req);
        
        st.setString(1,a1.getTitle());
        st.setString(2,a1.getImage());
        st.setString(3,a1.getDescription());
        st.setInt(4,a1.getRank());
        st.setInt(5, id);
        st.executeUpdate();

    }

    public void DeleteAdds(int id) throws SQLException {
        String req = "DELETE FROM adds WHERE id=?";
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1, id);
        pre.executeUpdate();
        
    }

    public Adds FindById(int id) throws SQLException {
        Adds a = new Adds();

        String req = "SELECT * FROM `adds` WHERE id = ?";
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {

            a.setId(rs.getInt("id"));
            a.setEnseigne_id(rs.getInt("enseigne_id"));
            a.setTitle(rs.getString("Title"));
            a.setImage(rs.getString("Image"));
            a.setDescription(rs.getString("Description"));
            a.setRank(rs.getInt("Rank"));
                        
        }

        return a;
    }

    public List<Adds> FindAll() {

        List<Adds> listAdds = new ArrayList<>();

        try {

            String req = "SELECT * FROM adds";
            PreparedStatement st = cn.prepareStatement(req);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listAdds.add(new Adds(
                        rs.getInt("id"),
                        rs.getInt("enseigne_id"),
                        rs.getString("Title"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        rs.getInt("Rank")
                ));

            }

            System.out.println(listAdds.toString());

        } catch (SQLException ex) {
            Logger.getLogger(AddsService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listAdds;
    }

}
