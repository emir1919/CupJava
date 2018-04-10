/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXListView;
import edu.cupcake.entities.ObservableProduct;
import edu.cupcake.services.ProductService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author yassi
 */
public class AccueilController implements Initializable {

    @FXML
    private JFXListView<ObservableProduct> prodLV;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ProductService s = new ProductService();
        ObservableList<ObservableProduct> list = FXCollections.observableArrayList (s.getObsMostPopProducts());
        prodLV.setItems(list);
        prodLV.setCellFactory(param -> new ProductsFrontController.SuggCell());
    }    
    
}
