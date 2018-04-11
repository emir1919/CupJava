/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import edu.cupcake.entities.Enseigne;
import edu.cupcake.services.EnseigneServices;
import edu.cupcake.services.Upload;
import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class AddBrandController implements Initializable {

    @FXML
    private TextField name;
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
    private TextField img;
    @FXML
    private TextField text;
    @FXML
    private Button BrandAddButton;
    @FXML
    private Button upload;

    private FileChooser fc;
    private File selectedfile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void AddBrand(ActionEvent event) throws IOException {
         if (name.getText().length() == 0 || adresse.getText().length() == 0 
                 || tel.getText().length() == 0  || fax.getText().length() == 0 
                 || email.getText().length() == 0 || site.getText().length() == 0
                 || code.getText().length() == 0 || img.getText().length() == 0
                 || text.getText().length() == 0) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("tous les champs doivent etre remplis");
                alert.showAndWait();
            }
          else  {

            try {
                Integer.parseInt(code.getText());
                Integer.parseInt(fax.getText());
                Integer.parseInt(tel.getText());


                
            } catch (NumberFormatException e) {
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("fax et tel doivent etre des nombres");
                alert.showAndWait();

            }
            
            
            }
         String url = null;
        String url2 = null;
        String url3 = "";

        if (!(img.getText().isEmpty())) {
            url = img.getText();
            url2 = "http://localhost/cupcake/uploads/";
            url3 = url2.concat(url.substring(url.lastIndexOf('/') + 1, url.length()));

        }
        String filePath = "";
        if (!(img.getText().isEmpty())) {
            Upload u = new Upload();
            File f = new File(url.replace("file:", ""));

            filePath = u.UploadFile(f);
            //Image ii = new Image(url3);
        }
        try {
            Enseigne e1 = new Enseigne();
            e1.setName(name.getText());
            e1.setAddress(adresse.getText());
            e1.setPhoneNumber(tel.getText());
            e1.setFax(fax.getText());
            e1.setEmail(email.getText());
            e1.setWebSite(site.getText());
            e1.setCodeRc(code.getText());
            e1.setLogo(filePath);
            
            e1.setDescription(text.getText());
            e1.setId_user(cupcake.Cupcake.user.getId());
            EnseigneServices es = new EnseigneServices();
            es.ajouterEnseigne(e1);
            InfoBrandController.NameEnseigne=e1.getName();
            InfoBakeryController.IdEnseigne=e1.getId();
            System.out.println(e1.toString());
            AddBakeryController.IdEnseigne=e1.getId();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoBrand.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) BrandAddButton.getScene().getWindow();
                stage.close();
                    
                Stage s = new Stage();
                s.setScene(new Scene(root));
                s.show();
            } catch (IOException ex) {
                Logger.getLogger(AddBrandController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBrandController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

                img.setText(s);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
