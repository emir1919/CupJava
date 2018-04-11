/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.cupcake.entities.Category;
import edu.cupcake.entities.Product;
import edu.cupcake.entities.Subcategory;
import edu.cupcake.services.CategoryService;
import edu.cupcake.services.ProductService;
import edu.cupcake.services.SubcategoryService;
import edu.cupcake.services.Upload;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author yassi
 */
public class BrandManagementController implements Initializable {

    
    int currentBrandId = 1;
    
    
    @FXML
    private Button productManagementBtn;
    @FXML
    private JFXTextField pNameTF;
    @FXML
    private JFXTextField pPriceTF;
    @FXML
    private JFXSlider pReductionS;
    @FXML
    private Label reductionLbl;
    @FXML
    private ChoiceBox<Subcategory> pSubCatCB;
    @FXML
    private JFXButton addProductBtn;
    @FXML
    private JFXButton editProductBtn;
    @FXML
    private JFXButton deleteProductBtn;
    @FXML
    private JFXTextArea pDescTA;
    @FXML
    private SplitPane productManagementPane;
    
    
    @FXML
    private TableView<Product> productsTV;
    @FXML
    private TableColumn<?, ?> pNameCol;
    @FXML
    private TableColumn<?, ?> pPriceCol;
    @FXML
    private TableColumn<?, ?> pSalesCol;
    @FXML
    private TableColumn<?, ?> pRedCol;
    @FXML
    private TableColumn<?, ?> pDescCol;
    @FXML
    private TableColumn<?, ?> pRatingCol;
    @FXML
    private TableColumn<?, ?> pAddedCol;
    @FXML
    private JFXButton pClearBtn;
    @FXML
    private JFXButton pImageBtn;
    @FXML
    private ImageView pIV;
    @FXML
    private Label imgPathLbl;
    @FXML
    private Button InfoBrandButton;
    @FXML
    private Button BakeryButton;
    @FXML
    private Button map;
    @FXML
    private Button stat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        SubcategoryService service = new SubcategoryService();
        ObservableList<Subcategory> subcats = FXCollections.observableArrayList(service.getSubcategories());
        pSubCatCB.setItems(subcats);
        pSubCatCB.setValue(subcats.stream().findFirst().get());
        
        refreshProducts();
        
