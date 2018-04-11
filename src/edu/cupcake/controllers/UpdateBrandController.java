/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import edu.cupcake.entities.Enseigne;
import static edu.cupcake.controllers.InfoBakeryController.IdEnseigne;
import edu.cupcake.services.EnseigneServices;
import edu.cupcake.services.Upload;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class UpdateBrandController implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField adresse;
    @FXML
    private TextField tel;
    @FXML
    private TextField fax;
    @FXML
    private TextField email;
    @FXML
    private TextField site;
    @FXML
    private TextField code;
    @FXML
    private TextField logo;
    @FXML
    private TextField text;
    @FXML
    private Button parcourir;
    @FXML
    private Button BrandUpdateButton;
    private FileChooser fc;
    private File selectedfile;
    public static Enseigne selectedenseigne;
    private ImageView background;

    /**
     * Initializes the controller class.
     */
    public void initController(Enseigne e)
    {
        selectedenseigne=e;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
            // TODO
            FileInputStream file = null;
            EnseigneServices es=new EnseigneServices();
            Enseigne en=es.getEnseignebyUserId(cupcake.Cupcake.user.getId());
            /*try {
                es.getImage(background);
            } catch (SQLException ex) {
                Logger.getLogger(AddBakeryController.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        
    }    

   

    @FXML
    private void upload(ActionEvent event) {
       FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jpg File", "*.jpg", "*.png"));
        try {
            File f = fc.showOpenDialog(null);

            if (f != null) {
                String backslash = System.getProperty("file.separator");
                String st = f.getAbsolutePath().replace(backslash, "/");
                String s = "file:" + st;

                logo.setText(s);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void UpdateBrand(ActionEvent event) throws IOException {
        if (nom.getText().length() == 0 || adresse.getText().length() == 0 
                 || tel.getText().length() == 0  || fax.getText().length() == 0 
                 || email.getText().length() == 0 || site.getText().length() == 0
                 || code.getText().length() == 0 || logo.getText().length() == 0
                 || text.getText().length() == 0) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("tous les champs doivent etre remplis");
                alert.showAndWait();
            }
          else  {
  String url = null;
        String url2 = null;
        String url3 = "";

        if (!(logo.getText().isEmpty())) {
            url = logo.getText();
            url2 = "http://localhost/cupcake/uploads/";
            url3 = url2.concat(url.substring(url.lastIndexOf('/') + 1, url.length()));

        }
        String filePath = "";
        if (!(logo.getText().isEmpty())) {
            Upload u = new Upload();
            File f = new File(url.replace("file:", ""));

            filePath = u.UploadFile(f);
            //Image ii = new Image(url3);
        }
        try {
            selectedenseigne.setId(selectedenseigne.getId());
            selectedenseigne.setName(nom.getText());
            selectedenseigne.setAddress(adresse.getText());
            selectedenseigne.setPhoneNumber(tel.getText());
            selectedenseigne.setFax(fax.getText());
            selectedenseigne.setEmail(email.getText());
            selectedenseigne.setWebSite(site.getText());
            selectedenseigne.setCodeRc(code.getText());
            selectedenseigne.setLogo(filePath);
         
            selectedenseigne.setDescription(text.getText());
            EnseigneServices es = new EnseigneServices();
            es.modifierEnseigne(selectedenseigne.getId(),selectedenseigne.getName(),selectedenseigne.getAddress(),selectedenseigne.getPhoneNumber(),selectedenseigne.getFax(),selectedenseigne.getEmail(),selectedenseigne.getWebSite(),selectedenseigne.getLogo(),selectedenseigne.getLogo(),selectedenseigne.getDescription());
            try {
                BackBrandController.informations=1;
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/BackBrand.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
       
        adresse.getScene().setRoot(root);
            } catch (IOException ex) {
                Logger.getLogger(AddBrandController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBrandController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }}
    
}
