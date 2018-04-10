/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import edu.cupcake.entities.ObservableProduct;
import edu.cupcake.entities.Panier;
import edu.cupcake.entities.Product;
import edu.cupcake.services.ProductService;
import edu.cupcake.services.StockService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author yassi
 */
public class ProductsFrontController implements Initializable {

    @FXML
    private JFXTextField searchTF;
    @FXML
    private JFXButton searchBtn;
    @FXML
    private JFXSlider maxPriceSL;
    @FXML
    private JFXSlider minPriceSL;
    @FXML
    private JFXRadioButton pCherR;
    @FXML
    private ToggleGroup orderBy;
    @FXML
    private JFXRadioButton mCherR;
    @FXML
    private JFXRadioButton pPopR;
    @FXML
    private JFXRadioButton mPopR;
    @FXML
    private JFXButton resetBtn;
    @FXML
    private JFXButton showProductsBtn;
    @FXML
    private JFXButton showCatsBtn;
    @FXML
    private BorderPane mainPane;
    @FXML
    private JFXListView<ObservableProduct> suggLV;
    static  int productdetail=0;
   

    @FXML
    private void searchByName(ActionEvent event) {
        
        if(!searchTF.getText().equals("")){
            currentList = currentList.filtered(
                    (ObservableProduct p) -> p.getName().toUpperCase().startsWith(searchTF.getText().toUpperCase()));
        }
        
        if(maxPriceSL.getValue() != 0 || minPriceSL.getValue() != 0){
            currentList = currentList.filtered((ObservableProduct p) -> p.getPrice() >= minPriceSL.getValue() &&
                    p.getPrice() <= maxPriceSL.getValue());
        }
        
        JFXRadioButton selected = (JFXRadioButton) orderBy.getSelectedToggle();
        String sort = selected.getText();
        
        switch(sort){
            case "Plus cher":
                currentList = currentList.sorted((ObservableProduct p1, ObservableProduct p2) -> (int)(p2.getPrice() - p1.getPrice()));
                break;
            case "Moins cher":
                currentList = currentList.sorted((ObservableProduct p1, ObservableProduct p2) -> (int)(p1.getPrice() - p2.getPrice()));
                break;
            case "Plus populaire":
                currentList = currentList.sorted((ObservableProduct p1, ObservableProduct p2) -> (int)(p2.getRating() - p1.getRating()));
                break;
            case "Moins populaire":
                currentList = currentList.sorted((ObservableProduct p1, ObservableProduct p2) -> (int)(p1.getRating() - p2.getRating()));
                break;
            default:break;
        }
        
        productsLV.setItems(currentList);
        currentList = list;
    }

    @FXML
    private void reset(ActionEvent event) {
        reload();
        maxPriceSL.setValue(2500.0);
        minPriceSL.setValue(0.0);
        maxPriceSL.setMin(0.0);
        minPriceSL.setMax(2500.0);
        searchTF.setText("");
        orderBy.selectToggle(pPopR);
    }

 
    

    static class ProductCell extends JFXListCell<ObservableProduct>{
        
        HBox hbox = new HBox();
        VBox vbox = new VBox();
        HBox buttonContainer = new HBox();
        Pane pane = new Pane();
        
