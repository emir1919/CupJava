/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.gui;

import com.jfoenix.controls.JFXButton;
import edu.CupTest2.gui.main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class HomeController implements Initializable {

    @FXML
    private VBox menupane;
    @FXML
    private JFXButton connexiobutton;
    @FXML
    private JFXButton profilebutton;
    @FXML
    private AnchorPane content;
    @FXML
    private JFXButton BrandButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (main.user != null) {
            menupane.getChildren().remove(connexiobutton);

        } else {
            menupane.getChildren().remove(profilebutton);
        }
    }

    @FXML
    public void Connexion(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/CupTest2/gui/Login.fxml"));
        AnchorPane root = (AnchorPane) loader.load();

        menupane.getScene().setRoot(root);

    }

    @FXML
    public void Afficherprofile(ActionEvent event) throws IOException {

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Login.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
       
        menupane.getScene().setRoot(root);*/
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/CupTest2/gui/Profile.fxml"));
        //AnchorPane pane1=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/EditProfile.fxml"));

        content.getChildren().setAll(pane);

    }

    @FXML
    private void ShowBrand(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/CupTest2/gui/BrandFront.fxml"));
        content.getChildren().setAll(pane);

    }

}
