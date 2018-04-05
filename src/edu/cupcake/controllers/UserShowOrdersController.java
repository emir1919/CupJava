/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import edu.cupcake.entities.Adresses;
import edu.cupcake.entities.Orders;
import edu.cupcake.services.AdressesService;
import edu.cupcake.services.OrdersService;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import edu.cupcake.utils.Routing;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;


/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class UserShowOrdersController implements Initializable {

    @FXML
    private TableColumn<Orders, Date> Date;
    @FXML
    private TableColumn<Orders, String> Reference;
    @FXML
    private TableColumn<Orders, String> Adresse;
    @FXML
    private TableColumn<Orders, Float> Total;
    @FXML
    private TableColumn<Orders, String> Paiement;
    @FXML
    private TableView<Orders> tableView;
    
    List<Orders> listeorders;
            ObservableList<Orders> listViewOrders;
            
         public static Orders selectedorder;
    @FXML
    private JFXButton btnPayer;
    


    public Orders getSelectedorder() {
        return selectedorder;
    }

    public void setSelectedorder(Orders selectedorder) {
        this.selectedorder = selectedorder;
    }
         
            
            

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnPayer.setVisible(false);
        try {
            initCol();
        } catch (SQLException ex) {
            Logger.getLogger(UserShowOrdersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadData();
         
       
            /*PrinterJob job = PrinterJob.createPrinterJob();
            if(job != null){
            job.showPrintDialog(tableView.getScene().getWindow()); // Window must be your main Stage
            // job.printPage(yourNode);
            job.endJob();
            }*/
           /*  if (tableView.getSelectionModel().getSelectedItem().getPaymentState().equals("paid")) {
             System.err.println(tableView.getSelectionModel().getSelectedItem().getPaymentState());
         }*/
           tableView.setOnMouseClicked((event) -> {
               if (tableView.getSelectionModel().getSelectedItem().getPaymentState().equals("Non Payée"))
              {
                  btnPayer.setVisible(true);
              }
               else
               {
              btnPayer.setVisible(false);

               }
            });
    }    
    
     private void initCol() throws SQLException {
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        Reference.setCellValueFactory(new PropertyValueFactory<>("reference"));
        Total.setCellValueFactory(new PropertyValueFactory<>("amount"));
        Total.setText("Total (DT)");
        Paiement.setCellValueFactory(new PropertyValueFactory<>("PaymentState"));
        Adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        
         
        


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
            OrdersService sr = new OrdersService();
            listeorders = new ArrayList<>();
            listeorders = sr.getOrdersbyUserId(cupcake.Cupcake.user.getId());
            listViewOrders = FXCollections.observableArrayList(listeorders);
     
            tableView.setItems(listViewOrders);
             for (Orders listeorder : listeorders) {
              System.err.println(listeorder.getId());
          }
            initCol();
        } catch (Exception e) {
        }
        
         
        
    }

    @FXML
    private void showFacture(ActionEvent event) throws IOException {
         
        
 
      
        setSelectedorder(tableView.getSelectionModel().getSelectedItem());
        FXMLLoader lo = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/AfficherFacture.fxml"));
                            Parent root = lo.load();
                             AfficherFactureController pc=lo.getController();
                             pc.setSelected(getSelectedorder());
                             
        
    
                            // pc.txtEditNom.setText(""+pc.getSelected().getId());
                            Stage stage = new Stage(StageStyle.DECORATED);
                            stage.setTitle("Modification adresse");    

                           
                          stage.setResizable(false);
                            stage.setScene(new Scene(root));
                            stage.show();
        
        
        
    }

    @FXML
    private void PayerOrder(ActionEvent event) throws IOException {
                ValidationLivraisonCommandeController.currentorder=tableView.getSelectionModel().getSelectedItem();
 
        MaCommandeController.paiement=1;
        HomeController.macommande=1;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Home.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
       
        tableView.getScene().setRoot(root);
    }

   

      
 
      
      
      
      
      
    
    
}
