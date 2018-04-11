/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.entities.Comment;
import edu.cupcake.entities.Msg;
import edu.cupcake.entities.Users;
import edu.cupcake.entities.favory_brand;
import edu.cupcake.utils.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Emir
 */
public class FavoryBrandServices {
    Connection con = Connexion.getInstance().getConnection();
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
    public List<favory_brand> GetFavoriById(int id) throws SQLException {
        List<favory_brand> listP = new ArrayList<favory_brand>();
        String req = "select * from favory_brand where user_id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            favory_brand c = new favory_brand(rs.getInt(1), rs.getInt(2), rs.getInt(3));
            listP.add(c);
        }
        return listP;
    }
     public  favory_brand getfavoryById(int id1,int id2) throws SQLException {
         favory_brand c=null;
        String req = "SELECT * FROM favory_brand WHERE enseigne_id = ? and user_id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id1);
                pre.setInt(2, id2);

        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            c=new favory_brand();
          c.setEnseigne_id(rs.getInt(3));
          c.setUser_id(rs.getInt(2));
        }
        return c;
    }
}
