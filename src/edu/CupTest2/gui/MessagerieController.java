/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import edu.CupTest2.entities.Msg;
import edu.CupTest2.services.MsgServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class MessagerieController implements Initializable {

    @FXML
    private JFXButton ReceiveButton;
    @FXML
    private JFXButton sendButton;
    @FXML
    private JFXButton NewButton;
    @FXML
    public Pane content2;
    @FXML
    private JFXListView<Msg> listmsg;
    private ObservableList<Msg> items = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MsgServices ms=new MsgServices();
        List<Msg> lm=new ArrayList<Msg>();
        try {
            lm=ms.GetAllMsgByRecepteur((int)(long)main.user.getId());
        } catch (SQLException ex) {
            Logger.getLogger(MessagerieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=0;i<lm.size();i++)
        {
        items.add(lm.get(i));
        }
              listmsg.setItems(items);

    }    

    @FXML
    private void Receive(ActionEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/CupTest2/gui/Messagerie.fxml"));

        content2.getChildren().setAll(pane);
    }

    @FXML
    private void sended(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/CupTest2/gui/SendedMsg.fxml"));

        content2.getChildren().setAll(pane);
    }

    @FXML
    private void New(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/CupTest2/gui/NewMsg.fxml"));

        content2.getChildren().setAll(pane);
    }
    
}
