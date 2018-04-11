/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.User;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import edu.cupcake.services.UsersService;
import edu.cupcake.entities.Users;
import java.io.IOException;
import javafx.scene.control.Alert.AlertType;
import cupcake.Cupcake;
import edu.cupcake.entities.Enseigne;
import edu.cupcake.services.EnseigneServices;
import edu.cupcake.utils.BCrypt;
import edu.cupcake.utils.Routing;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.management.Notification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private Label txtetat;
    @FXML
    private JFXButton connectbutton;
    @FXML
    private VBox VBoxInfoPersonel;
    @FXML
    private VBox VBoxMdp;
    @FXML
    private JFXButton back;
    @FXML
    private JFXTextField txtEmailRecover;
    @FXML
    private JFXButton btnsendrecover;
    @FXML
    private JFXButton facebookButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cupcake.Cupcake.lastroute = cupcake.Cupcake.currentroute;
        cupcake.Cupcake.currentroute = Routing.LOGIN;
        txtEmailRecover.setVisible(false);
        btnsendrecover.setVisible(false);
    }

    @FXML
    public void Back(ActionEvent event) throws IOException {
        //Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource(cupcake.Cupcake.lastroute));
        // Scene scene = new Scene(root);

        // stage.setScene(scene);
        //stage.show();
        connectbutton.getScene().setRoot(root);

    }

    @FXML
    public void SeConnecter() throws IOException, SQLException {
        if (validateInputs()) {
            UsersService us = new UsersService();
            String pseudo = txtUsername.getText();
            String password = txtPassword.getText();

            Users u = us.searchByPseudoPass(pseudo, password);
            System.out.println(u);
            if (u != null && u.getEnabled() == 1 && BCrypt.checkpw(txtPassword.getText(), u.getPassword())) {
                Cupcake.user = u;

                System.out.println("sooooo nice");
                txtetat.setText("");

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Succès");
                alert.setHeaderText("Authentifié");
                alert.setContentText("Vous êtes connecté en tant que :" + u.getUsername());

                alert.showAndWait();
                AnchorPane root = getRole(u);
                /*AnchorPane root;

                EnseigneServices es = new EnseigneServices();
                Enseigne e = new Enseigne();
                e = es.getEnseignebyUserId((cupcake.Cupcake.user.getId()));
                if (e == null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/AddBrand.fxml"));
                    root = (AnchorPane) loader.load();

                } else {
                    BackBrandController.informations=1;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/BackBrand.fxml"));
                    root = (AnchorPane) loader.load();
                }
                /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Home.fxml"));
                AnchorPane root = (AnchorPane) loader.load();*/

                txtPassword.getScene().setRoot(root);

            } else if (u != null && u.getEnabled() == 0) {
                txtetat.setText("Votre compte est desactivé.");

            } else {
                txtetat.setText("Identifiants incorrects.");
            }

        }
    }
    
    public AnchorPane getRole(Users user) throws IOException{
        AnchorPane root;
        //ObservableList<String> RoleList = FXCollections.observableArrayList("ROLE_USER", "ROLE_ADMIN","ROLE_DELIVERYMAN","ROLE_BAKERY","ROLE_BRAND");
        if (user.getRoles().contains("ROLE_ADMIN")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/CategoriesAdmin.fxml"));
            root = (AnchorPane) loader.load();
        }else if(user.getRoles().contains("ROLE_BRAND")){
            EnseigneServices es = new EnseigneServices();
            Enseigne e = new Enseigne();
            e = es.getEnseignebyUserId((cupcake.Cupcake.user.getId()));
            if (e == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/AddBrand.fxml"));
                root = (AnchorPane) loader.load();

            } else {
                BackBrandController.informations = 1;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/BackBrand.fxml"));
                root = (AnchorPane) loader.load();
            }
        }else if(user.getRoles().contains("ROLE_BAKERY")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/BakeryManagement.fxml"));
            root = (AnchorPane) loader.load();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Home.fxml"));
            root = (AnchorPane) loader.load();
        }
        return root;
    }
    
    
         
    @FXML
    public void Inscription(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Registration.fxml"));
        AnchorPane root = (AnchorPane) loader.load();

        connectbutton.getScene().setRoot(root);

    }

    private boolean validateInputs() throws SQLException {

        if (txtUsername.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez saisir votre nom d'utilisateur");
            alert1.setHeaderText("Controle de saisie");
            alert1.show();
            return false;
        }
        if (txtPassword.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez saisir votre mot de passe");
            alert1.setHeaderText("Controle de saisie");
            alert1.show();
            return false;
        }
        return true;
    }

    private void FacebookConnect() throws SQLException, IOException, ParseException {
        String domain = "https://localhost";
        String appId = "344309509416204";
        String appSecret = "ce2ad147b8ac69cb16df8c90c8c62990";

        String authUrl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + appId + "&redirect_uri=" + domain + "&scope=public_profile,user_birthday,email";

        System.setProperty("webdriver.chrome.driver", "chromedriver");

        WebDriver driver = new ChromeDriver();
        driver.get(authUrl);

        System.out.println(driver.getCurrentUrl());
        String accessToken;

        boolean b = true;
        while (b) {
            if (!driver.getCurrentUrl().contains("facebook.com")) {

                String url = driver.getCurrentUrl();
                accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");
                System.out.println("test");

                driver.quit();
                b = false;
                FacebookClient fbClient = new DefaultFacebookClient(accessToken);
                String fields = "name,first_name,last_name,age_range,birthday,email,gender,address";
                User user = fbClient.fetchObject("me", User.class, Parameter.with("fields", fields));
                System.out.println(user.getName());
                System.out.println(user.toString());

                UsersService usr = new UsersService();

                if (usr.getUserByEmail(user.getEmail()) != null) {
                    Users u = usr.getUserByEmail(user.getEmail());
                    Cupcake.user = u;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Home.fxml"));
                    AnchorPane root = (AnchorPane) loader.load();
                    connectbutton.getScene().setRoot(root);

                } else {
                    java.sql.Date sqlDate = null;
                    if (user.getBirthday() != null) {

                        java.util.Date dtJ = new SimpleDateFormat("MM/DD/YYYY").parse(user.getBirthday());
                        sqlDate = new java.sql.Date(dtJ.getTime());
                        Users u = new Users(user.getFirstName().toLowerCase() + user.getLastName().toLowerCase(), user.getEmail(), user.getFirstName().toLowerCase() + user.getLastName().toLowerCase(), sqlDate, "ROLE_USER", user.getFirstName(), user.getLastName(), 0);
                        Cupcake.user = u;
                        usr.AddFbUser(u);
                    } else {
                        Users u = new Users(user.getFirstName().toLowerCase() + user.getLastName().toLowerCase(), user.getEmail(), user.getFirstName().toLowerCase() + user.getLastName().toLowerCase(), sqlDate, "ROLE_USER", user.getFirstName(), user.getLastName(), 0);
                        Cupcake.user = u;
                        usr.AddFbUser(u);
                    }
                    HomeController.afficherprofile = 1;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Home.fxml"));
                    AnchorPane root = (AnchorPane) loader.load();
                    connectbutton.getScene().setRoot(root);

                }

            }
        }

    }

    @FXML
    private void MissedPassword(ActionEvent event) {
        txtEmailRecover.setVisible(true);
        btnsendrecover.setVisible(true);
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    @FXML
    private void Recover(ActionEvent event) throws SQLException {
        if (!txtEmailRecover.getText().isEmpty() && txtEmailRecover.getText().contains("@")) {
            UsersService usr = new UsersService();

            Users user = new Users();
            user = usr.getUserByEmail(txtEmailRecover.getText());
            if (user != null) {
                String plainpassword = getSaltString();
                String password = BCrypt.hashpw(plainpassword, BCrypt.gensalt());
                System.out.println("Le nouveau mot de passe de " + user.getEmail() + "est " + plainpassword);

                usr.changePassword(password, txtEmailRecover.getText());
                sendEmail(user, plainpassword);
                txtEmailRecover.setVisible(false);
                btnsendrecover.setVisible(false);
                txtetat.setText("Mot de passe envoyé par email.");
            } else {
                txtetat.setText("Utilisateur introuvable");
            }

        }
    }

    public void sendEmail(Users compte, String plainpassword) {
        try {
            String host = "smtp.gmail.com";
            String user = "berrahal.ryadh@gmail.com";
            String pass = "Alpha499789";
            String to = "ryadh.berrahal@esprit.tn";
            String from = "berrahal.ryadh@gmail.com";
            String subject = "Cupcake - Nouveau mot de passe";
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};

            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            //msg.setSentDate(new Date());

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText("Bonjour " + compte.getFirstname() + " " + compte.getLastname() + ", votre nouveau mot de passe est : " + plainpassword);
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("Email envoyé");

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void AuthFB(ActionEvent event) throws SQLException, IOException, ParseException {
        try {
            FacebookConnect();
        } catch (ParseException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
