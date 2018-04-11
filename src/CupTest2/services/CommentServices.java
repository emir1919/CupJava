/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.entities.Comment;
import edu.cupcake.entities.Enseigne;
import edu.cupcake.utils.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emir
 */
public class CommentServices {

    Connection con = Connexion.getInstance().getConnection();
    private Statement stmt;

    public CommentServices() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void AjouterCommentaire(Comment c) throws SQLException {
        String req = "insert into comment(content,DateComment,user_id,enseigne_id)  values (?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
        pre.setString(1, c.getContent());
        pre.setString(2, c.getDateComment());
        pre.setInt(3, c.getUser_id());
        pre.setInt(4, c.getEnseigne_id());
        pre.executeUpdate();
        ResultSet generatedKeys = pre.getGeneratedKeys();
        if (generatedKeys.next()) {
            c.setId(generatedKeys.getInt(1));
        }
        System.out.println("commentaire ajouté");
    }

    public void ModifierCommentaire(Comment c) throws SQLException {
        String req = "update comment set content=? where id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, c.getContent());
        pre.setInt(2, c.getId());
        pre.executeUpdate();

        System.out.println("commentaire modifié");
    }

    public void SupprimerCommentaire(Comment c) throws SQLException {
        String req = "delete from comment where id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, c.getId());
        pre.executeUpdate();
        System.out.println("Enseigne suprimé");
    }

    public List<Comment> GetAllCommentByEnseigne(int id) throws SQLException {
        List<Comment> listP = new ArrayList<Comment>();
            String req = "select * from comment where enseigne_id=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, id);
                    ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Comment c = new Comment(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getInt(4), rs.getInt(5));
                listP.add(c);
            }
    return listP;
    }
    public List<String> GetCommentByEnseigne(int id) throws SQLException {
        List<String> listP = new ArrayList<String>();
            String req = "select * from comment where enseigne_id=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, id);
                    ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Comment c = new Comment(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getInt(4), rs.getInt(5));
                listP.add(c.getContent());
            }
    return listP;
    }
    
}
