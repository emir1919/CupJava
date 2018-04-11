/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import edu.cupcake.entities.Adds;
import edu.cupcake.services.AddsService;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;
import edu.cupcake.entities.Enseigne;
import edu.cupcake.services.EnseigneServices;
import edu.cupcake.utils.Routes;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import tray.notification.TrayNotification;
import tray.notification.NotificationType;
import tray.animations.AnimationType;

/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class AddAddsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField title;

    @FXML
    private Button btnImportImage;
    String path = "";

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXTextField rank;

    @FXML
    public Button BtnAjouter;
    
    @FXML
    private ImageView imageViewAdd;
     
    @FXML
    private JFXButton GP;
    
    @FXML
    private JFXButton CR;
    
    @FXML
    private ComboBox Ens;
    
    List listEns;
    ObservableList options = FXCollections.observableArrayList();

    @FXML
    private void addAdds(ActionEvent e) {
        
        AddsService as = new AddsService();
        
        if(validateInputs()){
            
                       
             Enseigne ens = new Enseigne();
             String c2 = (String) Ens.getSelectionModel().getSelectedItem();
             EnseigneServices es = new EnseigneServices();
             int id_ens = es.getEnseignebyName(c2).getId();

            Adds a = new Adds(id_ens, title.getText(), path, description.getText(), Integer.parseInt(rank.getText()));

            as.AddAdds(a); 
            
            String tit = "Opération réussie";
            String message = "La publicité a été ajoutée avec succée";
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
            imageViewAdd.setImage(image);

        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("NoData");
        }
        
                } catch (IOException ex) {
            Logger.getLogger(AddAddsController.class.getName()).log(Level.SEVERE, null, ex);}

    }

    private boolean validateInputs() {

                String e = (String) Ens.getSelectionModel().getSelectedItem();


        if ((description.getText().isEmpty()) || (rank.getText().isEmpty()) || ("".equals(path)) || (title.getText().isEmpty()) || (e == null) ) {
            
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Vous devez remplir tous les champs et choisir l'enseigne !");
            alert1.setHeaderText(null);
            alert1.show();

             return false;

        } 
        
        else if (isNotInteger(rank.getText())) {

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
    
    @FXML
    private void GestionAdds(ActionEvent event) throws IOException{
        
        Parent root=null;
        root = FXMLLoader.load(getClass().getResource(Routes.ShowAddsVIEW));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }
    
    @FXML
    private void ClaimsPage(ActionEvent event) throws IOException {
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(Routes.ShowClaimsAdminVIEW));
               
        Scene scene = new Scene(root);
   
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();   
        
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        FillEns();

        
    }

}
