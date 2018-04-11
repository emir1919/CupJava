/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXListView;
import edu.cupcake.entities.Adresses;
import edu.cupcake.entities.Line_Order;
import edu.cupcake.entities.Orders;
import edu.cupcake.entities.Panier;
import edu.cupcake.services.AdressesService;
import edu.cupcake.services.Line_OrderService;
import edu.cupcake.services.OrdersService;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class ValidationLivraisonCommandeController implements Initializable {

    @FXML
    private TableView<Adresses> tableView;
    @FXML
    private TableColumn<?, ?> Nom;
    @FXML
    private TableColumn<?, ?> Prenom;
    @FXML
    private TableColumn<?, ?> Tel;
    @FXML
    private TableColumn<?, ?> Cp;
    @FXML
    private TableColumn<?, ?> Pays;
    @FXML
    private TableColumn<?, ?> Ville;
    @FXML
    private TableColumn<?, ?> Adresse;
    @FXML
    private TableColumn<?, ?> Complement;
    List<Adresses> listAdresses;
    ObservableList<Adresses> listViewAdresses;
    
    public static Orders currentorder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ;
        try {
            initCol();
            loadData();
        } catch (Exception e) {
        }

    }

    private void initCol() {
        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        Tel.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        Cp.setCellValueFactory(new PropertyValueFactory<>("cp"));
        Pays.setCellValueFactory(new PropertyValueFactory<>("pays"));
        Ville.setCellValueFactory(new PropertyValueFactory<>("ville"));
        Adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        Complement.setCellValueFactory(new PropertyValueFactory<>("complement"));

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
            AdressesService sr = new AdressesService();
            listAdresses = new ArrayList<>();
            listAdresses = sr.listeAdresses(cupcake.Cupcake.user.getId());
            System.err.println(listAdresses);
            listViewAdresses = FXCollections.observableArrayList(listAdresses);
            tableView.setItems(listViewAdresses);
            initCol();
        } catch (Exception e) {
        }

    }

    @FXML
    private void ValiderAdresse(ActionEvent event) throws SQLException, URISyntaxException, IOException {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        Iterator<Panier> it = cupcake.Cupcake.Panier.iterator();
        int nbproduit = 0;
        double prixtotal = 0;
        while (it.hasNext()) {
            Panier next = it.next();
            nbproduit++;
            prixtotal = next.getProduit().getPrice() * next.getQte() + prixtotal;

        }
Random r = new Random();
        Orders order = new Orders(sqlDate, "Cupcake-"+r.nextInt((999999 - 0) + 1) + 0+"-2018", tableView.getSelectionModel().getSelectedItem().getId(), cupcake.Cupcake.user.getId(), (float) prixtotal, "notpaid");
        System.out.println(order.toString());
        
     
             OrdersService orsrv = new OrdersService();
            
            int primkey=orsrv.addOrder(order);
            
                 Iterator<Panier> it1 = cupcake.Cupcake.Panier.iterator();

        while (it1.hasNext()) {
            Panier next = it1.next();
            Line_OrderService linesrv = new Line_OrderService();
            Line_Order line = new Line_Order(next.getQte(), primkey, next.getProduit().getId(), null);
            linesrv.addLineORder(line);
            
        }
        
    currentorder=order;
    
     MaCommandeController.paiement=1;
        HomeController.macommande=1;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Home.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
       
        tableView.getScene().setRoot(root);
            //Paypal
            /* try {
        new ProcessBuilder("x-www-browser", "http://www.google.com").start();
    } catch (IOException e) {
        e.printStackTrace();
    }*/

    }
    

}
