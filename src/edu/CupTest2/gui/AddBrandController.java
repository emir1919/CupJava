/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.gui;

import edu.CupTest2.entities.Enseigne;
import edu.CupTest2.services.EnseigneServices;
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
        try {
            Enseigne e1 = new Enseigne();
            e1.setName(name.getText());
            e1.setAddress(adresse.getText());
            e1.setPhoneNumber(tel.getText());
            e1.setFax(fax.getText());
            e1.setEmail(email.getText());
            e1.setWebSite(site.getText());
            e1.setCodeRc(code.getText());
            e1.setLogo(img.getText());
            Path p = Paths.get(selectedfile.getAbsolutePath());
            Path p1 = Paths.get("C:\\Users\\Emir\\Documents\\NetBeansProjects\\CupTest2\\src\\edu\\Cuptest2\\images\\" + selectedfile.getName());
            Files.copy(p, p1, REPLACE_EXISTING);
            e1.setDescription(text.getText());
            e1.setId_user(1);
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
        Image image1;
        fc = new FileChooser();
        selectedfile = fc.showOpenDialog(null);
        if (selectedfile != null) {
            img.setText(selectedfile.getName());
            System.out.println(selectedfile.toPath().toString());
            System.out.println(selectedfile.toURI().toString());
            System.out.println(selectedfile.getAbsolutePath());

            //image.setImage(new Image("file:"+selectedfile.toPath().toString()));
        }
    }

}
