/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import edu.CupTest2.entities.Enseigne;
import edu.CupTest2.services.EnseigneServices;
import edu.CupTest2.utils.MyConnexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import edu.CupTest2.entities.Rating;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class BrandFrontController implements Initializable {

    @FXML
    private JFXButton brandbutton;
    @FXML
    private JFXListView<String> list;
    private ObservableList<String> items = FXCollections.observableArrayList();
    private Rating rating;
    @FXML
    private org.controlsfx.control.Rating rate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        list.setItems(items);

        Statement stmt = null;
        ResultSet rs = null;
        try {
            Connection con = MyConnexion.getInstance().getConection();

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Enseigne where id=1");

            while (rs.next()) {
                items.add(rs.getString(3));
                System.out.println(rs.getString(3));
            }
        } catch (SQLException e) {

        }
        rating = new Rating();

        rate.setPartialRating(true);
        rate.ratingProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                System.out.println(newValue.toString());
            }
        
        });
       
           
       System.out.println("aa");
        //yourratingtxt.setText(String.valueOf(rating.getRating()));
    }

    @FXML
    private void getAll(ActionEvent event) {
    }

}
