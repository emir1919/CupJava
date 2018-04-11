/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import edu.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class ErreurScanController implements Initializable {
    
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
    private JFXButton BTNErreur;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void GestionEvents(ActionEvent event) throws IOException {
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(Routes.ShoweventsBrandVIEW));
               
        Scene scene = new Scene(root);
   
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    private void Refresh(ActionEvent event) throws IOException {
        
           BackBrandController.listevconf = 1;
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/BackBrand.fxml"));
           AnchorPane root = (AnchorPane) loader.load();
           Type.getScene().setRoot(root);
        

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
