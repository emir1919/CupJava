/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import edu.cupcake.entities.Line_Order;
import edu.cupcake.entities.Orders;
import edu.cupcake.entities.Product;
import edu.cupcake.entities.Users;
import edu.cupcake.services.Line_OrderService;
import edu.cupcake.services.ProductService;
import edu.cupcake.services.UsersService;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;
import java.util.Date;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class AfficherFactureController implements Initializable {

    
        private Orders selected;
    @FXML
    private Text txtReference;
    @FXML
    private Text txtDate;
    @FXML
    private VBox VBoxInfoPersonel;
    @FXML
    private Text txtNom;
    @FXML
    private Text txtPrenom;
    @FXML
    private Text txtAdresse;
    @FXML
    private Text txtTel;
    @FXML
    private Text txtStatus;
    @FXML
    private GridPane grid;
    @FXML
    private Text txtPrixTotal;
    @FXML
    private AnchorPane FacturePane;
    @FXML
    private ColumnConstraints rowProduit1;
    @FXML
    private ColumnConstraints rowPrix1;
    @FXML
    private ColumnConstraints rowQte1;
    @FXML
    private ColumnConstraints rowTotal1;
    @FXML
    private Text txtVille;

    public Orders getSelected() {
        return selected;
    }

    public void setSelected(Orders selected) {
        this.selected = selected;
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                setSelected(UserShowOrdersController.selectedorder);
                
                txtReference.setText(selected.getReference());
                txtDate.setText(""+selected.getDate());
                UsersService usr = new UsersService();
                Users user = new Users();
            try {
                user=usr.getUserById(selected.getUtilisateur_id());
            } catch (SQLException ex) {
                Logger.getLogger(AfficherFactureController.class.getName()).log(Level.SEVERE, null, ex);
            }
                txtNom.setText(user.getFirstname());
                txtPrenom.setText(user.getLastname());
                txtAdresse.setText(selected.getAdresse());
                txtTel.setText(""+cupcake.Cupcake.user.getPhonenumber());
                txtStatus.setText(selected.getPaymentState());
                
               Line_OrderService linesr = new Line_OrderService();
               List<Line_Order> lineorders = new ArrayList<Line_Order>();
            try {
                lineorders=linesr.getLineOrdersbyOrderId(selected.getId());
            } catch (SQLException ex) {
                Logger.getLogger(AfficherFactureController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Iterator<Line_Order> it = lineorders.iterator();
            ObservableList<Line_Order> list = FXCollections.observableArrayList();
            int i=0;
            double total=0;
            while (it.hasNext()) {
            Line_Order next = it.next();
                    System.out.println(next.getId());
            Text pid = new Text(""+next.getProduct_id());
                ProductService psr = new ProductService();
                Product product = new Product();
                product=psr.getProductById(next.getProduct_id());
            Text pname = new Text(""+product.getName());
            Text pprix = new Text(""+product.getPrice()+" DT");
            Text pqte = new Text(""+next.getQte());
            Text ptotal = new Text(""+product.getPrice()*next.getQte());


            
            total=next.getQte()*product.getPrice()+total;
                    grid.add(pname, 0, i);
                    grid.add(pprix, 1, i);
                    grid.add(pqte, 2, i);
                    grid.add(ptotal, 3, i);

                    grid.setAlignment(Pos.CENTER);
                    i++;
        }
            txtPrixTotal.setText(""+total+" DT");
         
                
            
        }
    
    
    
    String chemin = "/home/berrahal/NetBeansProjects/" + "stat.pdf";

    @FXML
    private void pdf(ActionEvent event) throws IOException, DocumentException {
        Document d = new Document();
        SnapshotParameters s = new SnapshotParameters();
        
        WritableImage i = new WritableImage((int) FacturePane.getWidth(), (int) FacturePane.getHeight());
        WritableImage img = FacturePane.snapshot(new SnapshotParameters(), null);
       
        String url = "snapshot" + new Date().getTime() + ".png";
         WritableImage writableImage = 
            new WritableImage((int)FacturePane.getWidth(), (int)FacturePane.getHeight()+150);
        FacturePane.getScene().snapshot(writableImage);
        System.out.println(writableImage.getWidth()+" | "+writableImage.getHeight());
        File output = new File(url);
        ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", output);

        PdfWriter.getInstance(d, new FileOutputStream(chemin));

        d.open();
        Image is = Image.getInstance(url);
        d.add(is);
        d.close();
        
            
            ((Stage) FacturePane.getScene().getWindow()).close();
            //s
    }


    }    
    
    
   
    

