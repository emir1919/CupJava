/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.cupcake.entities.Category;
import edu.cupcake.services.CategoryService;
import edu.cupcake.services.Upload;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
public class CategoriesAdminController implements Initializable {

    @FXML
    private Button subCategoryManagementBtn;
    @FXML
    private Button categoryManagementBtn;
    @FXML
    private SplitPane categoryManagementPane;
    @FXML
    private JFXTextField cNameTF;
    @FXML
    private ImageView cIV;
    @FXML
    private JFXButton cImageBtn;
    @FXML
    private Label cimgPathLbl;
    @FXML
    private JFXButton addCategoryBtn;
    @FXML
    private JFXButton editCategoryBtn;
    @FXML
    private JFXButton deleteCategoryBtn;
    @FXML
    private JFXButton cClearBtn;
    @FXML
    private TableView<Category> categoriesTV;
    @FXML
    private TableColumn<?, ?> cNameCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshCategories();
        categoriesTV.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            try {
                Category selected = categoriesTV.getSelectionModel().getSelectedItem();
                cNameTF.setText(selected.getName());
                cIV.setImage(new Image("http://" + selected.getImage_name()));
                cimgPathLbl.setText(selected.getImage_name());
                deleteCategoryBtn.disableProperty().set(false);
                editCategoryBtn.disableProperty().set(false);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }

        });
    }

    private void refreshCategories() {
        CategoryService service = new CategoryService();
        cNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ObservableList<Category> list = FXCollections.observableArrayList(service.getCategories());
        categoriesTV.setItems(list);
        cClear();
    }

    private void cClear() {
        cNameTF.clear();
        cIV.setImage(null);
        cimgPathLbl.setText("");
        categoriesTV.getSelectionModel().clearSelection();
        deleteCategoryBtn.disableProperty().set(true);
        editCategoryBtn.disableProperty().set(true);
    }

    @FXML
    private void showSubCategoryManagement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/SubcatAdmin.fxml"));
            Parent root = loader.load();
            categoryManagementBtn.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showCategoryManagement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/CategoriesAdmin.fxml"));
            Parent root = loader.load();
            categoryManagementBtn.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cGetImage(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jpg File", "*.jpg", "*.png"));
        try {
            File f = fc.showOpenDialog(null);

            if (f != null) {
                String backslash = System.getProperty("file.separator");
                String st = f.getAbsolutePath().replace(backslash, "/");
                String s = "file:" + st;
                Image i = new Image(s);
                cIV.setImage(i);

                cimgPathLbl.setText(s);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void addCategory(ActionEvent event) {
        try {
            if (!cNameTF.getText().equals("") && !cimgPathLbl.getText().isEmpty()) {
                String url = null;
                String url2 = null;
                String url3 = "";
                if (!(cimgPathLbl.getText().isEmpty())) {
                    url = cimgPathLbl.getText();
                    url2 = "http://localhost/cupcake/uploads/";
                    url3 = url2.concat(url.substring(url.lastIndexOf('/') + 1, url.length()));

                }
                String filePath = "";
                if (!(cimgPathLbl.getText().isEmpty())) {
                    Upload u = new Upload();
                    File f = new File(url.replace("file:", ""));

                    filePath = u.UploadFile(f);
                    Image ii = new Image(url3);
                }
                Category category = new Category(cNameTF.getText(), filePath, java.sql.Date.valueOf(LocalDate.now()));
                CategoryService service = new CategoryService();
                service.addCategory(category);
                refreshCategories();

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
                String message = "Impossible d'effectuer cette opération";
                NotificationType notification = NotificationType.ERROR;

                TrayNotification tray = new TrayNotification();
                tray.setTitle(title);
                tray.setMessage(message);
                tray.setNotificationType(notification);
                tray.showAndWait();
            }

        } catch (Exception ex) {
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
    private void editCategory(ActionEvent event) {
        try {
            CategoryService service = new CategoryService();
            Category currentCategory = (Category) categoriesTV.getSelectionModel().getSelectedItem();
            if (!cNameTF.getText().equals(currentCategory.getName())
                    && !cNameTF.getText().equals("")) {

                currentCategory.setName(cNameTF.getText());
                currentCategory.setUpdated_at(java.sql.Date.valueOf(LocalDate.now()));

            }

            if (!currentCategory.getImage_name().equals(cimgPathLbl.getText())
                    && cimgPathLbl.getText() != null) {

                String url = null;
                String url2 = null;
                String url3 = "";

                url = cimgPathLbl.getText();
                url2 = "http://localhost/cupcake/uploads/";
                url3 = url2.concat(url.substring(url.lastIndexOf('/') + 1, url.length()));
                Upload u = new Upload();
                File f = new File(url.replace("file:", ""));
                String filePath = u.UploadFile(f);
                Image ii = new Image(url3);
                currentCategory.setImage_name(filePath);
                System.out.println(filePath);
            }

            service.editCategory(currentCategory);
            refreshCategories();

            String title = "Succès";
            String message = "Opération effectué avec succès";
            NotificationType notification = NotificationType.SUCCESS;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndWait();
        } catch (Exception ex) {
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
    private void deleteCategory(ActionEvent event) {
        try {
            CategoryService service = new CategoryService();
            Category cat = categoriesTV.getSelectionModel().getSelectedItem();
            service.deleteCategory(cat.getId());
            categoriesTV.getSelectionModel().clearSelection();
            refreshCategories();

            String title = "Succès";
            String message = "Opération effectué avec succès";
            NotificationType notification = NotificationType.SUCCESS;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndWait();
        } catch (Exception ex) {
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
    private void cClearAll(ActionEvent event) {
        cClear();
    }

}
