/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import edu.cupcake.entities.Adds;
import edu.cupcake.entities.Enseigne;
import edu.cupcake.entities.Events;
import edu.cupcake.services.AddsService;
import edu.cupcake.services.EnseigneService;
import edu.cupcake.services.EventsService;
import edu.cupcake.utils.Connexion;
import edu.cupcake.utils.Routes;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class ShoweventsBrandController implements Initializable {
    
    @FXML
    private ImageView logo;

    @FXML
    private JFXButton GE;

    @FXML
    private JFXButton CR;

    @FXML
    private TableView<Events> tableEvent;

    @FXML
    private TableColumn<Events,String> txtTitle;

    @FXML
    private TableColumn<Events,Date> txtDate;

    @FXML
    private TableColumn<Events,String> txtType;

    @FXML
    private TableColumn<Events,String> txtAdresse;

    @FXML
    private TableColumn<Events,Integer> txtNBP;

    @FXML
    private TableColumn<Events,String> txtDescription;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXButton BtnModifier;

    @FXML
    private JFXButton txtSupprimer;

    @FXML
    private JFXButton BtnAjouter;

    @FXML
    private ImageView imageViewEvent;    
    
    ObservableList<Events> data;
    
    private Events selectedid;
    @FXML
    private JFXButton LP;
    @FXML
    private JFXButton CP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDataFromDatabase();
        
        FilteredList<Events> filteredData = new FilteredList<>(data, e -> true);
       
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(a -> {
                
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                String lowerCaseFilter = newValue.toLowerCase();

                if (a.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (a.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }
                return false; 
            });
        });

         
        SortedList<Events> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableEvent.comparatorProperty());
        tableEvent.setItems(sortedData);
        
        
        
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
        
        if (tableEvent.getSelectionModel().getSelectedItem() == null) {

            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Vous devez selectionner l'évenement à modifier");
            alert1.setHeaderText(null);
            alert1.show();
        } else {

            cupcake.Cupcake.Selected_id = tableEvent.getSelectionModel().getSelectedItem().getId();
            Parent root = null;
            root = FXMLLoader.load(getClass().getResource(Routes.EditEventsVIEW));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        }

    }

    @FXML
    private void afficherImage(MouseEvent event) {
        try {

            selectedid = (Events) tableEvent.getSelectionModel().getSelectedItem();
            System.out.println(selectedid.getImage());
            File file = new File("C:\\Users\\El Kamel\\Pictures\\"+selectedid.getImage());

            Image image = new Image(file.toURI().toURL().toExternalForm());
            imageViewEvent.setImage(image);

        } catch (MalformedURLException ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void supprimer(ActionEvent event) throws SQLException {
       
                
        if (tableEvent.getSelectionModel().getSelectedItem() == null) {

            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Vous devez selectionner l'évenement à supprimer");
            alert1.setHeaderText(null);
            alert1.show();
        } else {
        
          
        ButtonType ok = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,"Voulez vous vraiment supprimer la publicité ? ",ok,cancel);

        alert.setTitle("Suppresion");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ok) {
            
            EventsService es = new EventsService();
            ObservableList<Events> EventSelected,allEvents;

            allEvents = tableEvent.getItems();
            EventSelected = tableEvent.getSelectionModel().getSelectedItems();
            es.DeleteEvent(EventSelected.get(0).getId());
            
            EventSelected.forEach(data::removeAll);
            
            
        }

            String tit = "Opération réussie";
            String message = "La publicité a été supprimée avec succée";
            NotificationType notification = NotificationType.SUCCESS;
    
            TrayNotification tray = new TrayNotification(tit, message, notification);          
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(2));
        }
        

    }   
    
    private void loadDataFromDatabase() {
                Connection cn = Connexion.getInstance().getConnection();  

        
        EnseigneService enss = new EnseigneService();
        Enseigne ens = new Enseigne();
        ens = enss.FindByUserId(cupcake.Cupcake.user_id);
        int id_ens = ens.getId();

        try {

            data = FXCollections.observableArrayList();

            EventsService es = new EventsService();
            String req = "SELECT * FROM events WHERE enseigne_id = ?";
            PreparedStatement pre = cn.prepareStatement(req);
            pre.setInt(1, id_ens);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {

                data.add(new Events(
                     
                rs.getInt("id"),
                rs.getDate("DateStart"),
                rs.getString("Title"),
                rs.getString("Description"),
                rs.getString("Image"),
                rs.getString("Type"),
                rs.getString("Adress"),
                rs.getInt("nbPlaces"),
                rs.getInt("nbPart"),
                rs.getInt("enseigne_id")
               
                ));

            }

            txtTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
            txtDate.setCellValueFactory(new PropertyValueFactory<>("DateStart"));
            txtType.setCellValueFactory(new PropertyValueFactory<>("Type"));
            txtAdresse.setCellValueFactory(new PropertyValueFactory<>("Adress"));
            txtNBP.setCellValueFactory(new PropertyValueFactory<>("nbPlaces"));
            txtDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));


            tableEvent.setItems(data);

        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    
    @FXML
    private void GestionEvents(ActionEvent event) {

    }
    
    @FXML
    private void ClaimsPage(ActionEvent event) throws IOException {
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(Routes.ShowClaimsBrandVIEW));
               
        Scene scene = new Scene(root);
   
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();  

    }

    @FXML
    private void GoLP(ActionEvent event) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(Routes.ListeEventsPRVIEW));
               
        Scene scene = new Scene(root);
   
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();  
    }

    @FXML
    private void GoCP(ActionEvent event) throws IOException {
    
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(Routes.ListEventsVIEW));
               
        Scene scene = new Scene(root);
   
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        
    }
    
}
