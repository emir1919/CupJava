/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.cupcake.entities.Adresses;
import edu.cupcake.entities.ObservableLineOrders;
import edu.cupcake.entities.Users;
import edu.cupcake.services.AdressesService;
import edu.cupcake.services.Line_OrderService;
import edu.cupcake.services.UsersService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class BakeryOrdersController implements Initializable {

    
        ObservableList<Users> DeliveryList ;
            List<Users> deliverys;

                //FXCollections.observableArrayList(us.getUserbyRole("ROLE_DELIVERYMAN"));

    @FXML
    private Button stockManagementBtn;
    @FXML
    private Button cmdbutton;
    @FXML
    private TableView<ObservableLineOrders> orderslist;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private TableColumn<?, ?> produit;
    @FXML
    private TableColumn<?, ?> qte;
    @FXML
    private TableColumn<?, ?> prix;
    @FXML
    private TableColumn<?, ?> livraison;
      List<ObservableLineOrders> listAdresses;
    ObservableList<ObservableLineOrders> listViewAdresses;
    @FXML
    private JFXButton btnAffecter;
    @FXML
    private JFXComboBox<Users> combousers;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       btnAffecter.setVisible(false);
        try {
            initCol();
            loadData();
        } catch (Exception e) {
        }
        orderslist.setOnMouseClicked((event) -> {
               if (orderslist.getSelectionModel().getSelectedItem().getAffected() == null || orderslist.getSelectionModel().getSelectedItem().getAffected().equals("") )
              {
                  btnAffecter.setVisible(true);
              }
               else
               {
              btnAffecter.setVisible(false);

               }
            });
        
        UsersService us= new UsersService();
        deliverys = new ArrayList<Users>();
            try {
                deliverys=us.getUserbyRole("ROLE_DELIVERYMAN");
            } catch (SQLException ex) {
                Logger.getLogger(BakeryOrdersController.class.getName()).log(Level.SEVERE, null, ex);
            }
            List <String> list=new ArrayList<String>();
           Iterator<Users> it = deliverys.iterator();
        UsersService usr= new UsersService();
                               while (it.hasNext()) {
            Users next = it.next();
            list.add(next.getUsername());
                                   
                                   
            
        }
                               
            System.err.println(deliverys);
            DeliveryList=FXCollections.observableArrayList(deliverys);
            
        combousers.setItems(DeliveryList);
       
        
        
    }  
    
    
    private void initCol() {
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        produit.setCellValueFactory(new PropertyValueFactory<>("produit"));
        qte.setCellValueFactory(new PropertyValueFactory<>("qte"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        livraison.setCellValueFactory(new PropertyValueFactory<>("affected"));
        

    }

    private void loadData() {

        /*  try{
        AdressesService adresse = new AdressesService();
        ObservableList<Document> data = FXCollections.observableArrayList(adresse.listeAdresses(1));
        //tableView.setItems(data);
        tableView.getItems().setAll(data);
        list = data;
        }catch(Exception e){
        Logger.getLogger(DocumentsServices.class.getName()).log(Level.SEVERE, null, e);
        }*/
        try {
            Line_OrderService lsr = new Line_OrderService();
            listAdresses = new ArrayList<>();
            listAdresses = lsr.getLineOrdersbyBakeryId(3);
              System.out.println(listAdresses.toString());
            listViewAdresses = FXCollections.observableArrayList(listAdresses);
            orderslist.setItems(listViewAdresses);
          
            initCol();
        } catch (Exception e) {
        }

    }

    @FXML
    private void showStockManagement(ActionEvent event) {
    }

    @FXML
    private void ShowLineOrders(ActionEvent event) {
    }

    @FXML
    private void AffecterLivraison(ActionEvent event) throws IOException {
       AffectationLivreurController.setSelecteduser(combousers.getSelectionModel().getSelectedItem());
        AffectationLivreurController.setSelected(orderslist.getSelectionModel().getSelectedItem());
       FXMLLoader lo = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/AffectationLivreur.fxml"));
                            Parent root = lo.load();
                             AffectationLivreurController pc=lo.getController();
                            // pc.setSelected(orderslist.getSelectionModel().getSelectedItem());
                            // pc.setSelecteduser(combousers.getSelectionModel().getSelectedItem());
        System.err.println("papapap"+orderslist.getSelectionModel().getSelectedItem().getId());
    
                            // pc.txtEditNom.setText(""+pc.getSelected().getId());
                            Stage stage = new Stage(StageStyle.DECORATED);
                            stage.setTitle("Modification adresse");    

                           
                          stage.setResizable(false);
                            stage.setScene(new Scene(root));
                            stage.show();
    }
    
}
