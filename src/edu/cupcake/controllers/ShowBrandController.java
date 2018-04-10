/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import edu.CupTest2.entities.Enseigne;
import edu.CupTest2.services.EnseigneServices;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class ShowBrandController implements Initializable {

    @FXML
    private ImageView BrandLogo;
    @FXML
    private Text BrandName;
    @FXML
    private Text BrandDescription;
    @FXML
    private JFXButton DetailsButton;
    List<Enseigne> enseignes = new ArrayList<Enseigne>();
    ObservableList<Enseigne> oa;
    EnseigneServices es = new EnseigneServices();
    Enseigne en;
    @FXML
    private Rating rate;
    @FXML
    private VBox box;
    @FXML
    private HBox hbox1;
    @FXML
    private HBox hbox2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

           // hbox2.getChildren().addAll(AfficheVbox(box), AfficheVbox(box));
en = es.getEnseignebyId(100);
       try {
            enseignes = es.selectAllEnseigne();
        } catch (SQLException ex) {
            Logger.getLogger(ShowBrandController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileInputStream file = null;
        try {
            file = new FileInputStream("C:\\Users\\Emir\\Documents\\NetBeansProjects\\CupTest2\\src\\edu\\CupTest2\\images\\" + enseignes.get(1).getLogo());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ShowBrandController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BrandLogo.setImage(new Image(file));
        BrandName.setText(enseignes.get(enseignes.size()-1).getName());
        BrandDescription.setText(enseignes.get(1).getDescription());

    }

    public VBox AfficheVbox(VBox u) {
        u = new VBox();
        en = es.getEnseignebyId(100);
        try {
            enseignes = es.selectAllEnseigne();
        } catch (SQLException ex) {
            Logger.getLogger(ShowBrandController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileInputStream file = null;
        try {
            file = new FileInputStream("C:\\Users\\Emir\\Documents\\NetBeansProjects\\CupTest2\\src\\edu\\CupTest2\\images\\" + enseignes.get(1).getLogo());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ShowBrandController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BrandLogo.setImage(new Image(file));
        BrandName.setText(enseignes.get(1).getName());
        BrandDescription.setText(enseignes.get(1).getDescription());
        u.getChildren().addAll(BrandLogo, BrandName);
        return u;

    }

    public ImageView getBackground() {
        return BrandLogo;
    }

    @FXML
    private void MoreDetails(ActionEvent event) {
    }

}