        JFXButton detailButton = new JFXButton("Détails");
        JFXButton button = new JFXButton("To Cart");
        Label nameLbl = new Label();
        Label priceLbl = new Label();
        Label subCatLbl = new Label();
        Label brandLbl = new Label();
        Label ratingLbl = new Label();
        Rating ratingB = new Rating();
        ImageView pIV = new ImageView();
        ObservableProduct lastItem;
        
        
        public ProductCell(){
            super();
            
            buttonContainer.getChildren().addAll(detailButton, button);
            vbox.getChildren().addAll(nameLbl, priceLbl, subCatLbl, brandLbl, ratingB, buttonContainer);
            hbox.getChildren().addAll(pIV, vbox);
            
            VBox.setVgrow(pane, Priority.ALWAYS);
            
            
            
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    StockService s = new StockService();
                    s.getStockByProduct(lastItem.getId());
                    if(cupcake.Cupcake.Panier.stream().anyMatch((Panier p) -> p.getProduit().getId()==lastItem.getId())){
                        Panier pan = cupcake.Cupcake.Panier.stream().filter((Panier p)->p.getProduit().getId()==lastItem.getId()).findFirst().get();
                        Panier newPan = pan;
                        newPan.setQte(pan.getQte()+1);
                        cupcake.Cupcake.Panier.set(cupcake.Cupcake.Panier.indexOf(pan), newPan);
                    }else{
                        Panier p = new Panier();
                        p.setProduit(lastItem);
                        p.setQte(1);
                        cupcake.Cupcake.Panier.add(p);
                    } 
                }
            });
            
            
            
            
            detailButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/ProductDetail.fxml"));
                 
                    Parent root = loader.load();
                    ProductDetailController ctrl = loader.getController();
                    ctrl.setBrandLbl(lastItem.getBrand());
                    ctrl.setPriceLbl(lastItem.getPrice());
                    ctrl.setPromoLbl(lastItem.getReduction());
                    ctrl.setPriceAfterLbl(lastItem.getPrice(), lastItem.getReduction());
                    ctrl.setCatLbl(lastItem.getSubCat());
                    ctrl.setNameLbl(lastItem.getName());
                    ctrl.setpRating(lastItem.getRating());
                    ctrl.setpIV(lastItem.getImage_name());
                    ctrl.setId(lastItem.getId());
                    ctrl.setSubcatId(lastItem.getSubcategory_id());
                    button.getScene().setRoot(root);
                } catch (IOException ex) {
                    Logger.getLogger(ProductsFrontController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            });
        }

        @Override
        protected void updateItem(ObservableProduct item, boolean empty) {
            super.updateItem(item, empty);
            
            setText(null);
            setGraphic(null);
            if(item != null && !empty){
                buttonContainer.setId("buttonContainer");
                vbox.setId("rootContainer");
                nameLbl.setText("Nom : " + item.getName());
                priceLbl.setText("Prix : " + item.getPrice());
                subCatLbl.setText("Subcategory : " + item.getSubCat());
                brandLbl.setText("Enseigne : " + item.getBrand());
                ratingB.setRating(item.getRating());
                ratingB.setDisable(true);
                ratingB.setMax(5);
                pIV.setImage(new Image("http://" +item.getImage_name(), 250, 250, false, false));
                setGraphic(hbox);
                lastItem = item;
            }
        }
        
        
    }
    
    
    static class SuggCell extends JFXListCell<ObservableProduct>{
        VBox vbox = new VBox();
        
        Pane pane = new Pane();
        
        JFXButton checkButton = new JFXButton("Détails");
      
        ImageView pIV = new ImageView();
        
        ObservableProduct lastItem;
        
        
        public SuggCell(){
            super();
            vbox.getChildren().addAll(pIV, checkButton);
            
            
            VBox.setVgrow(pane, Priority.ALWAYS);
            
            checkButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/ProductDetail.fxml"));
                    Parent root = loader.load();
                    ProductDetailController ctrl = loader.getController();
                    ctrl.setBrandLbl(lastItem.getBrand());
                    ctrl.setCatLbl(lastItem.getSubCat());
                    ctrl.setNameLbl(lastItem.getName());
                    ctrl.setPriceLbl(lastItem.getPrice());
                    ctrl.setPromoLbl(lastItem.getReduction());
                    ctrl.setPriceAfterLbl(lastItem.getPrice(), lastItem.getReduction());
                    ctrl.setpRating(lastItem.getRating());
                    ctrl.setpIV(lastItem.getImage_name());
                    ctrl.setId(lastItem.getId());
                    ctrl.setSubcatId(lastItem.getSubcategory_id());
                    pIV.getScene().setRoot(root);
                } catch (IOException ex) {
                    Logger.getLogger(ProductsFrontController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            });
        }

        @Override
        protected void updateItem(ObservableProduct item, boolean empty) {
            super.updateItem(item, empty);
            
            setText(null);
            setGraphic(null);
            if(item != null && !empty){
                checkButton.setText(item.getName());
                pIV.setImage(new Image("http://" +item.getImage_name(), 250, 250, false, false));
                setGraphic(vbox);
                lastItem = item;
            }
        }
        
        
    }
    
    @FXML
    private JFXListView<ObservableProduct> productsLV;
    
    ObservableList<ObservableProduct> list = FXCollections.observableArrayList ();
    ObservableList<ObservableProduct> currentList = FXCollections.observableArrayList ();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        minPriceSL.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                maxPriceSL.minProperty().setValue(new_val);
                    
            }
        });
        
        maxPriceSL.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                minPriceSL.maxProperty().setValue(new_val);
                    
            }
        });
        
        productsLV.setCellFactory(param -> new ProductCell());
        suggLV.setCellFactory(param -> new SuggCell());
        
        reload();
        
        
        if (productdetail==1) {
            
            
        }
    }    
    
    private void reload(){
        ProductService service = new ProductService();
        
        list = FXCollections.observableArrayList (service.getObsProducts());
        list = list.sorted((ObservableProduct p1, ObservableProduct p2) -> (int)(p2.getRating() - p1.getRating()));
        currentList = list.sorted((ObservableProduct p1, ObservableProduct p2) -> (int)(p2.getRating() - p1.getRating()));
        productsLV.setItems(currentList);
        suggLV.setItems(FXCollections.observableArrayList(service.getObsMostSoldProducts()));
    }

    
}
