/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import edu.cupcake.entities.Enseigne;
import edu.cupcake.entities.Events;
import edu.cupcake.services.EnseigneServices;
import edu.cupcake.utils.Connexion;
import edu.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class ListeEventsPRController implements Initializable {

   @FXML
    private AnchorPane Type;

    @FXML
    private ImageView logo;

    @FXML
    private JFXButton GE;

    @FXML
    private JFXButton CR;
    
    @FXML
    private JFXButton LP;
    
    @FXML
    private JFXButton CP;
    
    @FXML
    private JFXButton BTAfficher ;
    
    @FXML
    private Pane pane;
    
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
    
    ObservableList<Events> data;
    
    
    
    private Events selectedid;

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
        
//        EventsService es = new EventsService();
//        List liste_events = new ArrayList<>();
//        
//        EnseigneService enss = new EnseigneService();
//        Enseigne ens = new Enseigne();
//        ens = enss.FindByUserId(Cupcup.user_id);
//        int id_ens = ens.getId();
//        
//        liste_events = es.FindByEnseigneID(id_ens);
//        
//
//        int nb = 111;
//        for(Object evt : liste_events){
//            
//            Events e = (Events)evt;
//            Label l = new Label(e.getDescription());
//            Node n = l;
//            n.setLayoutY(nb+10);
//            nb+=10;
//            pane.getChildren().add(n);
//            n.setVisible(true);
//            System.out.println(e.getId());
//            
//        }
        

             
        
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
    private void GestionEvents(ActionEvent event) throws IOException {
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(Routes.ShoweventsBrandVIEW));
               
        Scene scene = new Scene(root);
   
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();  

    }
    
    @FXML
    private void loadDataFromDatabase() {
            Connection cn = Connexion.getInstance().getConnection();

        
        EnseigneServices enss = new EnseigneServices();
        Enseigne ens = new Enseigne();
        ens = enss.getEnseignebyUserId(cupcake.Cupcake.user_id);
        int id_ens = ens.getId();

        try {

            data = FXCollections.observableArrayList();

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
    private void afficherImage(MouseEvent event) {

    }
    
    @FXML
    private void Afficher(ActionEvent event) throws IOException, SQLException {
        
        if (tableEvent.getSelectionModel().getSelectedItem() == null) {

            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Vous devez selectionner un évenement");
            alert1.setHeaderText(null);
            alert1.show();
        } else {

            cupcake.Cupcake.Selected_id = tableEvent.getSelectionModel().getSelectedItem().getId();

            Parent root = null;
            root = FXMLLoader.load(getClass().getResource(Routes.ListeParticipantsVIEW));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        }

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
