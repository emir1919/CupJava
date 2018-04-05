/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import edu.cupcake.entities.Users;
import edu.cupcake.entities.Adresses;
import edu.cupcake.entities.Orders;

import edu.cupcake.utils.BCrypt;
import edu.cupcake.utils.Connexion;
import java.sql.Connection;
import java.sql.Date;
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
 * @author berrahal
 */
public class AdressesService {

    Connection con = Connexion.getInstance().getConnection();
    private Statement stmt;

    public AdressesService() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void ajouterAdresse(Adresses adresse) throws SQLException {
        String req = "INSERT INTO adresses (adresse,complement,cp,nom,pays,prenom,telephone,ville,utilisateur_id) values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        //   pre.setLong(1, user.getId());

        pre.setString(1, adresse.getAdresse());
        pre.setString(2, adresse.getComplement());
        pre.setString(3, adresse.getCp());
        pre.setString(4, adresse.getNom());

        pre.setString(5, adresse.getPays());
        pre.setString(6, adresse.getPrenom());
        pre.setString(7, adresse.getTelephone());
        pre.setString(8, adresse.getVille());
        pre.setInt(9, adresse.getUtilisateur_id());

        pre.executeUpdate();
    }

    /* public Adresses getAdressebyUserID(long id) throws SQLException {
        Adresses adresse = null;
        String req = "SELECT * FROM `users` WHERE id = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setLong(1, id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            adresse = new Adresses(id, req, req, req, req, req, req, req, req);

            adresse.setId(rs.getLong("id"));
            adresse.setUsername(rs.getString("username"));
            adresse.setEmail(rs.getString("email"));
            adresse.setRoles(rs.getString("roles"));
            adresse.setFirstname(rs.getString("firstname"));
            adresse.setBirthday(rs.getDate("birthday"));
            adresse.setLastname(rs.getString("lastname"));
            adresse.setPhonenumber(rs.getLong("phonenumber"));
            adresse.setPassword(rs.getString("password"));
            adresse.setEnabled(rs.getInt("enabled"));

            System.out.println("Utilisateur trouvé !");
        }
        return Adresses;
    }*/
    public List<Adresses> listeAdresses(int id) {
        List<Adresses> myList = new ArrayList<Adresses>();
        try {

            String requete2 = "SELECT * from adresses where utilisateur_id= ?";

            PreparedStatement st2 = con.prepareStatement(requete2);
            st2.setInt(1, id);
            ResultSet rs = st2.executeQuery();
            while (rs.next()) {
                Adresses a = new Adresses();
                a.setNom(rs.getString("nom"));
                a.setPrenom(rs.getString("prenom"));
                a.setTelephone(rs.getString("telephone"));
                a.setAdresse(rs.getString("adresse"));
                a.setComplement(rs.getString("complement"));
                a.setCp(rs.getString("cp"));
                a.setPays(rs.getString("pays"));
                a.setVille(rs.getString("ville"));
                a.setId(rs.getInt("id"));
                a.setUtilisateur_id(rs.getInt("utilisateur_id"));

                myList.add(a);

                //System.out.println("code est "+code+" nom est "+nom+" prenom est "+prenom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdressesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myList;
    }

    public Adresses getAdressebyId(int id) throws SQLException {
        Adresses a = null;
        String req = "SELECT * FROM `adresses` WHERE id = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            a = new Adresses();

            a.setNom(rs.getString("nom"));
            a.setPrenom(rs.getString("prenom"));
            a.setTelephone(rs.getString("telephone"));
            a.setAdresse(rs.getString("adresse"));
            a.setComplement(rs.getString("complement"));
            a.setCp(rs.getString("cp"));
            a.setPays(rs.getString("pays"));
            a.setVille(rs.getString("ville"));
            a.setId(rs.getInt("id"));
            a.setUtilisateur_id(rs.getInt("utilisateur_id"));

            System.out.println("Adresse trouvé !");
        }
        return a;
    }

    public void modifierAdresse(Adresses adresse, int id) throws SQLException {
        String req = "UPDATE adresses SET nom=?,prenom = ? ,adresse = ? ,complement= ?,cp= ? ,telephone= ? , ville= ? ,pays= ? WHERE id = ?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setLong(9, id);
        ste.setString(1, adresse.getNom());
        ste.setString(2, adresse.getPrenom());
        ste.setString(3, adresse.getAdresse());
        ste.setString(4, adresse.getComplement());
        ste.setString(5, adresse.getCp());
        ste.setString(6, adresse.getTelephone());
        ste.setString(7, adresse.getVille());
        ste.setString(8, adresse.getPays());

        ste.executeUpdate();

    }

    public void supprimerAdresse(int id) throws SQLException {
        String req = "DELETE FROM adresses WHERE id =?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);
        pre.executeUpdate();
    }

    public Adresses getAdressebyOrder(int id) throws SQLException {
        Adresses a = null;
        String req = "SELECT DISTINCT adresses.* FROM `adresses` inner join orders ON adresses.id = orders.adresse_id WHERE orders.id = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            a = new Adresses();

            a.setNom(rs.getString("nom"));
            a.setPrenom(rs.getString("prenom"));
            a.setTelephone(rs.getString("telephone"));
            a.setAdresse(rs.getString("adresse"));
            a.setComplement(rs.getString("complement"));
            a.setCp(rs.getString("cp"));
            a.setPays(rs.getString("pays"));
            a.setVille(rs.getString("ville"));
            a.setId(rs.getInt("id"));
            a.setUtilisateur_id(rs.getInt("utilisateur_id"));

            System.out.println("Adresse trouvé !");
        }
        return a;
    }

}
