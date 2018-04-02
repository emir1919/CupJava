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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class EditProfileController implements Initializable {

    
    
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         txtNom.setText(cupcake.Cupcake.user.getFirstname());
       txtPrenom.setText(cupcake.Cupcake.user.getLastname());
       txtEmail.setText(cupcake.Cupcake.user.getEmail());
       txtTel.setText(""+cupcake.Cupcake.user.getPhonenumber());
       txtDate.setPromptText(""+cupcake.Cupcake.user.getBirthday());
       txtPseudo.setText(cupcake.Cupcake.user.getUsername());
       Role.setText(cupcake.Cupcake.user.getRoles());
                   
       Date dt = new Date() ;
       if (cupcake.Cupcake.user.getBirthday() != null)
       {
       dt=cupcake.Cupcake.user.getBirthday();
       }
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String foo=formatter.format(dt);
        
        DateTimeFormatter formatter1= DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localdate= LocalDate.parse(foo, formatter1);

       txtDate.setValue(localdate);
       

       

       


    }  
    
         @FXML
    public void modifieruser(ActionEvent event) throws IOException, SQLException {

                UsersService sr = new UsersService();

                java.sql.Date birthday = java.sql.Date.valueOf(txtDate.getValue());
                Users user=new Users(txtPseudo.getText(), txtEmail.getText(), txtPassword.getText(), birthday, cupcake.Cupcake.user.getRoles(), txtNom.getText(), txtPrenom.getText(), Long.parseLong(txtTel.getText()));
                sr.modifierClient(user, cupcake.Cupcake.user.getId());
        
       cupcake.Cupcake.user=sr.getUserById(cupcake.Cupcake.user.getId());

    } 
    
}
