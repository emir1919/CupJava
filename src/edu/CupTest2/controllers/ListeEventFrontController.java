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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import edu.cupcake.entities.Enseigne;
import edu.cupcake.entities.Events;
import edu.cupcake.services.EnseigneServices;
import edu.cupcake.utils.Connexion;
import edu.cupcake.utils.Routes;
import edu.cupcake.utils.Routing;

/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class ListeEventFrontController implements Initializable {

    @FXML
    private AnchorPane Type;

    
    @FXML
    private TableView<Events> tableEvent;

    @FXML
    private TableColumn<Events,String> txtTitle;

    @FXML
    private TableColumn<Events,Date> txtDate;
    


    @FXML
    private JFXTextField searchField;
    
    ObservableList<Events> data;
    
    
    
    private Events selectedid;
    @FXML
    private JFXButton BTNpts;

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
    
   
    private void loadDataFromDatabase() {
             Connection cn = Connexion.getInstance().getConnection();

        
        EnseigneServices enss = new EnseigneServices();
        Enseigne ens = new Enseigne();
        ens = enss.getEnseignebyUserId(cupcake.Cupcake.user_id);
       // int id_ens = ens.getId();

        try {

            data = FXCollections.observableArrayList();

            String req = "SELECT * FROM events";
            PreparedStatement pre = cn.prepareStatement(req);
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
            
            TableColumn actionCol = new TableColumn("Action");
            actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

            Callback<TableColumn<Events, String>, TableCell<Events, String>> cellFactory = new Callback<TableColumn<Events, String>, TableCell<Events, String>>() {
            
            @Override
            public TableCell call(final TableColumn<Events, String> param) {
                final TableCell<Events, String> cell = new TableCell<Events, String>() {

                     final Button btn = new Button("Afficher");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } 
                            else {
                                btn.setOnAction(event -> {
                                Events e = getTableView().getItems().get(getIndex());
                                cupcake.Cupcake.Selected_id = e.getId();
                                
                                Parent root = null;
                                HomeController.showevents=1;
                                    try {
                                        root = FXMLLoader.load(getClass().getResource(Routing.HOME));
                                    } catch (IOException ex) {
                                        Logger.getLogger(ListeEventFrontController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                               /* Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.setMaximized(true);
                                stage.show();*/
  tableEvent.getScene().setRoot(root);
                                
                                
                            });
                                
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
            };

            actionCol.setCellFactory(cellFactory);
            tableEvent.setItems(data);
            tableEvent.getColumns().add(actionCol);

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    @FXML
    private void GoFidelity(ActionEvent event) throws IOException {
        
         Parent root = null;
         root = FXMLLoader.load(getClass().getResource(Routes.MesPointsView));   
         Scene scene = new Scene(root);
         Stage stage = new Stage();
         stage.setScene(scene);
         stage.setMaximized(true);
         stage.show();
    }
    
    
 
    
}
