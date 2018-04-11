/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import static edu.cupcake.controllers.ProfileController.afficherprofile;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class MaCommandeController implements Initializable {

    
     public static int validationpanier=0;
     public static int validationlivraison=0;
     public static int paiement=0;
    @FXML
    private ImageView imgLivraison;
    @FXML
    private ImageView imgValidationPanier;
    @FXML
    private ImageView imgPaiement;
    @FXML
    private AnchorPane CommandeContent;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       imgLivraison.setVisible(false);
       imgValidationPanier.setVisible(false);
       imgPaiement.setVisible(false);
       
       if (validationpanier!=0) {
           imgValidationPanier.setVisible(true);
           AnchorPane pane1;
           try {
               pane1 = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/ValidationPanier.fxml"));
                               CommandeContent.getChildren().setAll(pane1);

           } catch (IOException ex) {
               Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
           }
           validationpanier=0;
        }
       
       if (paiement!=0) {
           imgPaiement.setVisible(true);
           AnchorPane pane1;
           try {
               pane1 = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/CommandePaiement.fxml"));
                               CommandeContent.getChildren().setAll(pane1);

           } catch (IOException ex) {
               Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
           }
           paiement=0;
        }
       
       if (validationlivraison!=0) {
           imgLivraison.setVisible(true);
           AnchorPane pane1;
           try {
               pane1 = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/ValidationLivraisonCommande.fxml"));
                               CommandeContent.getChildren().setAll(pane1);

           } catch (IOException ex) {
               Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
           }
           validationlivraison=0;
        }
    }    

    
}
