/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.gui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.CupTest2.entities.Users;
import edu.CupTest2.services.UsersService;
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
//import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class ProfileController implements Initializable {

       ObservableList<String> RoleList = FXCollections.observableArrayList("ROLE_USER", "ROLE_ADMIN");

    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTel;
    @FXML
    private DatePicker txtDate;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXTextField txtPseudo;
    @FXML
    private Button btnInscrire;
    @FXML
    private RadioButton radioHomme;
    @FXML
    private RadioButton radioFemme;
    @FXML
    private TextField txtAdresse;
    @FXML

    private TextField txtCfPassword;
    @FXML
    private VBox VBoxInfoPersonel;
    @FXML
    private ToggleGroup gender;
    @FXML
    private VBox VBoxMdp;

    @FXML
    private JFXComboBox Rolecombo;
    
    @FXML
    private Label Role;
    
     @FXML
    private AnchorPane profilecontent;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
       

       

        AnchorPane pane1;
           try {
               pane1 = FXMLLoader.load(getClass().getResource("/edu/CupTest2/gui/EditProfile.fxml"));
                               profilecontent.getChildren().setAll(pane1);

           } catch (IOException ex) {
               Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
           }



       
    }    
    
   @FXML
    public void Afficherprofile(ActionEvent event) throws IOException {

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Login.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
       
        menupane.getScene().setRoot(root);*/
        AnchorPane pane=FXMLLoader.load(getClass().getResource("/edu/CupTest2/gui/Profile.fxml"));
                AnchorPane pane1=FXMLLoader.load(getClass().getResource("/edu/CupTest2/gui/EditProfile.fxml"));

                profilecontent.getChildren().setAll(pane1);

        
        

    } 
   @FXML
    public void showadresses(ActionEvent event) throws IOException {

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Login.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
       
        menupane.getScene().setRoot(root);*/
       //AnchorPane pane=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/Profile.fxml"));
                AnchorPane pane1=FXMLLoader.load(getClass().getResource("/edu/CupTest2/gui/ShowAdresses.fxml"));

                profilecontent.getChildren().setAll(pane1);
                
                System.out.println("ssss");

        
        

    } 
    
}
