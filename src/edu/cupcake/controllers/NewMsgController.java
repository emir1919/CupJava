/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.cupcake.entities.Msg;
import edu.cupcake.services.MsgServices;
import edu.cupcake.services.UsersService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class NewMsgController implements Initializable {

    @FXML
    private JFXTextField Destinataire;
    @FXML
    private JFXTextField Objet;
    @FXML
    private JFXTextArea Contenu;
    @FXML
    private JFXButton AddButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Add(ActionEvent event) throws SQLException, IOException {
        UsersService us=new UsersService();
        MsgServices ms=new MsgServices();
        Msg m=new Msg();
        m.setBody(Contenu.getText());
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

// Using DateFormat format method we can create a string 
// representation of a date with the defined format.
        String reportDate = df.format(new java.util.Date().getTime());
        m.setDateEnvoi(reportDate);
        m.setEmetteur_id(cupcake.Cupcake.user.getId());
        m.setSubject(Objet.getText());
        m.setRecepteur_id(us.getUserByEmail(Destinataire.getText().toString()).getId());
        m.setLu(0);
        ms.AjouterMsg(m);
        
        
    }
    
}
