/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import edu.cupcake.entities.Enseigne;
import edu.cupcake.services.EnseigneServices;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class BackBrandController implements Initializable {

    @FXML
    private Button InfoBrandButton;
    @FXML
    private Button BakeryButton;
    @FXML
    private Button map;
    @FXML
    private Button stat;
    @FXML
    private ImageView background;
    @FXML
    private AnchorPane content;
    
    public static int informations=0;
        public static int patisseries=0;
    public static int localisation=0;
        public static int stats=0;
        public static int updatebrand=0;
        public static int showProd=0;


EnseigneServices es = new EnseigneServices();
    Enseigne en;
    @FXML
    private Button productManagementBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
                en=es.getEnseignebyUserId(cupcake.Cupcake.user.getId());

                background.setImage(new Image("http://" +en.getLogo()));

        
        if (informations!=0) {
            AnchorPane pane1;
           try {
               pane1 = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/InfoBrand.fxml"));
                               content.getChildren().setAll(pane1);

           } catch (IOException ex) {
               Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
           }
           informations=0;
        }
        if (patisseries!=0) {
            AnchorPane pane1;
           try {
               pane1 = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/InfoBakery.fxml"));
                               content.getChildren().setAll(pane1);

           } catch (IOException ex) {
               Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
           }
           patisseries=0;
        }
        
        if (localisation!=0) {
            AnchorPane pane1;
           try {
               pane1 = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/Localisation.fxml"));
                               content.getChildren().setAll(pane1);

           } catch (IOException ex) {
               Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
           }
           localisation=0;
        }
        
        if (showProd!=0) {
            AnchorPane pane1;
           try {
               pane1 = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/BrandManagement.fxml"));
                               content.getChildren().setAll(pane1);

           } catch (IOException ex) {
               Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
           }
           showProd=0;
        }
        
        if (updatebrand!=0) {
            AnchorPane pane1;
           try {
               pane1 = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/UpdateBrand.fxml"));
                               content.getChildren().setAll(pane1);

           } catch (IOException ex) {
               Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
           }
           updatebrand=0;
        }
    }    

    @FXML
    private void ShowBrand(ActionEvent event) throws IOException {
        AnchorPane pane=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/InfoBrand.fxml"));

                content.getChildren().setAll(pane);
    }

    @FXML
    private void ShowBakery(ActionEvent event) throws IOException {
         AnchorPane pane=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/InfoBakery.fxml"));

                content.getChildren().setAll(pane);
    }

    @FXML
    private void localisation(ActionEvent event) throws IOException {
        AnchorPane pane=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/Localisation.fxml"));

                content.getChildren().setAll(pane);
    }

    @FXML
    private void statistiques(ActionEvent event) throws IOException {
        AnchorPane pane=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/Statistiques.fxml"));

                content.getChildren().setAll(pane);
    }

    @FXML
    private void showProductManagement(ActionEvent event) throws IOException {
         AnchorPane pane=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/BrandManagement.fxml"));

                content.getChildren().setAll(pane);

    }
    
}
