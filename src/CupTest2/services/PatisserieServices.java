/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.services;

import edu.CupTest2.entities.Bakery;
import edu.CupTest2.entities.Enseigne;
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
public class PatisserieServices {

    Connection con = MyConnexion.getInstance().getConection();
    private Statement stmt;

    public PatisserieServices() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void ajouterPatisserie(Bakery patisserie) throws SQLException {
        String req = "INSERT INTO bakery (enseigne_id,user_id,name,address,phonenumber,fax,email)values(?,?,?,?,?,?,?)";

        PreparedStatement pre = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
        pre.setInt(1, patisserie.getId_enseigne());
        pre.setInt(2, patisserie.getId_user());
        pre.setString(3, patisserie.getName());
        pre.setString(4, patisserie.getAddress());
        pre.setString(5, patisserie.getPhoneNumber());
        pre.setString(6, patisserie.getFax());
        pre.setString(7, patisserie.getEmail());

        pre.executeUpdate();
        ResultSet generatedKeys = pre.getGeneratedKeys();
        if (generatedKeys.next()) {
        patisserie.setId(generatedKeys.getInt(1));}
        System.out.println("Patisserie ajouté");
    }

    public void modifierPatisserie(Integer id,String nom,String adresse,String tel,String fax,String email) throws SQLException {
        String req = "UPDATE bakery SET name=?,address = ? ,PhoneNumber = ? ,Fax=?,Email=? WHERE id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, nom);
        pre.setString(2,adresse);
        pre.setString(3, tel);
        pre.setString(4, fax);
        pre.setString(5, email);
        pre.setInt(6, id);
        pre.executeUpdate();

        System.out.println("Patisserie modifié");

    }

    public void supprimerPatisserie(int id) throws SQLException {
        String req = "DELETE FROM bakery WHERE id =?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);
        pre.executeUpdate();
        System.out.println("Patisserie suprimée");

    }

    public List<Bakery> selectAllPatisserie() throws SQLException {
        List<Bakery> listPatisserie = new ArrayList<>();
        String req = "SELECT * FROM bakery";
        PreparedStatement ste = con.prepareStatement(req);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            Bakery p = new Bakery();
            p.setId(rs.getInt(1));
            p.setName(rs.getString(4));
            p.setAddress(rs.getString(5));
            p.setPhoneNumber(rs.getString(6));
            p.setFax(rs.getString(7));
            p.setEmail(rs.getString(8));
            listPatisserie.add(p);
        }
        return listPatisserie;
    }

    public List<Bakery> getBakerybyBrand(Integer id) {
        Bakery b = null;
        List<Bakery> listPatisserie = new ArrayList<>();

        String req = "select * from bakery where enseigne_id=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                b = new Bakery();
                b.setId(rs.getInt(1));
                b.setName(rs.getString(4));
                b.setAddress(rs.getString(5));
                b.setPhoneNumber(rs.getString(6));
                b.setFax(rs.getString(7));
                b.setEmail(rs.getString(8));
                listPatisserie.add(b);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listPatisserie;

    }
  

}
