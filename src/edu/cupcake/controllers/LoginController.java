/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import edu.cupcake.utils.BCrypt;
import edu.cupcake.utils.Routing;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.management.Notification;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cupcake.Cupcake.lastroute = cupcake.Cupcake.currentroute;
        cupcake.Cupcake.currentroute = Routing.LOGIN;

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
            if (u != null && u.getEnabled()==1 && BCrypt.checkpw(txtPassword.getText(), u.getPassword())) {
                Cupcake.user = u;
                
                System.out.println("sooooo nice");
                txtetat.setText("");

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Succès");
                alert.setHeaderText("Authentifié");
                alert.setContentText("Vous êtes connecté en tant que :" + u.getUsername());
               
                alert.showAndWait();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Home.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
       
        txtPassword.getScene().setRoot(root);

            }
            else if (u != null && u.getEnabled()==0)
            {
             txtetat.setText("Votre compte est desactivé.");
             

            }
            else
            {
               txtetat.setText("Identifiants incorrects.");
            }

        }
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

}
