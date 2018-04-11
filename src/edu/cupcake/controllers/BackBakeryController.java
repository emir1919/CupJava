/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import static edu.cupcake.controllers.BackBrandController.localisation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author yassi
 */
public class BackBakeryController implements Initializable {

    @FXML
    private Button showCommandsBtn;
    @FXML
    private AnchorPane content;
    @FXML
    private Button stockManagementBtn;
    
    public static int stockMan = 0;
    public static int commandsMan = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (stockMan != 0) {
            AnchorPane pane1;
            try {
                pane1 = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/BakeryManagement.fxml"));
                content.getChildren().setAll(pane1);

            } catch (IOException ex) {
                Logger.getLogger(BackBakeryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            stockMan = 0;
        }
        
        if (commandsMan != 0) {
            AnchorPane pane1;
            try {
                pane1 = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/BakeryOrders.fxml"));
                content.getChildren().setAll(pane1);

            } catch (IOException ex) {
                Logger.getLogger(BackBakeryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            commandsMan = 0;
        }
    }    


    @FXML
    private void showCommands(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/BakeryOrders.fxml"));

        content.getChildren().setAll(pane);
    }

    @FXML
    private void showStockManagement(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/BakeryManagement.fxml"));

        content.getChildren().setAll(pane);
    }
    
}
