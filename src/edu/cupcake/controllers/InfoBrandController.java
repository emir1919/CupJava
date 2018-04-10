/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import edu.CupTest2.entities.Enseigne;
import edu.CupTest2.services.EnseigneServices;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumnBuilder;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class InfoBrandController implements Initializable {

    @FXML
    private Button InfoBrandButton;
    @FXML
    private Button BakeryButton;
    @FXML
    private Button map;
    @FXML
    private Button stat;
    @FXML
    private TableColumn<Enseigne, String> nom;
    @FXML
    private TableColumn<Enseigne, String> adresse;
    @FXML
    private TableColumn<Enseigne, Integer> tel;
    @FXML
    private TableColumn<Enseigne, String> site;
    @FXML
    private Button edit;
    @FXML
    private TableView<Enseigne> TableId;
    List<Enseigne> enseignes = new ArrayList<Enseigne>();
    ObservableList<Enseigne> oa;
    EnseigneServices es = new EnseigneServices();
    Enseigne en;
    public static String NameEnseigne="";
    @FXML
    private ImageView background;

    public ImageView getBackground() {
        return background;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FileInputStream file=null;
            try {
               
                // TODO
                //enseignes = es.selectAllEnseigne();
                en=es.getEnseignebyUserId(cupcake.Cupcake.user.getId());
                /*try {
                    es.getImage(background);
                } catch (SQLException ex) {
                    Logger.getLogger(InfoBrandController.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                nom.setCellValueFactory(new PropertyValueFactory<Enseigne, String>("name"));
                adresse.setCellValueFactory(new PropertyValueFactory<Enseigne, String>("address"));
                tel.setCellValueFactory(new PropertyValueFactory<Enseigne, Integer>("PhoneNumber"));
                site.setCellValueFactory(new PropertyValueFactory<Enseigne, String>("WebSite"));
                //logo.setCellValueFactory(new PropertyValueFactory<Enseigne, String>("Email"));
                TableId.getColumns().addAll(nom, adresse, tel, site);
                //logo.setImage(new Image("file:"+logo.toPath().toString()));
                oa = FXCollections.observableArrayList(en);
                TableId.setItems(oa);
            file = new FileInputStream("C:\\Users\\Emir\\Documents\\NetBeansProjects\\CupTest2\\src\\edu\\CupTest2\\images\\"+en.getLogo());
            background.setImage(new Image(file));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(InfoBrandController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void editbrand(ActionEvent event) throws IOException {
        if (TableId.getSelectionModel().getSelectedItem() != null) {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateBrand.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) edit.getScene().getWindow();
                stage.close();
                UpdateBrandController emc = loader.getController();
                emc.initController(TableId.getSelectionModel().getSelectedItem());
                Stage s = new Stage();
                s.setScene(new Scene(root));
                s.show();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
         else{
           
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("aucun élément 'a ètè séléctionné");
        alert.showAndWait();

           
        
       }
    }
}
