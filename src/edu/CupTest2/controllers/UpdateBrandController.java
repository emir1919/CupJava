/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import edu.cupcake.entities.Enseigne;
import static edu.cupcake.controllers.InfoBakeryController.IdEnseigne;
import edu.cupcake.services.EnseigneServices;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class UpdateBrandController implements Initializable {

    @FXML
    private Button InfoBrandButton;
    @FXML
    private Button BakeryButton;
    @FXML
    private Button map;
    @FXML
    private Button stat;
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
    private static Enseigne selectedenseigne;
    @FXML
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
        try {
            // TODO
            FileInputStream file = null;
            EnseigneServices es=new EnseigneServices();
            Enseigne en=es.getEnseignebyUserId(cupcake.Cupcake.user.getId());
            /*try {
                es.getImage(background);
            } catch (SQLException ex) {
                Logger.getLogger(AddBakeryController.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            file = new FileInputStream("C:\\Users\\Emir\\Documents\\NetBeansProjects\\CupTest2\\src\\edu\\CupTest2\\images\\" + en.getLogo());
            background.setImage(new Image(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddBakeryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void ShowBrand(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoBrand.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) InfoBrandButton.getScene().getWindow();
        stage.close();
        Stage s = new Stage();
        s.setScene(new Scene(root));
        s.show();
    }

    @FXML
    private void ShowBakery(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoBakery.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) BakeryButton.getScene().getWindow();
        stage.close();
        Stage s = new Stage();
        s.setScene(new Scene(root));
        s.show();
    }

    @FXML
    private void localisation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Localisation.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) BakeryButton.getScene().getWindow();
        stage.close();
        Stage s = new Stage();
        s.setScene(new Scene(root));
        s.show();
    }

    @FXML
    private void statistiques(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Statistiques.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) BakeryButton.getScene().getWindow();
        stage.close();
        Stage s = new Stage();
        s.setScene(new Scene(root));
        s.show();
    }

    @FXML
    private void upload(ActionEvent event) {
        Image image1;
        fc = new FileChooser();
        selectedfile = fc.showOpenDialog(null);
        if (selectedfile != null) {
            logo.setText(selectedfile.getName());
            System.out.println(selectedfile.toPath().toString());
            System.out.println(selectedfile.toURI().toString());
            System.out.println(selectedfile.getAbsolutePath());

            //image.setImage(new Image("file:"+selectedfile.toPath().toString()));
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

        try {
            selectedenseigne.setId(selectedenseigne.getId());
            selectedenseigne.setName(nom.getText());
            selectedenseigne.setAddress(adresse.getText());
            selectedenseigne.setPhoneNumber(tel.getText());
            selectedenseigne.setFax(fax.getText());
            selectedenseigne.setEmail(email.getText());
            selectedenseigne.setWebSite(site.getText());
            selectedenseigne.setCodeRc(code.getText());
            selectedenseigne.setLogo(logo.getText());
            Path p = Paths.get(selectedfile.getAbsolutePath());
            Path p1 = Paths.get("C:\\Users\\Emir\\Documents\\NetBeansProjects\\CupTest2\\src\\edu\\Cuptest2\\images\\" + selectedfile.getName());
            Files.copy(p, p1, REPLACE_EXISTING);
            selectedenseigne.setDescription(text.getText());
            EnseigneServices es = new EnseigneServices();
            es.modifierEnseigne(selectedenseigne.getId(),nom.getText(),adresse.getText(),tel.getText(),fax.getText(),email.getText(),site.getText(),logo.getText(),code.getText(),text.getText());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoBrand.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) BrandUpdateButton.getScene().getWindow();
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
            
        }}
    
}
