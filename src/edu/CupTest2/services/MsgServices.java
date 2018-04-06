/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.services;

import edu.CupTest2.entities.Comment;
import edu.CupTest2.entities.Msg;
import edu.CupTest2.utils.MyConnexion;
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
public class MsgServices {
    Connection con = MyConnexion.getInstance().getConection();
    private Statement stmt;

    public MsgServices() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void AjouterMsg(Msg c) throws SQLException {
        String req = "insert into msg(emetteur_id,recepteur_id,subject,body,piece,lu,DateEnvoi)  values (?,?,?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
        pre.setInt(1, c.getEmetteur_id());
        pre.setInt(2, c.getRecepteur_id());
        pre.setString(3, c.getSubject());
        pre.setString(4, c.getBody());
        pre.setString(5, c.getPiece());
        pre.setInt(6, c.getLu());
        pre.setString(7, c.getDateEnvoi());

        pre.executeUpdate();
        ResultSet generatedKeys = pre.getGeneratedKeys();
        if (generatedKeys.next()) {
            c.setId(generatedKeys.getInt(1));
        }
        System.out.println("Message ajouté");
    }
     public List<Msg> GetAllMsgByEmeteur(int id) throws SQLException {
        List<Msg> listP = new ArrayList<Msg>();
            String req = "select * from msg where emetteur_id=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, id);
                    ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Msg c = new Msg(rs.getInt(1),rs.getInt(2),rs.getInt(3), rs.getString(4),rs.getString(5), rs.getString(6), rs.getInt(7),rs.getString(8));
                listP.add(c);
            }
    return listP;
    }
     public List<Msg> GetAllMsgByRecepteur(int id) throws SQLException {
        List<Msg> listP = new ArrayList<Msg>();
            String req = "select * from msg where recepteur_id=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, id);
                    ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Msg c = new Msg(rs.getInt(1),rs.getInt(2),rs.getInt(3), rs.getString(4),rs.getString(5), rs.getString(6), rs.getInt(7),rs.getString(8));
                listP.add(c);
            }
    return listP;
    }
}
