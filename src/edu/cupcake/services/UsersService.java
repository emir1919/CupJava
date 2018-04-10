/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.utils.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import edu.cupcake.entities.Users;
import java.sql.Date;
import edu.cupcake.utils.BCrypt;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author berrahal
 */
public class UsersService {

    Connection con = Connexion.getInstance().getConnection();
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
    
    
     public void AddFbUser(Users user) throws SQLException {
        String req = "INSERT INTO users (id,username,username_canonical,email,email_canonical,password,phonenumber,firstname,lastname,birthday,roles,enabled,reduction) values(?,?,?,?,?,?,?,?,?,?,?,1,0)";
        PreparedStatement pre = con.prepareStatement(req);
        String password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

       pre.setInt(1, user.getId());

        pre.setString(2, user.getUsername());
        pre.setString(3, user.getUsername());
        pre.setString(4, user.getEmail());
        pre.setString(5, user.getEmail());

        pre.setString(6, password);
        pre.setLong(7, user.getPhonenumber());
        pre.setString(8, user.getFirstname());
        pre.setString(9, user.getLastname());
        pre.setDate(10, (Date) user.getBirthday());
        pre.setString(11, user.getRoles());

        pre.executeUpdate();
    }
    
    
    

    public Users getUserById(int id) throws SQLException {
        Users u = null;
        String req = "SELECT * FROM `users` WHERE id = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            u = new Users();

            u.setId(rs.getInt("id"));
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
            u = UsersService.this.getUserById(rs.getInt("id"));
            if (BCrypt.checkpw(u.getPassword(), BCrypt.hashpw(password, BCrypt.gensalt())) == true) {
                return u;

            }
            
           
            

        }
        return u;
    }
    
    public void modifierClient(Users client, int id) throws SQLException {
        String req = "UPDATE users SET username=?,email = ? ,password = ? ,phonenumber=?, firstname=?, lastname=?, birthday=? WHERE id = ?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setString(1,client.getUsername());
        ste.setString(2, client.getEmail());
        ste.setString(3, client.getPassword());
        ste.setFloat(4, client.getPhonenumber());
        ste.setString(5, client.getFirstname());
        ste.setString(6, client.getLastname());
        ste.setDate(7,client.getBirthday());
        
        ste.setInt(8, id);
        ste.executeUpdate();
        Alert alertSucc = new Alert(Alert.AlertType.CONFIRMATION);
        alertSucc.setTitle("Succés");
        alertSucc.setContentText("Opération effectuer avec succés");
        alertSucc.setHeaderText(null);
        alertSucc.show();
    }
    
    public void changePassword(String password, String email) throws SQLException
    {
        String req = "UPDATE users SET password = ?  WHERE email = ?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setString(1,password);
        
        
        ste.setString(2, email);
        ste.executeUpdate();
    }
    
    public Users getUserByEmail(String email) throws SQLException {
        Users u = null;
        String req = "SELECT * FROM `users` WHERE email = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, email);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            u = new Users();

            u.setId(rs.getInt("id"));
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
    
      public void EditUserPF(Users u) throws SQLException {
        
        
        String req = "UPDATE users SET PointsF = ?,Reduction = ? WHERE id = ?";
        PreparedStatement st = con.prepareStatement(req);
        
        st.setInt(1,u.getPF());
        st.setInt(2,u.getReduction());
        st.setInt(3,u.getId());
 
        st.executeUpdate();

    }
    
        public Users getUserPFById(long id) throws SQLException {
        Users u = null;
        String req = "SELECT * FROM `users` WHERE id = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setLong(1, id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            u = new Users();

            u.setId(rs.getInt("id"));
            u.setEmail(rs.getString("email"));
            u.setRoles(rs.getString("roles"));
            u.setPF(rs.getInt("PointsF"));
            u.setReduction(rs.getInt("Reduction"));


            System.out.println("Utilisateur trouvé !");
        }
        return u;
    }
        
        public List<Users> getUserbyRole(String role) throws SQLException {
                List<Users> myList = new ArrayList<Users>();

          
        String req = "SELECT * FROM users WHERE roles LIKE ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, "%"+role+"%");
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
          Users u = new Users();

            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setEmail(rs.getString("email"));
            u.setRoles(rs.getString("roles"));
            u.setFirstname(rs.getString("firstname"));
            u.setBirthday(rs.getDate("birthday"));
            u.setLastname(rs.getString("lastname"));
            u.setPhonenumber(rs.getLong("phonenumber"));
            u.setPassword(rs.getString("password"));
            u.setEnabled(rs.getInt("enabled"));
                                myList.add(u);

            
        }
        return myList;
    }

}
