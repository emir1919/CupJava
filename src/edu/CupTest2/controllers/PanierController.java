/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.itextpdf.text.Image;
import com.jfoenix.controls.JFXListView;
import edu.cupcake.entities.Line_Order;
import edu.cupcake.entities.Panier;
import edu.cupcake.entities.Product;
import edu.cupcake.services.ProductService;
import edu.cupcake.utils.Routing;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class PanierController implements Initializable {

    @FXML
    private GridPane grid;
    @FXML
    private ColumnConstraints rowProduit1;
    @FXML
    private ColumnConstraints rowPrix1;
    @FXML
    private ColumnConstraints rowQte1;
    @FXML
    private ColumnConstraints rowTotal1;
    @FXML
    private Text txtPrixTotal;
    

    /**
     * Initializes the controller class.
     */ 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Iterator<Panier> it = cupcake.Cupcake.Panier.iterator();

                    ObservableList<Panier> list = FXCollections.observableArrayList();
                    int i=0;
            double total=0;
           
            while (it.hasNext()) {
            Panier next = it.next();
      if (next.getQte()==0) {
                        it.remove();
                        
                    }
    Text pname = new Text(""+next.getProduit().getName());
            Text pprix = new Text(""+next.getProduit().getPrice()+" DT");
            Text pqte = new Text(""+next.getQte());
            Text ptotal = new Text(""+next.getProduit().getPrice()*next.getQte()+" DT");
    
       
            Button plus = new Button("+");
            Button moins = new Button("-");

            plus.setOnAction((event) -> {
                try {
                    addproduit(next);
                    
                   HomeController.afficherpanier=1;
                    Parent root = FXMLLoader.load(getClass().getResource(Routing.HOME));
                    
                    grid.getScene().setRoot(root);
                } catch (IOException ex) {
                    Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            
            moins.setOnAction((event) -> {
                try {
                    removeproduit(next);
                    

                   HomeController.afficherpanier=1;
                    Parent root = FXMLLoader.load(getClass().getResource(Routing.HOME));
                    grid.getScene().setRoot(root);
                } catch (IOException ex) {
                    Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            
            grid.add(moins, 1, i);
            grid.add(plus, 0, i);

                 grid.add(pname, 2, i);

                    grid.add(pprix, 3, i);
                    grid.add(pqte, 4, i);
                    grid.add(ptotal, 5, i);

                    grid.setAlignment(Pos.CENTER);
                    i++;
                  total=next.getQte()*next.getProduit().getPrice()+total;

        }
                        txtPrixTotal.setText(""+total);



    }

   private void addproduit(Panier p)
   {
       p.setQte(p.getQte()+1);
      
   }
   private void removeproduit(Panier p)
   {
       p.setQte(p.getQte()-1);
   }

    @FXML
    private void PasserCommander(ActionEvent event) throws IOException {
        MaCommandeController.validationpanier=1;
        HomeController.macommande=1;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Home.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
       
        grid.getScene().setRoot(root);
    }


}
