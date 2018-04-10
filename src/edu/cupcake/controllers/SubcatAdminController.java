/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.cupcake.entities.Category;
import edu.cupcake.entities.Subcategory;
import edu.cupcake.services.CategoryService;
import edu.cupcake.services.SubcategoryService;
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
public class SubcatAdminController implements Initializable {

    @FXML
    private Button subCategoryManagementBtn;
    @FXML
    private Button categoryManagementBtn;
    @FXML
    private SplitPane subcategoryManagementPane;
    @FXML
    private JFXTextField scNameTF;
    @FXML
    private ChoiceBox<Category> categoriesCB;
    @FXML
    private JFXButton addSCategoryBtn;
    @FXML
    private JFXButton editSCategoryBtn;
    @FXML
    private JFXButton deleteSCategoryBtn;
    @FXML
    private JFXButton cSClearBtn;
    @FXML
    private ImageView csIV;
    @FXML
    private JFXButton csImageBtn;
    @FXML
    private Label csimgPathLbl;
    @FXML
    private TableView<Subcategory> scategoriesTV;
    @FXML
    private TableColumn<?, ?> cSNameCol;
    @FXML
    private TableColumn<?, ?> cSupdatedAtCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        CategoryService service = new CategoryService();
        ObservableList<Category> cats = FXCollections.observableArrayList(service.getCategories());
        categoriesCB.setItems(cats);
        categoriesCB.setValue(cats.stream().findFirst().get());
        
        refreshSubCategories();
        
        scategoriesTV.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            try {
                Subcategory selected = scategoriesTV.getSelectionModel().getSelectedItem();
                scNameTF.setText(selected.getName());
                csIV.setImage(new Image("http://" + selected.getImage_name()));
                csimgPathLbl.setText(selected.getImage_name());
                categoriesCB.setValue(categoriesCB.getItems().stream().
                        filter((Category s) -> s.getId() == selected.getCategory_id()).
                        findAny().get());
                deleteSCategoryBtn.disableProperty().set(false);
                editSCategoryBtn.disableProperty().set(false);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }

        });
    }

    private void refreshSubCategories() {
        SubcategoryService service = new SubcategoryService();
        cSNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        cSupdatedAtCol.setCellValueFactory(new PropertyValueFactory<>("updated_at"));
        ObservableList<Subcategory> list = FXCollections.observableArrayList(service.getSubcategories());
        scategoriesTV.setItems(list);
        csClear();
    }

    private void csClear() {
        scNameTF.clear();
        csIV.setImage(null);
        csimgPathLbl.setText("");
        scategoriesTV.getSelectionModel().clearSelection();
        deleteSCategoryBtn.disableProperty().set(true);
        editSCategoryBtn.disableProperty().set(true);
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
    private void addSCategory(ActionEvent event) {
        try {
            if(!scNameTF.getText().equals("") && !csimgPathLbl.getText().isEmpty()){
                String url = null;
            String url2 = null;
            String url3 = "";
            if (!(csimgPathLbl.getText().isEmpty())) {
                url = csimgPathLbl.getText();
                url2 = "http://localhost/cupcake/uploads/";
                url3 = url2.concat(url.substring(url.lastIndexOf('/') + 1, url.length()));

            }
            String filePath = "";
            if (!(csimgPathLbl.getText().isEmpty())) {
                Upload u = new Upload();
                File f = new File(url.replace("file:", ""));

                filePath = u.UploadFile(f);
                Image ii = new Image(url3);
            }
            Category category = (Category) categoriesCB.getSelectionModel().getSelectedItem();
            Subcategory subcategory = new Subcategory(scNameTF.getText(), filePath, java.sql.Date.valueOf(LocalDate.now()), category.getId());
            SubcategoryService service = new SubcategoryService();
            service.addSubCategory(subcategory);
            refreshSubCategories();

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
    private void editSCategory(ActionEvent event) {
        try {
            SubcategoryService service = new SubcategoryService();
            Subcategory currentSubCategory = (Subcategory) scategoriesTV.getSelectionModel().getSelectedItem();
            if (!scNameTF.getText().equals(currentSubCategory.getName())
                    && !scNameTF.getText().equals("")) {

                currentSubCategory.setName(scNameTF.getText());
                currentSubCategory.setUpdated_at(java.sql.Date.valueOf(LocalDate.now()));
                Category category = (Category) categoriesCB.getSelectionModel().getSelectedItem();
                currentSubCategory.setCategory_id(category.getId());
            }

            if (!currentSubCategory.getImage_name().equals(csimgPathLbl.getText())
                    && csimgPathLbl.getText() != null) {

                String url = null;
                String url2 = null;
                String url3 = "";

                url = csimgPathLbl.getText();
                url2 = "http://localhost/cupcake/uploads/";
                url3 = url2.concat(url.substring(url.lastIndexOf('/') + 1, url.length()));
                Upload u = new Upload();
                File f = new File(url.replace("file:", ""));
                String filePath = u.UploadFile(f);
                Image ii = new Image(url3);
                currentSubCategory.setImage_name(filePath);
                System.out.println(filePath);
            }

            service.editSubCategory(currentSubCategory);
            refreshSubCategories();
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
    private void deleteSCategory(ActionEvent event) {
        try {
            SubcategoryService service = new SubcategoryService();
            Subcategory cat = scategoriesTV.getSelectionModel().getSelectedItem();
            service.deleteSubCategory(cat.getId());
            scategoriesTV.getSelectionModel().clearSelection();
            refreshSubCategories();
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
    private void cSClearAll(ActionEvent event) {
        csClear();
    }

    @FXML
    private void csGetImage(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jpg File", "*.jpg", "*.png"));
        try {
            File f = fc.showOpenDialog(null);

            if (f != null) {
                String backslash = System.getProperty("file.separator");
                String st = f.getAbsolutePath().replace(backslash, "/");
                String s = "file:" + st;
                Image i = new Image(s);
                csIV.setImage(i);

                csimgPathLbl.setText(s);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
