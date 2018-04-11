/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.util.Duration;
import edu.cupcake.entities.Claims;
import edu.cupcake.entities.Enseigne;
import edu.cupcake.entities.TypeClaim;
import edu.cupcake.services.ClaimsService;
import edu.cupcake.services.EnseigneServices;
import edu.cupcake.services.TypeClaimService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class AddClaimsController implements Initializable {
    
    @FXML
    private JFXTextField Name;

    @FXML
    private JFXTextField Email;

    @FXML
    private JFXButton BtnAjouter;

    @FXML
    private JFXTextArea Description;

    @FXML
    private JFXTextField Phone;

    @FXML
    private ComboBox Type;

    @FXML
    private ComboBox Ens;
    
    List listTypes;
    ObservableList options = FXCollections.observableArrayList();
    
    List listEns;
    ObservableList options2 = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        FillType();
        FillEns();
       
    }    
    

    @FXML
    private void addClaim(ActionEvent event) {
        
        ClaimsService cs = new ClaimsService();
        TypeClaimService tcs = new TypeClaimService();
        EnseigneServices es = new EnseigneServices();
        
        if(validateInputs()){
                      
           TypeClaim tc = new TypeClaim();
           String c1 =  (String) Type.getSelectionModel().getSelectedItem();
           int id_type = tcs.FindByName(c1).getId();
           
           Enseigne ens = new Enseigne();
           String c2 = (String) Ens.getSelectionModel().getSelectedItem();
           int id_ens = es.getEnseignebyName(c2).getId();

           SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
           Date d = new Date();
                 
           Date dd = new java.sql.Date(d.getTime());
           
           Claims clm = new Claims((java.sql.Date)dd,Description.getText(),Name.getText(),Email.getText(),Integer.parseInt(Phone.getText()), id_type, id_ens);
           cs.AddClaim(clm);
           
           String tit = "Opération réussie";
           String message = "Réclamation a été ajoutée avec succée";
           NotificationType notification = NotificationType.SUCCESS;
    
           TrayNotification tray = new TrayNotification(tit, message, notification);          
           tray.setAnimationType(AnimationType.POPUP);
           tray.showAndDismiss(Duration.seconds(2));
            
        }

    }
    
    private void FillType() {
        try {
            
            TypeClaimService tcs = new TypeClaimService();
            listTypes = new ArrayList<>();
            listTypes = tcs.GetNameTypes();
            options = FXCollections.observableArrayList(listTypes);
            Type.setItems(options);
            
         
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    private void FillEns() {
        try {
            
            EnseigneServices es = new EnseigneServices();
            listEns = new ArrayList<>();
            listEns = es.GetNameENS();
            options = FXCollections.observableArrayList(listEns);
            Ens.setItems(options);
                  
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    private boolean validateInputs() {
        
        String t =  (String) Type.getSelectionModel().getSelectedItem();
        String e = (String) Ens.getSelectionModel().getSelectedItem();
        
        if ((Name.getText().isEmpty()) || (Email.getText().isEmpty()) || (Phone.getText().isEmpty()) || (Description.getText().isEmpty()) || (t == null) || (e== null)  ) {
            
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Vous devez remplir tous les champs et choisir le type et l'enseigne !");
            alert1.setHeaderText(null);
            alert1.show();

             return false;

        } 
        
        else if (isNotInteger(Phone.getText())) {

            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("erreur dans le champ rang veiller mettre un numero");
            alert1.setHeaderText(null);
            alert1.show();
            return false;

        } 

        return true;

    }
    
    public static boolean isNotInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return true;
        }

        return false;
    }
    
    
    
}
