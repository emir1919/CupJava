/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import edu.cupcake.entities.Events;
import edu.cupcake.entities.Users;
import edu.cupcake.utils.Connexion;
import edu.cupcake.utils.Routes;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class ConfirmationController implements Initializable {
    
    @FXML
    private AnchorPane Type;

    @FXML
    private ImageView logo;

    @FXML
    private JFXButton GE;

    @FXML
    private JFXButton CR;
    
    @FXML
    private JFXButton LP;
    
    @FXML
    private JFXButton CP;

    @FXML
    private ImageView ImageViewUser;

    @FXML
    private Label txtName;

    @FXML
    private Label txtPrenom;

    @FXML
    private Label txtCIN;

    @FXML
    private Label txtEmail;

    @FXML
    private Label txtPhone;
    
    @FXML
    private JFXButton BTNConfirmer;
    
    @FXML
    private AnchorPane Pane;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Users u = new Users();
        try {
            u = FindScanned();
        } catch (SQLException ex) {
            Logger.getLogger(ConfirmationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(u.toString());
        
        if(u.getId() == 0){
            

           
        
        }
        else {
            
 
            txtCIN.setText(Integer.toString(u.getCIN()));
            txtEmail.setText(u.getEmail());
            txtName.setText(u.getLastname());
            txtPhone.setText(Long.toString(u.getPhonenumber()));
            txtPrenom.setText(u.getFirstname());
        
            try {
                File file = new File(u.getImage());
                Image image = new Image(file.toURI().toURL().toExternalForm());
                ImageViewUser.setImage(image);
            } catch (MalformedURLException ex) {
                System.out.println(ex);
            }    
        
        }
    }  
    
    @FXML
    void ClaimsPage(ActionEvent event) throws IOException {
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(Routes.ShowClaimsBrandVIEW));
               
        Scene scene = new Scene(root);
   
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void GestionEvents(ActionEvent event) throws IOException {
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(Routes.ShoweventsBrandVIEW));
               
        Scene scene = new Scene(root);
   
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();

    }
    
    public Users FindScanned() throws SQLException {
        
     Connection cn = Connexion.getInstance().getConnection();
        Users u = new Users();

        String req = "SELECT * FROM USERS u where u.id = (SELECT user_id FROM `user_event` WHERE  scanned = 1 and event_id = ?)";
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1,cupcake.Cupcake.Selected_id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            
                u.setId(rs.getInt("id"));
                u.setFirstname(rs.getString("FirstName"));
                u.setLastname(rs.getString("LastName"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setPhonenumber(rs.getInt("PhoneNumber"));
                u.setCIN(rs.getInt("CIN"));
                u.setImage(rs.getString("image_name"));

        }

        return u;
    }
    
    @FXML
    private void Confirmer(ActionEvent event) throws SQLException {
        
     Connection cn = Connexion.getInstance().getConnection();
        Users u = new Users();
        
        
        String req = "UPDATE user_event SET scanned = 2 WHERE scanned = 1";
        PreparedStatement st = cn.prepareStatement(req);
        st.executeUpdate(); 
        
        BTNConfirmer.setVisible(false);
        
            String tit = "Opération réussie";
            String message = "Participation Confirmé";
            NotificationType notification = NotificationType.SUCCESS;
    
            TrayNotification tray = new TrayNotification(tit, message, notification);          
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(2));
        
    }
    
    @FXML
    private void GoLP(ActionEvent event) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(Routes.ListeEventsPRVIEW));
               
        Scene scene = new Scene(root);
   
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();  
    }

    @FXML
    private void GoCP(ActionEvent event) throws IOException {
    
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(Routes.ListEventsVIEW));
               
        Scene scene = new Scene(root);
   
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        
    }


    
}
