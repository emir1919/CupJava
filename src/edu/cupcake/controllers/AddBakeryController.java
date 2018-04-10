/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import edu.CupTest2.entities.Bakery;
import edu.CupTest2.entities.Enseigne;
import edu.CupTest2.entities.Users;
import edu.CupTest2.services.EnseigneServices;
import edu.CupTest2.services.PatisserieServices;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.SQLException;
import java.util.List;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class AddBakeryController implements Initializable {

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
    private Button add;

    public static Integer IdEnseigne;
    @FXML
    private ImageView background;
    @FXML
    private ChoiceBox userchoices=new ChoiceBox();
                EnseigneServices es=new EnseigneServices();

    List<Users> listuser;
    public ImageView getBackground() {
        return background;
    }

    public void setBackground(ImageView background) {
        this.background = background;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            FileInputStream file = null;
            try {
                listuser=es.getAllUsers();
            } catch (SQLException ex) {
                Logger.getLogger(AddBakeryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            listuser.stream().forEach(b -> userchoices.getItems().add(b.getUsername()));

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

        Stage stage = (Stage) map.getScene().getWindow();
        stage.close();
        Stage s = new Stage();
        s.setScene(new Scene(root));
        s.show();
    }

    @FXML
    private void statistiques(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Statistiques.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) stat.getScene().getWindow();
        stage.close();
        Stage s = new Stage();
        s.setScene(new Scene(root));
        s.show();
    }

    @FXML
    private void AddBakery(ActionEvent event) {
        if (nom.getText().length() == 0 || adresse.getText().length() == 0
                || tel.getText().length() == 0 || fax.getText().length() == 0
                || email.getText().length() == 0) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("tous les champs doivent etre remplis");
            alert.showAndWait();
        } else {

            try {
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
                    int index = userchoices.getSelectionModel().getSelectedIndex();

            Bakery e1 = new Bakery();
            System.out.println(IdEnseigne);
            e1.setName(nom.getText());
            e1.setAddress(adresse.getText());
            e1.setPhoneNumber(tel.getText());
            e1.setFax(fax.getText());
            e1.setEmail(email.getText());
            e1.setId_enseigne(es.getEnseignebyUserId(cupcake.Cupcake.user.getId()).getId());
            Long l=listuser.get(index).getId();
            int a=(int)(long)l;
            e1.setId_user(a);
            PatisserieServices ps = new PatisserieServices();
            ps.ajouterPatisserie(e1);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoBakery.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) add.getScene().getWindow();
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

}
