/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.services;

import edu.CupTest2.entities.Comment;
import edu.CupTest2.entities.favory_brand;
import edu.CupTest2.utils.MyConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Emir
 */
public class FavoryBrandServices {
   Connection con = MyConnexion.getInstance().getConection();
    private Statement stmt;

    public FavoryBrandServices() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void AjouterFavori(favory_brand c) throws SQLException {
        String req = "insert into favory_brand(user_id,enseigne_id)  values (?,?)";
        PreparedStatement pre = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
        pre.setInt(1, c.getUser_id());
        pre.setInt(2, c.getEnseigne_id());
        pre.executeUpdate();
        ResultSet generatedKeys = pre.getGeneratedKeys();
        if (generatedKeys.next()) {
            c.setId(generatedKeys.getInt(1));
        }
        System.out.println("Favori ajouté");
    } 
}
