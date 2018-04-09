/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.services;

import edu.CupTest2.entities.Enseigne;
import edu.CupTest2.entities.Product;
import edu.CupTest2.entities.Users;
import edu.CupTest2.gui.AddBakeryController;
import edu.CupTest2.gui.AddBrandController;
import edu.CupTest2.gui.InfoBrandController;
import edu.CupTest2.utils.MyConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Emir
 */
public class EnseigneServices {

    Connection con = MyConnexion.getInstance().getConection();
    private Statement stmt;

    public EnseigneServices() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void ajouterEnseigne(Enseigne enseigne) throws SQLException {
        String req = "INSERT INTO Enseigne(user_id,name,address,phonenumber,fax,email,website,logo,CodeRC,description) values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
        pre.setString(2, enseigne.getName());
        pre.setString(3, enseigne.getAddress());
        pre.setString(4, enseigne.getPhoneNumber());
        pre.setString(5, enseigne.getFax());
        pre.setString(6, enseigne.getEmail());
        pre.setString(7, enseigne.getWebSite());
        pre.setString(8, enseigne.getLogo());
        pre.setString(9, enseigne.getCodeRc());
        pre.setString(10, enseigne.getDescription());
        pre.setInt(1, enseigne.getId_user());
        pre.executeUpdate();
        ResultSet generatedKeys = pre.getGeneratedKeys();
        if (generatedKeys.next()) {
            enseigne.setId(generatedKeys.getInt(1));
        }
        System.out.println("Enseigne ajouté");
    }

    public void modifierEnseigne(Integer id, String name, String address, String PhoneNumber, String Fax, String Email, String Website, String Logo, String CodeRC, String Description) throws SQLException {
        String req = "UPDATE enseigne SET name=?,address=?,PhoneNumber=?,Fax=? ,Email=?,Website=?, Logo=?,CodeRC=?,Description=? WHERE id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, name);

        pre.setString(2, address);
        pre.setString(3, PhoneNumber);

        pre.setString(4, Fax);
        pre.setString(5, Email);
        pre.setString(6, Website);
        pre.setString(7, Logo);
        pre.setString(8, CodeRC);
        pre.setString(9, Description);
        //pre.setInt(12, enseigne.getNbClaims());
        pre.setInt(10, id);
        pre.executeUpdate();

        System.out.println("Enseigne modifié");

    }

    public void supprimerEnseigne(int id) throws SQLException {
        String req = "DELETE FROM enseigne WHERE id =?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);
        pre.executeUpdate();
        System.out.println("Enseigne suprimé");

    }

    public List<Enseigne> selectAllEnseigne() throws SQLException {
        List<Enseigne> listEnseigne = new ArrayList<>();
        String req = "SELECT * FROM enseigne";
        PreparedStatement ste = con.prepareStatement(req);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            Enseigne p = new Enseigne();
            p.setId(rs.getInt(1));
            p.setName(rs.getString(3));
            p.setAddress(rs.getString(4));
            p.setPhoneNumber(rs.getString(5));
            p.setWebSite(rs.getString(8));
            p.setLogo(rs.getString(9));
            p.setDescription(rs.getString(11));
            listEnseigne.add(p);
        }
        return listEnseigne;
    }

    public Enseigne getEnseignebyName(String nom) {
        Enseigne p = null;
        String req = "select * from enseigne where name=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1, nom);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                p = new Enseigne();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(3));
                p.setAddress(rs.getString(4));
                p.setPhoneNumber(rs.getString(5));
                p.setWebSite(rs.getString(8));
                p.setLogo(rs.getString(9));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return p;

    }

    public Enseigne getEnseignebyId(Integer id) {
        Enseigne p = null;
        String req = "select * from enseigne where id=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                p = new Enseigne();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(3));
                p.setAddress(rs.getString(4));
                p.setPhoneNumber(rs.getString(5));
                p.setWebSite(rs.getString(8));
                p.setLogo(rs.getString(9));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return p;

    }

    public void getImage(ImageView img) throws SQLException {
        int idModule = AddBakeryController.IdEnseigne;
        String requete2 = "select * from enseigne where id = ?";
        PreparedStatement st2 = con.prepareStatement(requete2);
        st2.setInt(1, idModule);
        ResultSet rs2 = st2.executeQuery();
        if (rs2.first()) {
            String imageName = rs2.getString("logo");
            System.out.println(imageName);
            Image image = new Image("/edu/CupTest2/images/" + imageName);
            System.out.println("/edu/CupTest2/images/" + imageName);
            img.setImage(image);
        }

    }

   

    public List<Users> getAllUsers() throws SQLException {
        Users u = null;
        List<Users> listUser = new ArrayList<>();

        String req = "SELECT * FROM `users` ";
        PreparedStatement pre = con.prepareStatement(req);
        //pre.setString(1, role);
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
            listUser.add(u);
        }
        return listUser;
    }
 public List<Product> TopProductByBrand(int test) throws SQLException {
        Product u = null;
        List<Product> products = new ArrayList<>();

         String requete2 = "select * from product  where enseigne_id = ? order by rating";
        PreparedStatement st2 = con.prepareStatement(requete2);
        st2.setInt(1, test);
        ResultSet rs = st2.executeQuery();
        while (rs.next()) {
             products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getDouble("rating"),
                        rs.getDouble("reduction"),
                        rs.getString("image_name"),
                        rs.getInt("sales"),
                        rs.getDate("updated_at"),
                        rs.getDate("added_at"),
                        rs.getInt("subcategory_id"),
                        rs.getInt("enseigne_id")
                ));
        }
        return products;
    }
  public Enseigne getEnseignebyUserId(Integer id) {
        Enseigne p = null;
        String req = "select * from enseigne where user_id=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                p = new Enseigne();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(3));
                p.setAddress(rs.getString(4));
                p.setPhoneNumber(rs.getString(5));
                p.setWebSite(rs.getString(8));
                p.setLogo(rs.getString(9));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return p;

    }

}
