/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.tests;

import edu.CupTest2.entities.Enseigne;
import edu.CupTest2.entities.Bakery;
import edu.CupTest2.entities.Comment;
import edu.CupTest2.entities.Msg;
import edu.CupTest2.entities.Rating;
import edu.CupTest2.entities.Users;
import edu.CupTest2.entities.favory_brand;
import edu.CupTest2.services.CommentServices;
import edu.CupTest2.services.EnseigneServices;
import edu.CupTest2.services.FavoryBrandServices;
import edu.CupTest2.services.MsgServices;
import edu.CupTest2.services.PatisserieServices;
import edu.CupTest2.services.RatingServices;
import edu.CupTest2.services.UsersService;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emir
 */
public class main {

    public static void main(String[] args) throws SQLException {
        Enseigne e1 = new Enseigne();
        e1.setCodeRc("3334");
        e1.setName("emir4");
        e1.setDescription("ff");
        e1.setLogo("1.jpg");
        e1.setEmail("joj");
        e1.setWebSite("hh");
        e1.setAddress("rades");
        e1.setPhoneNumber("22149535");
        e1.setFax("22");
        e1.setId_user(1);
        EnseigneServices es = new EnseigneServices();
        Bakery b1 = new Bakery();
        b1.setEmail("cc");
        b1.setPhoneNumber("22149535");
        b1.setName("khalil");
        b1.setFax("222");
        b1.setAddress("tunis");
        b1.setId_user(2);
        b1.setId_enseigne(242);
        Bakery b2 = new Bakery();

        PatisserieServices ps = new PatisserieServices();
        //ps.ajouterPatisserie(b1);
        //ps.modifierPatisserie(b1, 44);
        //ps.supprimerPatisserie(44);
        //System.out.println(ps.selectAllPatisserie().toString());
        //es.ajouterEnseigne(e1);
        //es.modifierEnseigne(e1);
        // es.supprimerEnseigne(99);
        //System.out.println(es.getEnseignebyName("emir4").toString());
        Users u = new Users();
        u.setRoles("Bakery");
        /// System.out.println(es.getUserById());
        //System.out.println(u.toString());
        //System.out.println(es.TopProductByBrand(1).toString());  
        Rating r = new Rating();
        RatingServices rs = new RatingServices();
        r.setUser_id(1);
        r.setEnseigne_id(100);
        r.setNote((float) 4.36);
        //rs.ajouterRating(r); 
        //System.out.println(r.toString());
        Comment c = new Comment();
        //c.setId(1);
        c.setContent("salut");
        c.setDateComment("11/04/2017");
        c.setEnseigne_id(1);
        c.setUser_id(1);
        CommentServices cs = new CommentServices();
        //cs.AjouterCommentaire(c);
        //cs.ModifierCommentaire(c);
        //System.out.println(cs.GetAllCommentByEnseigne(1).toString());
        System.out.println(cs.GetCommentByEnseigne(1).toString());
        System.out.println(rs.CalculerMoyRatring(200));
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

// Using DateFormat format method we can create a string 
// representation of a date with the defined format.
        String reportDate = df.format(new java.util.Date().getTime());
        Msg m1 = new Msg();
        m1.setId(1);
        m1.setEmetteur_id(4);
        m1.setRecepteur_id(2);
        m1.setBody("aa");
        m1.setSubject("aab");
        m1.setLu(0);
        m1.setDateEnvoi(reportDate);
        m1.setPiece("pdf");
        MsgServices ms = new MsgServices();
        //ms.AjouterMsg(m1);
        //System.out.println(ms.GetAllMsgByEmeteur(4).toString());
        //System.out.println(ms.GetAllMsgByRecepteur(4).toString());
        UsersService us=new UsersService();
        //System.out.println(us.searchByEmail("brand@brand.com").toString());
        favory_brand fb=new favory_brand(1,1,1);
        FavoryBrandServices fs=new FavoryBrandServices();
        //fs.AjouterFavori(fb);
        //System.out.println(ms.getMsgById(1).toString());
        //ms.ModifierMsg(3, 1);
               // ms.ModifierMsg(3, 0);
System.out.println(ms.GetCountReceived(1));
System.out.println(ms.GetCountSended(1));

    }
}
