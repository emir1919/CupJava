/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.gui;

import com.jfoenix.controls.JFXListView;
import edu.CupTest2.entities.Msg;
import edu.CupTest2.services.MsgServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class SendedMsgController implements Initializable {

    @FXML
    private JFXListView<Msg> lm;
    private ObservableList<Msg> items = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          MsgServices ms=new MsgServices();
        List<Msg> lm1=new ArrayList<Msg>();
        try {
            lm1=ms.GetAllMsgByEmeteur((int)(long)main.user.getId());
        } catch (SQLException ex) {
            Logger.getLogger(MessagerieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=0;i<lm1.size();i++)
        {
        items.add(lm1.get(i));
        }
              lm.setItems(items);
    }    
    
}
