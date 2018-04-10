/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXComboBox;

import edu.CupTest2.entities.Users;
import edu.CupTest2.services.UsersService;
import edu.CupTest2.utils.Routing;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class RegistrationController implements Initializable {

    ObservableList<String> RoleList = FXCollections.observableArrayList("ROLE_USER", "ROLE_ADMIN");

    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTel;
    @FXML
    private DatePicker txtDate;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXTextField txtPseudo;
    @FXML
    private Button btnInscrire;
    @FXML
    private RadioButton radioHomme;
    @FXML
    private RadioButton radioFemme;
    @FXML
    private TextField txtAdresse;
    @FXML

    private TextField txtCfPassword;
    @FXML
    private VBox VBoxInfoPersonel;
    @FXML
    private ToggleGroup gender;
    @FXML
    private VBox VBoxMdp;

    @FXML
    private JFXComboBox Rolecombo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Rolecombo.setItems(RoleList);
        main.lastroute = main.currentroute;
        main.currentroute = Routing.REGISTER;
    }

    @FXML
    public void Back(ActionEvent event) throws IOException {
        

        Parent root = FXMLLoader.load(getClass().getResource(main.lastroute));
     
        VBoxInfoPersonel.getScene().setRoot(root);

    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }

        return true;
    }

    private boolean validateInputs() {
        if ((txtNom.getText().isEmpty()) || (txtPrenom.getText().isEmpty())
                || (txtEmail.getText().isEmpty()) || (txtTel.getText().isEmpty())
                || (txtDate.getValue() == null) || (txtPseudo.getText().isEmpty()) || (txtPassword.getText().isEmpty())
                || ((!radioFemme.isSelected()) && !(radioHomme.isSelected()))) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez remplir tout les champs");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        } else if (!(txtCfPassword.getText().equals(txtPassword.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veillez vérifier votre mot de passe");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        } else if (!(validate(txtEmail.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veillez vérifier votre email");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        } else if ((txtTel.getText().trim().length() > 8) || ((txtTel.getText().trim().length() < 8)) || (!isInteger(txtTel.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veillez vérifier votre numéro de téléphone");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        }
        return true;
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @FXML
    private void ajouterClient(ActionEvent event) throws SQLException, IOException, InterruptedException {
        UsersService sr = new UsersService();
        //String image = "";
        String valueRadio = null;

        if (radioHomme.isSelected()) {
            valueRadio = "Homme";
        } else if (radioFemme.isSelected()) {
            valueRadio = "Femme";
        }

        if (validateInputs()) {
            int numTel = Integer.parseInt(txtTel.getText());

            Date birthday = Date.valueOf(txtDate.getValue());
            Users client = new Users(txtPseudo.getText(), txtEmail.getText(), txtPassword.getText(), birthday, Rolecombo.getValue().toString(), txtNom.getText(), txtPrenom.getText(), Long.parseLong(txtTel.getText()));
            UsersService us = new UsersService();

            sr.ajouterClient(client);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            alert.setContentText("Operation effectuée avec succée !");
            alert.show();
            alert.setOnHidden(e -> {
                if (alert.getResult() == ButtonType.YES) {
                    System.out.println("good");
                } else {
                    System.out.println("canceled");
                }
            });

        }
    }

}
