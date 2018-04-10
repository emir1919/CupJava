/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import edu.cupcake.entities.Events;
import edu.cupcake.entities.UserEvent;
import edu.cupcake.services.EventsService;
import edu.cupcake.services.ShareService;
import edu.cupcake.services.UserEventService;
import edu.cupcake.utils.Routes;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class ShowEventsFrontController implements Initializable {
    
    @FXML
    private AnchorPane Type;

    @FXML
    private AnchorPane test;

    @FXML
    private ImageView ImgEvent;

    @FXML
    private JFXButton BTparticiper;

    @FXML
    private JFXButton BTPartager;

    @FXML
    private JFXButton BTDescription;

    @FXML
    private Label txtTitre;

    @FXML
    private Label txtDate;

    @FXML
    private Label txtAdresse;
    
    @FXML
    private Label txtNBP;

    @FXML
    private Label NB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        EventsService es = new EventsService();
        Events e = new Events();
        try {
            e = es.FindById(cupcake.Cupcake.Selected_id);
        } catch (SQLException ex) {
            Logger.getLogger(ShowEventsFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtTitre.setText(e.getTitle());
        txtAdresse.setText(e.getAdress());
        
        if(e.getType().equals("Custom")){
            txtNBP.setVisible(false);
            NB.setVisible(false);
        }
        else{
            NB.setText(Integer.toString(e.getNbPlaces() - e.getNbPart()));
        }
        
        Format formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter1.format(e.getDateStart()); 
        txtDate.setText(s);
        String desc = e.getDescription();
        
        try {
                File file = new File(e.getImage());
                Image image = new Image(file.toURI().toURL().toExternalForm());
                ImgEvent.setImage(image);
            } catch (MalformedURLException ex) {System.out.println(ex);} 
        
        
        BTDescription.setOnAction(
        new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Description");
            alert1.setContentText(desc);
            alert1.setHeaderText(null);
            alert1.show();
            }
         });
        
        
        
    }   
    



    @FXML
    private void PartagerAction(ActionEvent event) throws IOException{
        
        ShareService ss = new ShareService();
        ss.partager();

    }

    @FXML
    private void ParticiperAction(ActionEvent event) throws SQLException {
        
        if(cupcake.Cupcake.user_id == 0){
            
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Accée réfusée");
            alert1.setContentText("Vous devez étre inscrit pour participer à cet événement");
            alert1.setHeaderText(null);
            alert1.show();
            
        }
        else{
            
             EventsService es = new EventsService();
             Events e = new Events();
             try {
                e = es.FindById(cupcake.Cupcake.Selected_id);
                } catch (SQLException ex) { Logger.getLogger(ShowEventsFrontController.class.getName()).log(Level.SEVERE, null, ex); }
             
             if(e.getType().equals("Custom")){
                 
                 if(!exist()){
                     
                     UserEventService uvs = new UserEventService();
                     UserEvent uv = new UserEvent(false, 0,cupcake.Cupcake.Selected_id,cupcake.Cupcake.user_id);
                     uvs.AddUV(uv);
                     
                     e.setNbPart(e.getNbPart()+1);
                     es.EditEvent(e,cupcake.Cupcake.Selected_id);
                     
                     cupcake.Cupcake.Selected_id = uvs.FindIdByObject(uv);
                     
                    Parent root = null;
                     try {
                         
                         root = FXMLLoader.load(getClass().getResource(Routes.ParticipateEventVIEW));
                         
                     } catch (IOException ex) {
                         Logger.getLogger(ShowEventsFrontController.class.getName()).log(Level.SEVERE, null, ex);
                     }
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                     
                     
                 }
                 else{
                     
                     Alert alert1 = new Alert(Alert.AlertType.WARNING);
                     alert1.setTitle("Accée réfusée");
                     alert1.setContentText("Vous avez déjà participé à cet événement!");
                     alert1.setHeaderText(null);
                     alert1.show();
                     
                 }
                 
             }
             else 
             if (e.getType().equals("places")) {
                 
                 if( (e.getNbPart()) < (e.getNbPlaces()) ){
                     
                     if(!exist()){
                         
                     UserEventService uvs = new UserEventService();
                     UserEvent uv = new UserEvent(false, 0,cupcake.Cupcake.Selected_id,cupcake.Cupcake.user_id);
                     uvs.AddUV(uv);
                     
                     e.setNbPart(e.getNbPart()+1);
                     es.EditEvent(e,cupcake.Cupcake.Selected_id);
                     
                     cupcake.Cupcake.Selected_id = uvs.FindIdByObject(uv);
                     
                    Parent root = null;
                     try {
                         
                         root = FXMLLoader.load(getClass().getResource(Routes.ParticipateEventVIEW));
                         
                     } catch (IOException ex) {
                         Logger.getLogger(ShowEventsFrontController.class.getName()).log(Level.SEVERE, null, ex);
                     }
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                     }
                     else{
                         
                     Alert alert1 = new Alert(Alert.AlertType.WARNING);
                     alert1.setTitle("Accée réfusée");
                     alert1.setContentText("Vous avez déjà participé à cet événement!");
                     alert1.setHeaderText(null);
                     alert1.show();
                         
                     }
                     
                     
                 }
                 else {
                     Alert alert1 = new Alert(Alert.AlertType.WARNING);
                     alert1.setTitle("Accée réfusée");
                     alert1.setContentText("Nous sommes désolé il ya plus de places !");
                     alert1.setHeaderText(null);
                     alert1.show();  
                 }
                 
             } 
                 
            
            
        }

    }
    
    public boolean exist(){
        UserEventService uvs = new UserEventService();
        List<UserEvent> listUV = new ArrayList<>();
        listUV = uvs.FindByUserID(cupcake.Cupcake.user_id);
        
        if(listUV != null)
            {
                for(UserEvent uv : listUV)
                {
                        if(uv.getEventId() == cupcake.Cupcake.Selected_id)
                           return true;
                }
            }
            return false;
        
    }
    
    

    
    
    

    

    
}
