/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.services;

import edu.CupTest2.utils.MyConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import edu.CupTest2.entities.Users;
import java.sql.Date;
import edu.CupTest2.utils.BCrypt;
import java.sql.ResultSet;
import javafx.scene.control.Alert;

/**
 *
 * @author berrahal
 */
public class UsersService {

    Connection con = MyConnexion.getInstance().getConection();
    private Statement stmt;

    public UsersService() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void ajouterClient(Users user) throws SQLException {
        String req = "INSERT INTO users (username,username_canonical,email,email_canonical,password,phonenumber,firstname,lastname,birthday,roles,enabled,reduction) values(?,?,?,?,?,?,?,?,?,?,1,0)";
        PreparedStatement pre = con.prepareStatement(req);
        //   pre.setLong(1, user.getId());
        String password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        pre.setString(1, user.getUsername());
        pre.setString(2, user.getUsername());
        pre.setString(3, user.getEmail());
        pre.setString(4, user.getEmail());

        pre.setString(5, password);
        pre.setLong(6, user.getPhonenumber());
        pre.setString(7, user.getFirstname());
        pre.setString(8, user.getLastname());
        pre.setDate(9, (Date) user.getBirthday());
        pre.setString(10, user.getRoles());

        pre.executeUpdate();
    }

    public Users getUserById(long id) throws SQLException {
        Users u = null;
        String req = "SELECT * FROM `users` WHERE id = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setLong(1, id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            u = new Users();

            u.setId(rs.getLong("id"));
            u.setUsername(rs.getString("username"));
            u.setEmail(rs.getString("email"));
            u.setRoles(rs.getString("roles"));
            u.setFirstname(rs.getString("firstname"));
            u.setBirthday(rs.getDate("birthday"));
            u.setLastname(rs.getString("lastname"));
            u.setPhonenumber(rs.getLong("phonenumber"));
            u.setPassword(rs.getString("password"));
            u.setEnabled(rs.getInt("enabled"));

            System.out.println("Utilisateur trouvé !");
        }
        return u;
    }

    public Users searchByPseudoPass(String pseudo, String password) throws SQLException {
        Users u = null;
        String req = "SELECT * FROM `users` WHERE ((username = ? OR email = ?))";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, pseudo);
        pre.setString(2, pseudo);
        //pre.setString(3, password);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            u = new Users();
            u = UsersService.this.getUserById(rs.getLong("id"));
            if (BCrypt.checkpw(u.getPassword(), BCrypt.hashpw(password, BCrypt.gensalt())) == true) {
                return u;

            }

        }
        return u;
    }
    
    public void modifierClient(Users client, long id) throws SQLException {
        String req = "UPDATE users SET username=?,email = ? ,password = ? ,phonenumber=?, firstname=?, lastname=?, birthday=? WHERE id = ?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setString(1,client.getUsername());
        ste.setString(2, client.getEmail());
        ste.setString(3, client.getPassword());
        ste.setFloat(4, client.getPhonenumber());
        ste.setString(5, client.getFirstname());
        ste.setString(6, client.getLastname());
        ste.setDate(7,client.getBirthday());
        
        ste.setLong(8, id);
        ste.executeUpdate();
        Alert alertSucc = new Alert(Alert.AlertType.CONFIRMATION);
        alertSucc.setTitle("Succés");
        alertSucc.setContentText("Opération effectuer avec succés");
        alertSucc.setHeaderText(null);
        alertSucc.show();
    }

}
