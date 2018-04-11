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
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.AnchorPane;
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
import edu.cupcake.utils.Routes;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class EditEventsController implements Initializable {
    
    @FXML
    private AnchorPane Type;

    @FXML
    private ImageView logo;

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField Adresse;

    @FXML
    private JFXButton BtnModifier;

    @FXML
    private JFXButton btnImportImage;
    String path = "";

    @FXML
    private JFXTextArea description;

    @FXML
    private ImageView imageViewEvent;

    @FXML
    private ComboBox TypeEvent;

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
            
        if(cupcake.Cupcake.Selected_id != 0) {
            
            EventsService es = new EventsService();
            Events e = new Events();
        
            
                try {
                    e = es.FindById(cupcake.Cupcake.Selected_id);
                    } catch (SQLException ex) {Logger.getLogger(EditAddsController.class.getName()).log(Level.SEVERE, null, ex);}
                
            title.setText(e.getTitle());
            Adresse.setText(e.getAdress());
            NBP.setText(Integer.toString(e.getNbPlaces()));
            description.setText(e.getDescription());
            
            Date dt = new Date();
            dt = e.getDateStart();
            
            Format formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            String s = formatter1.format(dt); 
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");          
            LocalDate localDate = LocalDate.parse(s, formatter);
                        
            txtDate.setValue(localDate);
            TypeEvent.setValue(e.getType());
            
            
            String t = (String) TypeEvent.getValue();

            if ("Custom".equals(t) ){
            NBP.setVisible(false);
            }
            else{
                
                if(e.getNbPlaces() == -1){
                    NBP.setText("");
                }
                else{
                    NBP.setText(Integer.toString(e.getNbPlaces()));
                }
                    
            NBP.setVisible(true);
            
            }
            
            File file = new File(e.getImage());
            path = file.getName();
            Image image = new Image("http://localhost/cupcake/" +e.getImage());
            imageViewEvent.setImage(image);


        }
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

    @FXML
    private void EditEvent(ActionEvent event) throws SQLException {
        
        EventsService evs = new EventsService();
        
        if(validateInputs()){
            
            
            EnseigneServices es = new EnseigneServices();
            Enseigne ens = new Enseigne();
            
            ens = es.getEnseignebyUserId(cupcake.Cupcake.user.getId());
            int id_ens = ens.getId();
            
            java.sql.Date date = java.sql.Date.valueOf(txtDate.getValue());
            String te =  (String) TypeEvent.getSelectionModel().getSelectedItem();
            
            String t =  (String) TypeEvent.getSelectionModel().getSelectedItem();
            int nb=-1;

            if("Places".equals(t)){
                nb = Integer.parseInt(NBP.getText());
            }
            
            Events e = new Events(date, title.getText(), description.getText(), path,te,Adresse.getText(),nb,0,id_ens);
            evs.EditEvent(e,cupcake.Cupcake.Selected_id);
            
            
            String tit = "Opération réussie";
            String message = "L'évenement a été ajoutée avec succée";
            NotificationType notification = NotificationType.SUCCESS;
    
            TrayNotification tray = new TrayNotification(tit, message, notification);          
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(2));
            
        }
        
    }

    @FXML
    private void comboAction(ActionEvent event) {
      String t = (String) TypeEvent.getValue();

      if ("Custom".equals(t) ){
          NBP.setVisible(false);
      }
      else{
          
         if("-1".equals(NBP.getText())){
              NBP.setText("");
          }
          
          NBP.setVisible(true);
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
    
    private void FillType() {
           
            listTypes = new ArrayList<>();
            listTypes.add("Places");
            listTypes.add("Custom");
            options = FXCollections.observableArrayList(listTypes);
            TypeEvent.setItems(options);

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
