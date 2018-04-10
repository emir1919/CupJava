/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import edu.cupcake.entities.Favorite;
import edu.cupcake.entities.ObservableProduct;
import edu.cupcake.entities.Subcategory;
import edu.cupcake.services.FavoriteService;
import edu.cupcake.services.SubcategoryService;
import edu.cupcake.utils.Routing;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author yassi
 */
public class CategoriesFrontController implements Initializable {

    private int currentUser = 1;
    @FXML
    private BorderPane mainPane;
    @FXML
    private JFXButton showProductsBtn;
    @FXML
    private JFXButton showCatsBtn;
    @FXML
    private JFXListView<Subcategory> subcatsLV;

    private ObservableList<Subcategory> subcats = FXCollections.observableArrayList();

    static class SubcategoryCell extends JFXListCell<Subcategory> {

        HBox hbox = new HBox();
        VBox vbox = new VBox();
        Pane pane = new Pane();

        JFXButton addButton = new JFXButton("Ajouter");
        JFXButton removeButton = new JFXButton("Supprimer");

        Label nameLbl = new Label();
        ImageView pIV = new ImageView();
        ObservableList<Favorite> myFavorites = FXCollections.observableArrayList();
        FavoriteService favService = new FavoriteService();
        int currentUser;

        Subcategory lastItem;

        public SubcategoryCell() {
            super();

            hbox.getChildren().addAll(addButton, removeButton);
            vbox.getChildren().addAll(pIV, nameLbl, hbox);

            VBox.setVgrow(pane, Priority.ALWAYS);

            if (cupcake.Cupcake.user != null) {
                currentUser = cupcake.Cupcake.user.getId();
                myFavorites = FXCollections.observableArrayList(favService.getCurrentFavorites(currentUser));
                addButton.setVisible(true);
                removeButton.setVisible(true);
            } else {
                addButton.setVisible(false);
                removeButton.setVisible(false);
            }

            addButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    favService.addFavorite(new Favorite(currentUser, lastItem.getId()));
                    try {

                        HomeController.affichercategories = 1;
                        Parent root = FXMLLoader.load(getClass().getResource(Routing.HOME));
                        addButton.getScene().setRoot(root);
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }
            });

            removeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    favService.deleteFavorite(currentUser, lastItem.getId());
                    try {
                        HomeController.affichercategories = 1;
                        Parent root = FXMLLoader.load(getClass().getResource(Routing.HOME));
                        addButton.getScene().setRoot(root);
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }
            });
        }

        @Override
        protected void updateItem(Subcategory item, boolean empty) {
            super.updateItem(item, empty);

            setText(null);
            setGraphic(null);
            if (item != null && !empty) {
                nameLbl.setText("Nom : " + item.getName());
                pIV.setImage(new Image("http://" + item.getImage_name(), 250, 250, false, false));
                if (cupcake.Cupcake.user != null) {
                    addButton.setVisible(true);
                    removeButton.setVisible(true);
                    if (myFavorites.stream().anyMatch((Favorite f) -> f.getSubcategory_id() == item.getId())) {
                        addButton.setVisible(false);
                        removeButton.setVisible(true);
                    } else {
                        addButton.setVisible(true);
                        removeButton.setVisible(false);
                    }
                } else {
                    addButton.setVisible(false);
                    removeButton.setVisible(false);
                }
                
                lastItem = item;
                setGraphic(vbox);
            }
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        subcatsLV.setCellFactory(param -> new SubcategoryCell());

        reload();
    }

    private void reload() {

        SubcategoryService service = new SubcategoryService();
        subcats = FXCollections.observableArrayList(service.getSubcategories());

        subcatsLV.setItems(subcats);
    }

    @FXML
    private void showProducts(ActionEvent event) {
        navigateTo(getClass().getResource("/gui/ProductsFront.fxml"));
    }

    @FXML
    private void showCats(ActionEvent event) {
        navigateTo(getClass().getResource("/gui/CategoriesFront.fxml"));
    }

    public void navigateTo(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            subcatsLV.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onProductDetails(MouseEvent event) {
    }

}
