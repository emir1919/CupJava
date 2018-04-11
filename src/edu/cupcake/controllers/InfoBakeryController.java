/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import edu.cupcake.entities.Bakery;
import edu.cupcake.entities.Enseigne;
import static edu.cupcake.controllers.AddBakeryController.IdEnseigne;
import static edu.cupcake.controllers.InfoBrandController.NameEnseigne;
import edu.cupcake.services.EnseigneServices;
import edu.cupcake.services.PatisserieServices;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class InfoBakeryController implements Initializable {

    private Button InfoBrandButton;
    private Button BakeryButton;
    private Button map;
    private Button stat;
    @FXML
    private Button Add;
    @FXML
    private Button update;
    @FXML
    private Button delete;
    @FXML
    private TableColumn<Bakery, String> nom;
    @FXML
    private TableColumn<Bakery, String> adresse;
    @FXML
    private TableColumn<Bakery, String> tel;
    @FXML
    private TableColumn<Bakery, String> fax;
    @FXML
    private TableColumn<Bakery, String> email;
    @FXML
    private TableView<Bakery> TableId;
    PatisserieServices ps=new PatisserieServices();
    List<Bakery> bakeries = new ArrayList<Bakery>();
    ObservableList<Bakery> oa;
    static Integer IdEnseigne;
    private ImageView background;
                EnseigneServices es=new EnseigneServices();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
            // TODO
            FileInputStream file = null;
            Enseigne en=es.getEnseignebyUserId(cupcake.Cupcake.user.getId());
            /*try {
                es.getImage(background);
            } catch (SQLException ex) {
                Logger.getLogger(AddBakeryController.class.getName()).log(Level.SEVERE, null, ex);
            }*/
          
        // TODO
        bakeries = ps.getBakerybyBrand(es.getEnseignebyUserId(cupcake.Cupcake.user.getId()).getId());
        //en=es.getEnseignebyName(NameEnseigne);
        nom.setCellValueFactory(new PropertyValueFactory<Bakery, String>("Name"));
        adresse.setCellValueFactory(new PropertyValueFactory<Bakery, String>("Address"));
        tel.setCellValueFactory(new PropertyValueFactory<Bakery, String>("PhoneNumber"));
        fax.setCellValueFactory(new PropertyValueFactory<Bakery, String>("Fax"));
        email.setCellValueFactory(new PropertyValueFactory<Bakery, String>("Email"));
        TableId.getColumns().addAll(nom,adresse,tel,fax,email);
        //logo.setImage(new Image("file:"+logo.toPath().toString()));
        oa = FXCollections.observableArrayList(bakeries);
        TableId.setItems(oa);
    }    


    

    @FXML
    private void AddBakery(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBakery.fxml"));
        Parent root =loader.load();
        
        Stage stage=(Stage) Add.getScene().getWindow();
        stage.close();
	Stage s = new Stage ();
        s.setScene(new Scene (root));     
        s.show();
    }

    @FXML
    private void UpdateBakery(ActionEvent event) {
         if (TableId.getSelectionModel().getSelectedItem() != null) {

            try {

                /*FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateBakery.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) update.getScene().getWindow();
                stage.close();
                UpdateBakeryController emc = loader.getController();
                emc.initController(TableId.getSelectionModel().getSelectedItem());
                Stage s = new Stage();
                s.setScene(new Scene(root));
                s.show();*/
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/UpdateBakery.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
       
        TableId.getScene().setRoot(root);

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

    @FXML
    private void DeleteBakery(ActionEvent event) throws SQLException {
        if(TableId.getSelectionModel().getSelectedItems().size()!=0){
            PatisserieServices ps=new PatisserieServices();
           ps.supprimerPatisserie(TableId.getSelectionModel().getSelectedItems().get(0).getId());
            ObservableList<Bakery>obe=FXCollections.observableArrayList(ps.getBakerybyBrand(es.getEnseignebyUserId(cupcake.Cupcake.user.getId()).getId()));
            TableId.setItems(obe);
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
