/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;


import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import edu.cupcake.entities.Adds;
import edu.cupcake.services.AddsService;
import edu.cupcake.utils.Connexion;
import edu.cupcake.utils.Routes;
import tray.notification.TrayNotification;
import tray.notification.NotificationType;
import tray.animations.AnimationType;


/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class ShowAddsController implements Initializable {

    @FXML
    private TableView<Adds> tablePub;

    @FXML
    private TableColumn<Adds, Integer> txtEnseigne;

    @FXML
    private TableColumn<Adds, String> txtTitre;

    @FXML
    private TableColumn<Adds, Integer> txtRang;

    @FXML
    private TableColumn<Adds, String> txtDescription;

    @FXML
    private ImageView imageViewAdd;

    @FXML
    private JFXButton btnLoad;

    @FXML
    private ImageView logo;

    @FXML
    private TextField searchField;

    @FXML
    private JFXButton BtnModifier;

    @FXML
    private JFXButton BtnAjouter;

    @FXML
    private JFXButton GP;
    
    @FXML
    private JFXButton CR;

    ObservableList<Adds> data;

    private Adds selectedid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       

          

      
        loadDataFromDatabase();

        FilteredList<Adds> filteredData = new FilteredList<>(data, e -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(a -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (a.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (a.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

         
        SortedList<Adds> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tablePub.comparatorProperty());
        tablePub.setItems(sortedData);

    }

    @FXML
    private void loadDataFromDatabase() {
 Connection cn = Connexion.getInstance().getConnection();  

        try {

            data = FXCollections.observableArrayList();

            AddsService as = new AddsService();
            String req = "SELECT * FROM adds";
            PreparedStatement pre = cn.prepareStatement(req);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {

                data.add(new Adds(
                        rs.getInt("id"),
                        rs.getInt("enseigne_id"),
                        rs.getString("Title"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        rs.getInt("Rank")
                ));

            }

            txtEnseigne.setCellValueFactory(new PropertyValueFactory<>("enseigne_id"));
            txtTitre.setCellValueFactory(new PropertyValueFactory<>("Title"));
            txtRang.setCellValueFactory(new PropertyValueFactory<>("Rank"));
            txtDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));

            tablePub.setItems(data);

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    @FXML
    private void afficherImage(MouseEvent event) {

       selectedid = (Adds) tablePub.getSelectionModel().getSelectedItem();
       Image image = new Image("http://localhost/cupcake/" +selectedid.getImage());
       imageViewAdd.setImage(image);

    }

    @FXML
    private void supprimer(ActionEvent event) throws SQLException {
        
        if (tablePub.getSelectionModel().getSelectedItem() == null) {

            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Vous devez selectionner la publicité à supprimer");
            alert1.setHeaderText(null);
            alert1.show();
        } else {
        
          
        ButtonType ok = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.WARNING,"Voulez vous vraiment supprimer la publicité ? ",ok,cancel);

        alert.setTitle("Suppresion");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ok) {
            
            AddsService as = new AddsService();
            ObservableList<Adds> AddSelected,allAdds;

            allAdds = tablePub.getItems();
            AddSelected = tablePub.getSelectionModel().getSelectedItems();

            as.DeleteAdds(AddSelected.get(0).getId());

            AddSelected.forEach(data::removeAll);
            
            
        }

            String tit = "Opération réussie";
            String message = "La publicité a été supprimée avec succée";
            NotificationType notification = NotificationType.SUCCESS;
    
            TrayNotification tray = new TrayNotification(tit, message, notification);          
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(2));
        }

    }

    @FXML
    private void Ajouter(ActionEvent event) throws IOException {

        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(Routes.AddAddsVIEW));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    private void Modifier(ActionEvent event) throws IOException {

        if (tablePub.getSelectionModel().getSelectedItem() == null) {

            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Vous devez selectionner la publicité à modifier");
            alert1.setHeaderText(null);
            alert1.show();
        } else {

            cupcake.Cupcake.Selected_id = tablePub.getSelectionModel().getSelectedItem().getId();
            Parent root = null;
            root = FXMLLoader.load(getClass().getResource(Routes.EditAddsVIEW));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        }

    }
    
    @FXML
    private void GestionAdds(ActionEvent event) throws IOException {

        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(Routes.ShowAddsVIEW));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();

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
