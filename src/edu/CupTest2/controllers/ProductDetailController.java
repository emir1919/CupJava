/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import edu.cupcake.entities.ObservableProduct;
import edu.cupcake.entities.Product;
import edu.cupcake.entities.Review;
import edu.cupcake.services.ProductService;
import edu.cupcake.services.ReviewService;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class ProductDetailController implements Initializable {

    @FXML
    private BorderPane mainPane;

    
    @FXML
    private JFXButton showProductsBtn;
    @FXML
    private JFXButton showCatsBtn;
    @FXML
    private ImageView pIV;
    @FXML
    private Label nameLbl;
    @FXML
    private Label priceLbl;
    @FXML
    private Label brandLbl;
    @FXML
    private Label catLbl;
    @FXML
    private JFXListView<ObservableProduct> suggLV;
    @FXML
    private Label promoLbl;
    @FXML
    private Label priceAfterLbl;

    public Label getPromoLbl() {
        return promoLbl;
    }

    public void setPromoLbl(double promo) {
        this.promoLbl.setText(Math.round(promo) + " %");
    }

    public Label getPriceAfterLbl() {
        return priceAfterLbl;
    }

    public void setPriceAfterLbl(double prx, double promo) {
        double newPrx = prx-prx*(promo/100);
        this.priceAfterLbl.setText(String.valueOf(Math.round(newPrx)));
    }

    public Label getPriceLbl() {
        return priceLbl;
    }

    public void setPriceLbl(double prix) {
        this.priceLbl.setText(String.valueOf(prix));
    }
    
    private int id;
    private int subcatId;
    @FXML
    private JFXTextArea commentTF;
    @FXML
    private JFXButton loadBtn;
    @FXML
    private JFXListView<Review> reviewsLV;
    @FXML
    private JFXButton addCommentBtn;
    @FXML
    private HBox ratingHbox;
    @FXML
    private Rating pRating;
    @FXML
    private Rating ratingB;

    public Rating getpRating() {
        return pRating;
    }

    public void setpRating(double rating) {
        this.pRating.setRating(rating);
    }
    
   


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubcatId() {
        return subcatId;
    }

    public void setSubcatId(int subcatId) {
        
        this.subcatId = subcatId;
    }

    public ImageView getpIV() {
        return pIV;
    }

    public void setpIV(String url) {
        this.pIV.setImage(new Image("http://" +url, 250, 250, false, false));
    }

    public Label getNameLbl() {
        return nameLbl;
    }

    public void setNameLbl(String txt) {
        this.nameLbl.setText(txt);
    }

    

    public Label getBrandLbl() {
        return brandLbl;
    }

    public void setBrandLbl(String txt) {
        this.brandLbl.setText(txt);
    }

    public Label getCatLbl() {
        return catLbl;
    }

    public void setCatLbl(String txt) {
        this.catLbl.setText(txt);
    }

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        reload();
        
        suggLV.setCellFactory(param -> new ProductsFrontController.SuggCell());
        reviewsLV.setCellFactory(param -> new ProductDetailController.ReviewCell());
    }    

    @FXML
    private void showProducts(ActionEvent event) {
        navigateTo(getClass().getResource("/edu/cupcake/gui/ProductsFront.fxml"));
    }

    @FXML
    private void showCats(ActionEvent event) {
        navigateTo(getClass().getResource("/edu/cupcake/gui/CategoriesFront.fxml"));
    }
    
    public void navigateTo(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            priceLbl.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    private void reload(){
        ProductService service = new ProductService();
        suggLV.setItems(FXCollections.observableArrayList(service.getObsMostSoldProducts()));
    }

    @FXML
    private void loadComments(ActionEvent event) {
        ReviewService service = new ReviewService();
        reviewsLV.setItems(FXCollections.observableArrayList(service.getProductReviews(id)));
    }

    @FXML
    private void addReview(ActionEvent event) {
        ReviewService service = new ReviewService();
        Review r = new Review(commentTF.getText(), java.sql.Date.valueOf(LocalDate.now()), (int) ratingB.getRating(), this.getId(), 1);
        service.addReview(r);
        reviewsLV.setItems(FXCollections.observableArrayList(service.getProductReviews(id)));
    }
    
    
    
    
    
    static class ReviewCell extends JFXListCell<Review>{
        
        HBox container = new HBox();
        Label user = new Label();
        Label comment = new Label();
        Rating rating = new Rating();
        
        Pane pane = new Pane();
        
        JFXButton removeButton = new JFXButton("Supprimer");
        
        Review lastItem;
        
        
        public ReviewCell(){
            super();
            
            
            container.getChildren().addAll(user, comment, rating, removeButton);
            
            
            HBox.setHgrow(pane, Priority.ALWAYS);
            
            removeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ReviewService service = new ReviewService();
                    service.deleteReview(lastItem.getId());
                    
                }
            });
        }

        @Override
        protected void updateItem(Review item, boolean empty) {
            super.updateItem(item, empty);
            
            setText(null);
            setGraphic(null);
            if(item != null && !empty){
                user.setText(String.valueOf(item.getUser_id()));
                comment.setText(item.getReviewText());
                rating.setRating(item.getRating());
                rating.setMax(5);
                rating.setDisable(true);
                if(true){
                    removeButton.setVisible(true);
                }
                setGraphic(container);
                lastItem = item;
            }
        }
        
        
    }
    
}
