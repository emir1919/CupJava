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
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import edu.cupcake.entities.Adds;
import edu.cupcake.entities.Enseigne;
import edu.cupcake.services.AddsService;
import edu.cupcake.services.EnseigneServices;
import edu.cupcake.utils.Routes;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import tray.notification.TrayNotification;
import tray.notification.NotificationType;
import tray.animations.AnimationType;

/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class EditAddsController implements Initializable {
    
    @FXML
    private ImageView logo;

    @FXML
    private JFXButton GP;

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField rank;

    @FXML
    private JFXButton BtnModifier;

    @FXML
    private JFXButton btnImportImage;
    String path = "";

    @FXML
    private JFXTextArea description;

    @FXML
    private ImageView imageViewAdd;
    
    @FXML
    private JFXButton CR;
    
    
    
        /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(cupcake.Cupcake.Selected_id != 0) {
        
            AddsService as = new AddsService();
            Adds a = new Adds();
            
                try {
                    a = as.FindById(cupcake.Cupcake.Selected_id);
                    } catch (SQLException ex) {Logger.getLogger(EditAddsController.class.getName()).log(Level.SEVERE, null, ex);}
                
            title.setText(a.getTitle());
            rank.setText(Integer.toString(a.getRank()));
            description.setText(a.getDescription());
        
            File file = new File(a.getImage());
            path = file.getName();
            
            Image image = new Image("http://localhost/cupcake/" +a.getImage());
            imageViewAdd.setImage(image);  
        }
        
    }    

    @FXML
    void GestionAdds(ActionEvent event) throws IOException {
        
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
    void editAdds(ActionEvent event) throws SQLException {
        
        AddsService as = new AddsService();
        
        if(validateInputs()){
            
            EnseigneServices es = new EnseigneServices();
            Enseigne ens = new Enseigne();
            ens = es.getEnseignebyUserId(cupcake.Cupcake.user_id);
            
            Adds a = new Adds(ens.getId(), title.getText(), path, description.getText(), Integer.parseInt(rank.getText()));
            as.EditAdds(a,cupcake.Cupcake.Selected_id);
            
            String tit = "Opération réussie";
            String message = "La publicité a été modifiée avec succée";
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

        if ((description.getText().isEmpty()) || (rank.getText().isEmpty()) || ("".equals(path)) || (title.getText().isEmpty())) {
            
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Vous devez remplir tous les champs");
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
    

    
}
