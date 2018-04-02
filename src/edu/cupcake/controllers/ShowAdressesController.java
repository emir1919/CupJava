/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;
import edu.cupcake.entities.Adresses;
import edu.cupcake.services.AdressesService;
import edu.cupcake.utils.Routing;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.text.Document;
import javax.swing.text.html.HTML;

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class ShowAdressesController implements Initializable {

    // add adresse
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtTel;
    @FXML
    private TextField txtAdresse;
    @FXML
    private TextField txtCp;
    @FXML
    private TextField txtPays;
    @FXML
    private TextField txtVille;
    @FXML
    private TextField txtComplement;
    
    @FXML
    private TableColumn<Adresses, String> Nom;
    @FXML
    private TableColumn<Adresses, String> Prenom;
    @FXML
    private TableColumn<Adresses, String> Tel;
    @FXML
    private TableColumn<Adresses, String> Cp;
    @FXML
    private TableColumn<Adresses, String> Pays;
    @FXML
    private TableColumn<Adresses, String> Ville;
    @FXML
    private TableColumn<Adresses, String> Adresse;
    @FXML
    private TableColumn<Adresses, String> Complement;
    @FXML
    private TableView<Adresses> tableView;
        List<Adresses> listAdresses;
            ObservableList<Adresses> listViewAdresses;

    public TableView<Adresses> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<Adresses> tableView) {
        this.tableView = tableView;
    }
            
            
            public static Adresses selectedadresse;

    public Adresses getSelectedadresse() {
        return selectedadresse;
    }

    public void setSelectedadresse(Adresses selectedadresse) {
        this.selectedadresse = selectedadresse;
    }

    
    
    //Edit Adresse        
    @FXML
    private VBox VBoxInfoPersonel;
    @FXML
    private JFXTextField txtEditNom;
    @FXML
    private JFXTextField txtEditPrenom;
    @FXML
    private JFXTextField txtEditTel;
    @FXML
    private JFXTextField txtEditAdresse;
    @FXML
    private JFXTextField txtEditCp;
    @FXML
    private JFXTextField txtEditPays;
    @FXML
    private JFXTextField txtEditVille;
    @FXML
    private JFXTextField txtEditComplement;


    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

     
    public void addAdresse(ActionEvent event) throws IOException {

     FXMLLoader lo = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/AjouterAdresse.fxml"));
                            Parent root = lo.load();
                            Parent loader = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/AjouterAdresse.fxml"));  
                            Stage stage = new Stage(StageStyle.DECORATED);
                            stage.setTitle("Ajout adresse");    

                           
                          stage.setResizable(false);
                            stage.setScene(new Scene(root));
                            stage.show();
        
        

    } 
    
    @FXML
    public void ajouteradresse(ActionEvent event)  throws SQLException, IOException {

            AdressesService aservice = new AdressesService();
            Adresses adresse = new Adresses(cupcake.Cupcake.user.getId(), txtNom.getText(), txtPrenom.getText(), txtTel.getText(), txtAdresse.getText(), txtCp.getText(), txtPays.getText(), txtVille.getText(), txtComplement.getText());
            aservice.ajouterAdresse(adresse);
            System.out.println(adresse.toString());
            
            ((Stage) txtNom.getScene().getWindow()).close();
            
            

    } 
    
    public void refreshtable()
    {
        this.listViewAdresses.clear();
        loadData();
       

    }
    
    
    @FXML
    private void editAdresse(ActionEvent event) throws IOException, SQLException {
        
        
        
        
        
 
      
        setSelectedadresse(tableView.getSelectionModel().getSelectedItem());
        System.out.println("Show:"+getSelectedadresse());
        FXMLLoader lo = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/EditAdresse.fxml"));
                            Parent root = lo.load();
                             EditAdresseController pc=lo.getController();
                             pc.setSelected(getSelectedadresse());
                             AdressesService adr= new AdressesService();
                             
        Adresses adresses = adr.getAdressebyId(getSelectedadresse().getId());
        
    
                            // pc.txtEditNom.setText(""+pc.getSelected().getId());
                            Stage stage = new Stage(StageStyle.DECORATED);
                            stage.setTitle("Modification adresse");    

                           
                          stage.setResizable(false);
                            stage.setScene(new Scene(root));
                            stage.show();
        
        
        
      
        
        
    }
    
    
    @FXML
    private void supprimerAdresse(ActionEvent event) throws IOException, SQLException {
        
        
        
        
        
         
      
        setSelectedadresse(tableView.getSelectionModel().getSelectedItem());
       Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Supprimer une adresse");
alert.setHeaderText("Vous voulez supprimer une adresse.");
alert.setContentText("Etes vous sur ?");

Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    AdressesService sr = new AdressesService();
    sr.supprimerAdresse(getSelectedadresse().getId());
} else {
    alert.close();
}
        
        
      
        
        
    }
}
