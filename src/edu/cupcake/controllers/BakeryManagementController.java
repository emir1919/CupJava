/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import edu.cupcake.entities.ObservableStock;
import edu.cupcake.entities.Product;
import edu.cupcake.entities.Stock;
import edu.cupcake.entities.Subcategory;
import edu.cupcake.services.ProductService;
import edu.cupcake.services.StockService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author yassi
 */
public class BakeryManagementController implements Initializable {
    
    private int currentBakery = 0;

    
    
    @FXML
    private SplitPane stockManagementPane;
    @FXML
    private ChoiceBox<Product> sProductCB;
    @FXML
    private JFXButton addStockBtn;
    @FXML
    private JFXButton editStockBtn;
    @FXML
    private JFXButton deleteStockBtn;
    @FXML
    private JFXButton sClearBtn;
    @FXML
    private TableView<ObservableStock> stocksTV;
    @FXML
    private TableColumn<?, ?> pNameCol;
    @FXML
    private TableColumn<?, ?> sQuantityCol;
    @FXML
    private TableColumn<?, ?> sSalesCol;
    @FXML
    private JFXSlider sQuantitySlider;
    @FXML
    private Button stockManagementBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        stockManagementPane.setVisible(true);
        ProductService service = new ProductService();
        ObservableList<Product> list = FXCollections.observableArrayList(service.getProductsByBrand(cupcake.Cupcake.user.getId()));
        sProductCB.setItems(list);
        sProductCB.setValue(list.stream().findFirst().get());
        RefreshStock();
        
        stocksTV.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            try {
                ObservableStock selected = stocksTV.getSelectionModel().getSelectedItem();
                sQuantitySlider.setValue(selected.getQte());
                sProductCB.setValue(sProductCB.getItems().stream().
                    filter((Product s) -> s.getId() == selected.getProduct_id()).
                    findAny().get());
                deleteStockBtn.disableProperty().set(false);
                editStockBtn.disableProperty().set(false);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }

        });
    }    



    @FXML
    private void sClearAll(ActionEvent event) {
        cClear();
    }
    
    private void RefreshStock(){
        StockService service = new StockService();
        pNameCol.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        sQuantityCol.setCellValueFactory(new PropertyValueFactory<>("qte"));
        sSalesCol.setCellValueFactory(new PropertyValueFactory<>("sales"));
        ObservableList<ObservableStock> list = FXCollections.observableArrayList(service.getObsStocks(cupcake.Cupcake.user.getId()));
        stocksTV.setItems(list);
        cClear();
    }

    private void cClear() {
        sQuantitySlider.setValue(0);
        stocksTV.getSelectionModel().clearSelection();
        
        deleteStockBtn.setDisable(true);
        editStockBtn.setDisable(true);
    }

    @FXML
    private void addStock(ActionEvent event) {
        try{
                Product product = (Product) sProductCB.getSelectionModel().getSelectedItem();
                boolean res = stocksTV.getItems().stream().anyMatch((ObservableStock s) -> s.getProduct_id() == product.getId());
            
                if(!res){
                    Stock stock = new Stock((int)sQuantitySlider.getValue(), product.getId(), cupcake.Cupcake.user.getId(), 0);
                    StockService service = new StockService();
                    service.addStock(stock);
                    RefreshStock();
                    
                    String title = "Succès";
                    String message = "Opération effectué avec succès";
                    NotificationType notification = NotificationType.SUCCESS;
                    TrayNotification tray = new TrayNotification();
                    tray.setTitle(title);
                    tray.setMessage(message);
                    tray.setNotificationType(notification); 
                    tray.showAndWait();
                }else{
                    String title = "Erreur";
                    String message = "Le produit existe déjà dans le stock";
                    NotificationType notification = NotificationType.ERROR;

                    TrayNotification tray = new TrayNotification();
                    tray.setTitle(title);
                    tray.setMessage(message);
                    tray.setNotificationType(notification); 
                    tray.showAndWait();
                }
                
                
        }catch(Exception ex){
            String title = "Erreur";
            String message = "Impossible d'effectuer cette opération";
            NotificationType notification = NotificationType.ERROR;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndWait();
        }
    }

    @FXML
    private void editStock(ActionEvent event) {
        try{
            Product product = (Product) sProductCB.getSelectionModel().getSelectedItem();
            //boolean res = stocksTV.getItems().stream().anyMatch((ObservableStock s) -> s.getProduct_id() == product.getId());
            StockService service = new StockService();
            ObservableStock selected = stocksTV.getSelectionModel().getSelectedItem();

            Stock s = new Stock();
            s.setBakery_id(cupcake.Cupcake.user.getId());
            s.setProduct_id(product.getId());
            s.setSales(selected.getSales());
            s.setQte((int) sQuantitySlider.getValue());
            service.editStock(s);
            RefreshStock();
            
            String title = "Succès";
            String message = "Opération effectué avec succès";
            NotificationType notification = NotificationType.SUCCESS;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndWait();
            /*if(!res){
                
            }else{
                System.out.println("already in stock");
            }*/
            
        }catch(Exception ex){
            String title = "Erreur";
            String message = "Impossible d'effectuer cette opération";
            NotificationType notification = NotificationType.ERROR;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndWait();
        }
    }

    @FXML
    private void deleteStock(ActionEvent event) {
        try{
            StockService service = new StockService();
            ObservableStock selected = stocksTV.getSelectionModel().getSelectedItem();
            service.deleteStock(selected.getProduct_id(), selected.getBakery_id());
            RefreshStock();
            String title = "Succès";
            String message = "Opération effectué avec succès";
            NotificationType notification = NotificationType.SUCCESS;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndWait();
        }catch(Exception ex){
            String title = "Erreur";
            String message = "Impossible d'effectuer cette opération";
            NotificationType notification = NotificationType.ERROR;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndWait();
        }
    }

    @FXML
    private void showStockManagement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/BakeryManagement.fxml"));
            Parent root = loader.load();
            stockManagementBtn.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
    
}
