/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import edu.cupcake.services.UsersService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import edu.cupcake.utils.Routes;
import edu.cupcake.utils.Routing;

/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class MesPointsController implements Initializable {

    @FXML
    private JFXButton BTConvertir;
    
    @FXML
    private Label TxtNum;
    @FXML
    private Label txt1;
    @FXML
    private Label txt2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       TxtNum.setText(Integer.toString(cupcake.Cupcake.user.getPF()));
        
    }    

    @FXML
    private void Convert(ActionEvent event) throws IOException, SQLException {
        
          UsersService us = new UsersService();
          cupcake.Cupcake.Selected_id = cupcake.Cupcake.user.getPF();
          cupcake.Cupcake.user.setReduction(cupcake.Cupcake.user.getPF());
          cupcake.Cupcake.user.setPF(0);
          
          us.EditUserPF(cupcake.Cupcake.user);
        
        Parent root = null;
                                HomeController.afficherprofile=1;
                                ProfileController.convertirpf=1;
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
  TxtNum.getScene().setRoot(root);
        
          
        
        
        
    }
    
}