        productsTV.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        try{
            Product selected = productsTV.getSelectionModel().getSelectedItem();
            pNameTF.setText(selected.getName());
            pPriceTF.setText(String.valueOf(selected.getPrice()));
            pReductionS.setValue(selected.getReduction());
            pDescTA.setText(selected.getDescription());
            pSubCatCB.setValue(pSubCatCB.getItems().stream().
                    filter((Subcategory s) -> s.getId() == selected.getSubcategory_id()).
                    findAny().get());
            pIV.setImage(new Image("http://"+selected.getImage_name()));
            imgPathLbl.setText(selected.getImage_name());
            deleteProductBtn.disableProperty().set(false);
            editProductBtn.disableProperty().set(false);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        
        });
        
    }    

    
    

    @FXML
    private void addProduct(ActionEvent event) {
        try{
            if(Double.parseDouble(pPriceTF.getText())>0){
                String url = null;
                String url2 = null;
                String url3 ="";
                Subcategory subcategory = (Subcategory) pSubCatCB.getSelectionModel().getSelectedItem();
                
                if (!(imgPathLbl.getText().isEmpty())) {
                    url=imgPathLbl.getText();
                    url2 = "http://localhost/cupcake/uploads/"; 
                    url3= url2.concat(url.substring( url.lastIndexOf('/')+1, url.length() ));
       
                }
                String filePath = "";
                if (!(imgPathLbl.getText().isEmpty())) {
                    Upload u = new Upload();
                    File f = new File(url.replace("file:", ""));

                    filePath = u.UploadFile(f);
                    Image ii = new Image(url3);
                }
                Product p = new Product(
                        pNameTF.getText(),
                        Double.parseDouble(pPriceTF.getText()),
                        pDescTA.getText(),
                        0,
                        pReductionS.getValue(),
                        filePath,
                        0,
                        java.sql.Date.valueOf(LocalDate.now()),
                        java.sql.Date.valueOf(LocalDate.now()),
                        subcategory.getId(),
                        currentBrandId);

                ProductService service = new ProductService();
                service.addProduct(p);
                refreshProducts();
                String title = "Succès";
                String message = "Opération effectué avec succès";
                NotificationType notification = NotificationType.SUCCESS;
        
                TrayNotification tray = new TrayNotification();
                tray.setTitle(title);
                tray.setMessage(message);
                tray.setNotificationType(notification); 
                tray.showAndWait();
            }else{
                String title = "Erreur";
                String message = "Veuillez saisir un prix positif";
                NotificationType notification = NotificationType.ERROR;
        
                TrayNotification tray = new TrayNotification();
                tray.setTitle(title);
                tray.setMessage(message);
                tray.setNotificationType(notification); 
                tray.showAndWait();
            }
            
        }catch(Exception ex){
                String title = "Erreur";
                String message = "Impossible d'effectuer cette opération";
                NotificationType notification = NotificationType.ERROR;
        
                TrayNotification tray = new TrayNotification();
                tray.setTitle(title);
                tray.setMessage(message);
                tray.setNotificationType(notification); 
                tray.showAndWait();
        }
        
    }

    @FXML
    private void editProduct(ActionEvent event) {
        try{
            Subcategory subcategory = (Subcategory) pSubCatCB.getSelectionModel().getSelectedItem();
            Product currentProd = productsTV.getSelectionModel().getSelectedItem();
            
            currentProd.setName(pNameTF.getText());
            currentProd.setPrice(Double.parseDouble(pPriceTF.getText()));
            currentProd.setDescription(pDescTA.getText());
            currentProd.setReduction(pReductionS.getValue());
            currentProd.setUpdated_at(java.sql.Date.valueOf(LocalDate.now()));
            currentProd.setSubcategory_id(subcategory.getId());
            
            if(!currentProd.getImage_name().equals(imgPathLbl.getText())
                    && imgPathLbl.getText()!=null){
                
                String url = null;
                String url2 = null;
                String url3 = "";
                
                url=imgPathLbl.getText();
                url2 = "http://localhost/cupcake/uploads/"; 
                url3= url2.concat(url.substring( url.lastIndexOf('/')+1, url.length() ));
                Upload u = new Upload();
                File f = new File(url.replace("file:", ""));
                String filePath = u.UploadFile(f);
                Image ii = new Image(url3);
                currentProd.setImage_name(filePath);
                
            }
            
            ProductService service = new ProductService();
            service.editProduct(currentProd);
            refreshProducts();
            String title = "Succès";
            String message = "Opération effectué avec succès";
            NotificationType notification = NotificationType.SUCCESS;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndWait();
        }catch(Exception ex){
            String title = "Erreur";
            String message = "Impossible d'effectuer cette opération";
            NotificationType notification = NotificationType.ERROR;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndWait();
        }
    }

    @FXML
    private void deleteProduct(ActionEvent event) {
        try{
            ProductService service = new ProductService();
            service.deleteProduct(productsTV.getSelectionModel().getSelectedItem().getId());
            refreshProducts();
            String title = "Succès";
            String message = "Opération effectué avec succès";
            NotificationType notification = NotificationType.SUCCESS;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndWait();
        }catch(Exception ex){
            String title = "Erreur";
            String message = "Impossible d'effectuer cette opération";
            NotificationType notification = NotificationType.ERROR;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndWait();
        }
        
    }
    
    private void refreshProducts(){
        ProductService service = new ProductService();
        pNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        pPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        pDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        pRatingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        pRedCol.setCellValueFactory(new PropertyValueFactory<>("reduction"));
        pSalesCol.setCellValueFactory(new PropertyValueFactory<>("sales"));
        pAddedCol.setCellValueFactory(new PropertyValueFactory<>("added_at"));
        ObservableList<Product> list = FXCollections.observableArrayList(service.getProductsByBrand(currentBrandId));
        productsTV.setItems(list);
        clear();
    }
    
    private void clear(){
        pDescTA.clear();
        pNameTF.clear();
        pPriceTF.clear();
        pDescTA.clear();
        pReductionS.setValue(0);
        productsTV.getSelectionModel().clearSelection();
        deleteProductBtn.disableProperty().set(true);
        editProductBtn.disableProperty().set(true);
    }

    @FXML
    private void pClearAll(ActionEvent event) {
        clear();
    }

    @FXML
    private void pGetImage(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jpg File", "*.jpg","*.png"));
        try {
            File f = fc.showOpenDialog(null);

            if (f != null) {
                String backslash = System.getProperty("file.separator");
                String st = f.getAbsolutePath().replace(backslash, "/");
                String s = "file:" + st;
                Image i = new Image(s);
                pIV.setImage(i);

                imgPathLbl.setText(s);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void ShowBrand(ActionEvent event) {
    }

    @FXML
    private void ShowBakery(ActionEvent event) {
    }

    @FXML
    private void localisation(ActionEvent event) {
    }

    @FXML
    private void statistiques(ActionEvent event) {
    }

    @FXML
    private void showProductManagement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/BrandManagement.fxml"));
            Parent root = loader.load();
            productManagementBtn.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
