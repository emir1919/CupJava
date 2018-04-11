/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import edu.cupcake.entities.Adds;
import edu.cupcake.entities.Enseigne;
import edu.cupcake.entities.Events;
import edu.cupcake.services.AddsService;
import edu.cupcake.services.EnseigneServices;
import edu.cupcake.services.EventsService;
import edu.cupcake.services.TypeClaimService;
import edu.cupcake.utils.Routes;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class AddEventsController implements Initializable {
    
    @FXML
    private ComboBox TypeEvent;

    @FXML
    private ImageView logo;

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField Adresse;

    @FXML
    private JFXButton BtnAjouter;

    @FXML
    private JFXButton btnImportImage;
    String path = "";

    @FXML
    private JFXTextArea description;

    @FXML
    private ImageView imageViewEvent;

    @FXML
    private JFXTextField NBP;

    @FXML
    private JFXDatePicker txtDate;

    @FXML
    private JFXButton GE;

    @FXML
    private JFXButton CR;
    
    @FXML
    private JFXButton LP;
    
    @FXML
    private JFXButton CP;
    
    List listTypes;
    ObservableList options = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FillType();
    }    
    

    @FXML
    private void addEvent(ActionEvent event) {
        
        EventsService evs = new EventsService();
        
        if(validateInputs()){
            
            
            EnseigneServices es = new EnseigneServices();
            Enseigne ens = new Enseigne();
            
            ens = es.getEnseignebyId(cupcake.Cupcake.user_id);
            int id_ens = ens.getId();
            
            Date date = Date.valueOf(txtDate.getValue());
            String te =  (String) TypeEvent.getSelectionModel().getSelectedItem();
            
            String t =  (String) TypeEvent.getSelectionModel().getSelectedItem();
            int nb=0;

            if("Places".equals(t)){
                nb = Integer.parseInt(NBP.getText());
            }
            
            Events e = new Events(date, title.getText(), description.getText(), path,te,Adresse.getText(),nb,0,id_ens);
            evs.AddEvent(e);
            
            
            String tit = "Opération réussie";
            String message = "L'évenement a été ajoutée avec succée";
            NotificationType notification = NotificationType.SUCCESS;
    
            TrayNotification tray = new TrayNotification(tit, message, notification);          
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(2));
            
        }

    }

    @FXML
    private void importImage(ActionEvent event) throws MalformedURLException {
        
                BufferedOutputStream stream = null;
	String globalPath="C:\\wamp\\www\\cupcake";
        
        
        try {
        
        JFileChooser fileChooser = new JFileChooser(); 
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg", "gif", "png");
        fileChooser.addChoosableFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {         
            
            File selectedFile = fileChooser.getSelectedFile();
            path = selectedFile.getName();
            
            Path p = selectedFile.toPath();      
            byte[] bytes = Files.readAllBytes(p); 
            File dir = new File(globalPath);
            
            File serverFile = new File(dir.getAbsolutePath()+File.separator + path);
            stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
            
            
            String path2 = selectedFile.toURI().toURL().toString();
            Image image = new Image(path2);
            imageViewEvent.setImage(image);

        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("NoData");
        }
        
                } catch (IOException ex) {
            Logger.getLogger(AddAddsController.class.getName()).log(Level.SEVERE, null, ex);}
        

        

    }
    @FXML
    private void comboAction(ActionEvent event) {
        
      String t = (String) TypeEvent.getValue();

      if ("Custom".equals(t) ){
          NBP.setVisible(false);
      }
      else{
          NBP.setVisible(true);
      }

    }
    
    private boolean validateInputs() {
        
        String t =  (String) TypeEvent.getSelectionModel().getSelectedItem();

        if ((description.getText().isEmpty()) || (Adresse.getText().isEmpty()) || ("".equals(path)) || (title.getText().isEmpty()) || (t == null) || (txtDate.getValue() == null)  ) {
            
                     
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Vous devez remplir tous les champs");
            alert1.setHeaderText(null);
            alert1.show();

             return false;

        } 
        else if ( ("Places".equals(t)) && (NBP.getText().isEmpty()) ) {
            
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Vous devez remplir le champ nombre de places");
            alert1.setHeaderText(null);
            alert1.show();

             return false;
        }
        
        else if ( ("Places".equals(t)) && (isNotInteger(NBP.getText())) ) {

            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("erreur dans le champ nombre de places veiller mettre un numero");
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
    
    private void FillType() {
           
            listTypes = new ArrayList<>();
            listTypes.add("Places");
            listTypes.add("Custom");
            options = FXCollections.observableArrayList(listTypes);
            TypeEvent.setItems(options);

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
    
    @FXML
    private void ClaimsPage(ActionEvent event) throws IOException {
        
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
    
}
