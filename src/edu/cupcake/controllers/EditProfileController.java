/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import static edu.cupcake.controllers.RegistrationController.isInteger;
import static edu.cupcake.controllers.RegistrationController.validate;
import edu.cupcake.entities.Users;
import edu.cupcake.services.UsersService;
import edu.cupcake.utils.BCrypt;
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
import javafx.scene.control.Alert;
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

             if (validateInputs()) {
                 UsersService sr = new UsersService();

                java.sql.Date birthday = java.sql.Date.valueOf(txtDate.getValue());
                Users user=new Users(txtPseudo.getText(), txtEmail.getText(), BCrypt.hashpw(txtPassword.getText(), BCrypt.gensalt()), birthday, cupcake.Cupcake.user.getRoles(), txtNom.getText(), txtPrenom.getText(), Long.parseLong(txtTel.getText()));
                sr.modifierClient(user, cupcake.Cupcake.user.getId());
        
       cupcake.Cupcake.user=sr.getUserById(cupcake.Cupcake.user.getId());
                 
             }
                

    } 
    
    private boolean validateInputs() {
        if ((txtNom.getText().isEmpty()) || (txtPrenom.getText().isEmpty())
                || (txtEmail.getText().isEmpty()) || (txtTel.getText().isEmpty())
                || (txtDate.getValue() == null) || (txtPseudo.getText().isEmpty()) || (txtPassword.getText().isEmpty())
                || ((!radioFemme.isSelected()) && !(radioHomme.isSelected()))) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez remplir tout les champs");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        } else if (!(txtCfPassword.getText().equals(txtPassword.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veillez vérifier votre mot de passe");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        } else if (!(validate(txtEmail.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veillez vérifier votre email");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        } else if ((txtTel.getText().trim().length() > 8) || ((txtTel.getText().trim().length() < 8)) || (!isInteger(txtTel.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veillez vérifier votre numéro de téléphone");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        }
        else if (!BCrypt.checkpw(txtPassword.getText(), cupcake.Cupcake.user.getPassword())) {
           Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Mot de passe incorrect");
            alert2.setHeaderText(null);
            alert2.show();
            return false; 
        }
        return true;
    }
    
}
