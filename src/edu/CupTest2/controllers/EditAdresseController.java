/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXTextField;
import cupcake.Cupcake;
import edu.cupcake.entities.Adresses;
import edu.cupcake.services.AdressesService;
import edu.cupcake.utils.Routing;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class EditAdresseController implements Initializable {

    @FXML
    private VBox VBoxInfoPersonel;
    @FXML
    public JFXTextField txtEditNom;

    @FXML
    public JFXTextField txtEditPrenom;
    @FXML
    public JFXTextField txtEditTel;
    @FXML
    public JFXTextField txtEditAdresse;
    @FXML
    public JFXTextField txtEditCp;
    @FXML
    public JFXTextField txtEditPays;
    @FXML
    public JFXTextField txtEditVille;
    @FXML
    public JFXTextField txtEditComplement;
    
    private Adresses selected;

    public Adresses getSelected() {
        return selected;
    }

    public void setSelected(Adresses selected) {
        this.selected = selected;
    }
    

    /**
     * Initializes the controller class.
     */
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        setSelected(ShowAdressesController.selectedadresse);
        txtEditNom.setText(selected.getNom());
        txtEditPrenom.setText(selected.getPrenom());
        txtEditAdresse.setText(selected.getAdresse());
        txtEditComplement.setText(selected.getComplement());
        txtEditCp.setText(selected.getCp());
        txtEditVille.setText(selected.getVille());
        txtEditTel.setText(selected.getTelephone());
        txtEditPays.setText(selected.getPays());
        
        
}

    @FXML
    private void modifieradresse(ActionEvent event) throws IOException, SQLException {
        AdressesService sr = new AdressesService();

            Adresses adresse = new Adresses( Cupcake.user.getId(), txtEditNom.getText(), txtEditPrenom.getText(), txtEditTel.getText(), txtEditAdresse.getText(), txtEditCp.getText(), txtEditPays.getText(), txtEditVille.getText(), txtEditComplement.getText());
            sr.modifierAdresse(adresse, selected.getId());
        
            ((Stage) txtEditAdresse.getScene().getWindow()).close();
            
            HomeController.afficherprofile=1;
            ProfileController.afficheradresses=1;
                    
                            Parent root = FXMLLoader.load(getClass().getResource(Routing.HOME));
                            Stage stage = new Stage(StageStyle.DECORATED);

                           stage.setMaximized(true);
                            stage.setScene(new Scene(root));
                            stage.show();
                    
            
          /*  
              FXMLLoader lo = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/ShowAdresses.fxml"));
                            Parent root = lo.load();
                         
    
                            // pc.txtEditNom.setText(""+pc.getSelected().getId());
                            Stage stage = new Stage(StageStyle.DECORATED);

                            stage.setScene(new Scene(root));
                            stage.show();*/
          
   
    }
    


}