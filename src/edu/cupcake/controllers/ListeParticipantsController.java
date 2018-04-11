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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import edu.cupcake.entities.Enseigne;
import edu.cupcake.entities.Events;
import edu.cupcake.entities.TypeClaim;
import edu.cupcake.entities.Users;
import edu.cupcake.services.EnseigneServices;
import edu.cupcake.utils.Connexion;
import edu.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class ListeParticipantsController implements Initializable {
    
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
    private TableView<Users> tableClient;

    @FXML
    private TableColumn<Users,String> txtNom;

    @FXML
    private TableColumn<Users,String> txtPrenom;

    @FXML
    private TableColumn<Users,Integer> txtCIN;

    @FXML
    private TableColumn<Users,Integer> txtPhone;

    @FXML
    private TableColumn<Users,String> txtEmail;

    @FXML
    private JFXTextField searchField;
    
    @FXML
    private ImageView ImageViewUser;
    
    ObservableList<Users> data;
    
    private Users selectedid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDataFromDatabase();
        
        FilteredList<Users> filteredData = new FilteredList<>(data, e -> true);
       
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(a -> {
                
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                String lowerCaseFilter = newValue.toLowerCase();

                if (a.getFirstname().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (a.getLastname().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }
                return false; 
            });
            
                            txtNom.setCellFactory(column -> {
                return new TableCell<Users, String>() {
                    
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? "" : getItem().toString());
                        setGraphic(null);

                        TableRow<Users> currentRow = getTableRow();

                if (!isEmpty()) {

                    if(item.contains("'")) 
                        currentRow.setStyle("-fx-background-color:lightgreen");
                    else
                        currentRow.setStyle("-fx-background-color:red");
                }
              }
               };
              });
            
            
        });

         
        SortedList<Users> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableClient.comparatorProperty());
        tableClient.setItems(sortedData);
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
    private void afficherImage(MouseEvent event) {
        
        try {

            selectedid = (Users) tableClient.getSelectionModel().getSelectedItem();
            System.out.println(selectedid.getImage());
            File file = new File(selectedid.getImage());

            Image image = new Image(file.toURI().toURL().toExternalForm());
            ImageViewUser.setImage(image);

        } catch (MalformedURLException ex) {
            System.out.println(ex);
        }

    }
    
        @FXML
    private void loadDataFromDatabase() {
            Connection cn = Connexion.getInstance().getConnection();

        
        EnseigneServices enss = new EnseigneServices();
        Enseigne ens = new Enseigne();
        ens = enss.getEnseignebyUserId(cupcake.Cupcake.user.getId());
        int id_ens = ens.getId();

        try {

            data = FXCollections.observableArrayList();

            
            String req = "SELECT * From USERS u join user_event uv on  (u.id = uv.user_id) where ( uv.event_id = ? )";

            PreparedStatement pre = cn.prepareStatement(req);
            pre.setInt(1,cupcake.Cupcake.Selected_id);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                
                String nom;
                if(rs.getInt("confirmed") == 1){
                    nom = rs.getString("LastName") + "'";
                }
                else{
                    nom = rs.getString("LastName");
                }

                data.add(new Users(
                     
                rs.getInt("id"),
                rs.getString("firstname"),
                nom,
                rs.getString("email"),
                rs.getString("password"),
                rs.getInt("phonenumber"),
                rs.getInt("CIN"),
                rs.getString("image_name"),
                rs.getInt("confirmed")
               
                ));

            }
            
            txtNom.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            txtPrenom.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            txtCIN.setCellValueFactory(new PropertyValueFactory<>("CIN"));
            txtEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            txtPhone.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
            
           
                txtNom.setCellFactory(column -> {
                return new TableCell<Users, String>() {
                    
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? "" : getItem().toString());
                        setGraphic(null);

                        TableRow<Users> currentRow = getTableRow();

                if (!isEmpty()) {

                    if(item.contains("'")) 
                        currentRow.setStyle("-fx-background-color:lightgreen");
                    else
                        currentRow.setStyle("-fx-background-color:red");
                }
              }
               };
              });
         
            



            tableClient.setItems(data);

        } catch (SQLException e) {
            System.out.println(e);
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
