/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.cupcake.entities.Users;
import edu.cupcake.services.UsersService;
import edu.cupcake.utils.Routes;
import java.io.IOException;
import java.net.URL;
//import java.sql.Date;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class ProfileController implements Initializable {

       ObservableList<String> RoleList = FXCollections.observableArrayList("ROLE_USER", "ROLE_ADMIN");

    
     @FXML
    private AnchorPane profilecontent;
     
     public static int afficherprofile=0;
     public static int afficheradresses=0;
     public static int affichercommandes=0;
             public static int convertirpf=0;

     
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
       

       
        if (afficherprofile!=0) {
            AnchorPane pane1;
           try {
               pane1 = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/EditProfile.fxml"));
                               profilecontent.getChildren().setAll(pane1);

           } catch (IOException ex) {
               Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
           }
           afficherprofile=0;
        }
        
        if (afficheradresses!=0) {
            AnchorPane pane1;
           try {
               pane1 = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/ShowAdresses.fxml"));
                               profilecontent.getChildren().setAll(pane1);

           } catch (IOException ex) {
               Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
           }
           afficheradresses=0;
        }
        
        if (affichercommandes!=0) {
            AnchorPane pane1;
           try {
               pane1 = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/UserShowOrders.fxml"));
                               profilecontent.getChildren().setAll(pane1);

           } catch (IOException ex) {
               Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
           }
           affichercommandes=0;
        }
        
         if (convertirpf!=0) {
            AnchorPane pane1;
           try {
               pane1 = FXMLLoader.load(getClass().getResource(Routes.ConvertView));
                               profilecontent.getChildren().setAll(pane1);

           } catch (IOException ex) {
               Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
           }
           convertirpf=0;
        }
        
        
        
        



       
    }    
    
   @FXML
    public void Afficherprofile(ActionEvent event) throws IOException {

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Login.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
       
        menupane.getScene().setRoot(root);*/
        AnchorPane pane=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/Profile.fxml"));
                AnchorPane pane1=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/EditProfile.fxml"));

                profilecontent.getChildren().setAll(pane1);

        
        

    } 
   @FXML
    public void showadresses(ActionEvent event) throws IOException {

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Login.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
       
        menupane.getScene().setRoot(root);*/
       //AnchorPane pane=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/Profile.fxml"));
                AnchorPane pane1=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/ShowAdresses.fxml"));

                profilecontent.getChildren().setAll(pane1);
                
                System.out.println("ssss");

        
        

    } 
    
    @FXML
    public void affichercommandes(ActionEvent event) throws IOException {

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Login.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
       
        menupane.getScene().setRoot(root);*/
       //AnchorPane pane=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/Profile.fxml"));
                AnchorPane pane1=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/UserShowOrders.fxml"));

                profilecontent.getChildren().setAll(pane1);
                

        
        

    } 

    @FXML
    private void ShowMesPoints(ActionEvent event) throws IOException {
        AnchorPane pane1=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/MesPoints.fxml"));

                profilecontent.getChildren().setAll(pane1);
    }
    
}
