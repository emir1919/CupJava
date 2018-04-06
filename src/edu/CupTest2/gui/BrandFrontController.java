/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import edu.CupTest2.entities.Comment;
import edu.CupTest2.entities.Enseigne;
import edu.CupTest2.services.EnseigneServices;
import edu.CupTest2.utils.MyConnexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import edu.CupTest2.entities.Rating;
import edu.CupTest2.services.CommentServices;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class BrandFrontController implements Initializable {

    private ObservableList<String> items = FXCollections.observableArrayList();
    private Rating rating;
    @FXML
    private JFXListView<Comment> list_commentaire;
    @FXML
    private TextArea contenu_commentaire;
    @FXML
    private Button commenter;
    Comment comment;
    public static int id=0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        

    }

    public void affiche(Comment s) throws SQLException {

        CommentServices sv = new CommentServices();
        System.out.println(s);
        ObservableList<Comment> items = FXCollections.observableArrayList(sv.GetAllCommentByEnseigne(id));

        list_commentaire.setCellFactory((ListView<Comment> arg0) -> {
            ListCell<Comment> cell = new ListCell<Comment>() {

                @Override
                protected void updateItem(Comment e, boolean btl) {
                    super.updateItem(e, btl);

                    if (e != null) {
                        File file = new File("src\\edu\\CupTest2\\images\\dialog.png");
                        file.getParentFile().mkdirs();
                        Image IMAGE_RUBY = new Image(file.toURI().toString());
                        //   Image IMAGE_RUBY = new Image(ps.findById(e.getPassager().getId()).getAvatar());

                        ImageView imgview = new ImageView(IMAGE_RUBY);

                        setGraphic(imgview);

                        imgview.setFitHeight(50);
                        imgview.setFitWidth(50);
                        Rectangle clip = new Rectangle(
                                imgview.getFitWidth(), imgview.getFitHeight()
                        );

                        clip.setArcWidth(20);
                        clip.setArcHeight(20);
                        imgview.setClip(clip);

                        // snapshot the rounded image.
                        SnapshotParameters parameters = new SnapshotParameters();
                        parameters.setFill(Color.TRANSPARENT);
                        WritableImage image = imgview.snapshot(parameters, null);

                        // remove the rounding clip so that our effect can show through.
                        imgview.setClip(null);

                        // apply a shadow effect.
                        imgview.setEffect(new DropShadow(20, Color.BLACK));

                        // store the rounded image in the imageView.
                        imgview.setImage(image);

                        setText("Commentaire : " + e.getContent());

                        setFont(Font.font("Berlin Sans FB Demi Bold", 12));
                        Button butt = new Button();
                        butt.setText("add");

                        butt.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                try {

                                    CommentServices sv = new CommentServices();
                                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

// Using DateFormat format method we can create a string 
// representation of a date with the defined format.
        String reportDate = df.format(new Date().getTime());
                                    Comment c = new Comment(list_commentaire.getSelectionModel().getSelectedItem().getId(), contenu_commentaire.getText(), reportDate, (int) (long)main.user.getId(), id);

                                    sv.ModifierCommentaire(c);
                                    AnchorPane pane = new AnchorPane();

                                    affiche(comment);
                                    contenu_commentaire.setText("");

                                } catch (SQLException ex) {
                                    Logger.getLogger(BrandFrontController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        setGraphic(butt);
                        // setAlignment(Pos.CENTER);
                    }

                }

            };
            return cell;
        });
        list_commentaire.setItems(items);
    }


    public Comment getComment() {
        return comment;
    }

    public void setSujet(Comment comment) {
        this.comment = comment;
    }

    @FXML
    private void commenter(ActionEvent event) throws SQLException {
        CommentServices sv = new CommentServices();
        // Create an instance of SimpleDateFormat used for formatting 
// the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

// Using DateFormat format method we can create a string 
// representation of a date with the defined format.
        String reportDate = df.format(new Date().getTime());
        Comment c = new Comment(contenu_commentaire.getText(), reportDate, (int) (long)main.user.getId(), id);

        sv.AjouterCommentaire(c);
        AnchorPane pane = new AnchorPane();

        affiche(comment);
        contenu_commentaire.setText("");
    }

    @FXML
    private void test(MouseEvent event) {
        contenu_commentaire.setText(list_commentaire.getSelectionModel().getSelectedItem().getContent());
        //commenter.setVisible(false);
    }

}
