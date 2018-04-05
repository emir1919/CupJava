/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.gui;

import com.jfoenix.controls.JFXButton;
import edu.CupTest2.entities.Comment;
import edu.CupTest2.entities.Enseigne;
import edu.CupTest2.services.CommentServices;
import edu.CupTest2.services.EnseigneServices;
import edu.CupTest2.services.RatingServices;
import static java.awt.Color.white;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class ShowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    List<VBox> oas = new ArrayList<VBox>();

    ObservableList<VBox> oa;
    List<Enseigne> enseignes = new ArrayList<Enseigne>();
    EnseigneServices es = new EnseigneServices();
    VBox vb;
    @FXML
    private AnchorPane AP;
    ScrollPane scrollPane;
    edu.CupTest2.entities.Rating r = new edu.CupTest2.entities.Rating();
    RatingServices rs = new RatingServices();
    int pos = 0;
    final int minPos = 0;
    final int maxPos = 100;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            // TODO
            enseignes = es.selectAllEnseigne();
        } catch (SQLException ex) {
            Logger.getLogger(ShowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);
        hb.setPrefWidth(1500);
        hb.setPrefHeight(350);
        for (int i = 0; i < enseignes.size(); i++) {
            try {
                VBox vbox1 = new VBox();
                
                vbox1.setAlignment(Pos.TOP_LEFT);
                vbox1.setPrefHeight(400);
                vbox1.setPrefWidth(300);
                
                Pane p = new Pane();
                p.setPrefHeight(50);
                p.setPrefWidth(100);
                VBox vbox2 = new VBox();
                vbox1.setStyle(
                        "-fx-border-style: solid;"
                                + "-fx-border-width: 3;"
                                + "-fx-border-color:  white;"
                                + "-fx-background-color: black;"
                                + "-fx-background-radius: 45px");
                vbox2.setAlignment(Pos.TOP_LEFT);
                
                vbox2.prefHeight(250);
                vbox2.prefWidth(150);
                ImageView img = new ImageView();
                img.setFitHeight(57);
                img.setFitWidth(50);
                img.setTranslateX(60);
                FileInputStream file = null;
                try {
                    file = new FileInputStream("C:\\Users\\Emir\\Documents\\NetBeansProjects\\CupTest2\\src\\edu\\CupTest2\\images\\" + enseignes.get(i).getLogo());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ShowBrandController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //img.setImage(new Image(file));
                Circle cir2 = new Circle(100,100,40);
                cir2.setStroke(Color.SEAGREEN);
                cir2.setFill(new ImagePattern(new Image(file)));
                cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
                cir2.setTranslateX(40);
                cir2.setTranslateY(20);
                // vbox2.getChildren().add(img);
                p.getChildren().add(vbox2);
                Pane p3=new Pane();
                Label l1=new Label("Nom:");
                Text t = new Text();
                t.setText(enseignes.get(i).getName());
                t.setFont(Font.font("Verdana", 15));
                t.setFill(Color.rgb(243, 156, 18));
                //t.setStyle(" -fx-padding: 40 0 0 80;");
                t.setTranslateX(33);
                t.setTranslateY(13);
                p3.setTranslateX(40);
                p3.setTranslateY(40);
                p3.getChildren().addAll(l1,t);
                Pane p4=new Pane();
                
                Label l2=new Label("Description:");
                Text t2 = new Text();
                t2.setText(enseignes.get(i).getDescription());
                t2.setFont(Font.font("Verdana", 10));
                t2.setFill(Color.rgb(243, 156, 18));
                //t.setStyle(" -fx-padding: 40 0 0 80;");
                t2.setTranslateX(73);
                t2.setTranslateY(13);
                p4.setTranslateX(40);
                p4.setTranslateY(40);
                p4.getChildren().addAll(l2,t2);
                Pane p2 = new Pane();
                JFXButton b = new JFXButton();
                                int k = enseignes.get(i).getId();

                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                       AnchorPane pane;
                        try {
                        BrandFrontController.id=k;

                            pane = FXMLLoader.load(getClass().getResource("/edu/CupTest2/gui/BrandFront.fxml"));
                   AP.getChildren().setAll(pane);

                            //AnchorPane pane1=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/EditProfile.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(ShowController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });
                
                b.setText("More Details");
                ImageView img2 = new ImageView();
                img2.setFitHeight(20);
                img2.setFitWidth(20);
                //img.setTranslateX(30);
                FileInputStream file2 = null;
                try {
                    file2 = new FileInputStream("C:\\Users\\Emir\\Documents\\NetBeansProjects\\CupTest2\\src\\edu\\CupTest2\\images\\info-g.png");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ShowBrandController.class.getName()).log(Level.SEVERE, null, ex);
                }
                img2.setImage(new Image(file2));
                b.setTranslateX(13);
                p2.getChildren().addAll(b, img2);
                p2.setTranslateX(30);
                p2.setTranslateY(90);
                Rating rate = new Rating();
                rate.setPartialRating(true);
                                rate.setRating(rs.CalculerMoyRatring(enseignes.get(i).getId()));

                rate.ratingProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable,
                            Number oldValue, Number newValue) {
                        //System.out.println(newValue.toString());
                        float n = newValue.floatValue();
                        r.setNote(n);
                        r.setEnseigne_id(k);
                        r.setUser_id((int) (long)main.user.getId());
                        try {
                            rs.ajouterRating(r);
                        } catch (SQLException ex) {
                            Logger.getLogger(ShowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                    
                });
                //System.out.println(rate.getRating());
                rate.setTranslateY(110);
                Label lf=new Label(rs.CalculerMoyRatring(enseignes.get(i).getId())+"/5 ("+rs.NbRating(enseignes.get(i).getId())+" votes)");
                lf.setTranslateX(40);
                lf.setTranslateY(120);
                vbox1.getChildren().addAll(cir2, p3, p4, p2, rate,lf);
                oas.add(vbox1);
            } catch (SQLException ex) {
                Logger.getLogger(ShowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        oa = FXCollections.observableArrayList(oas);
        System.out.println(oas.size());

        System.out.println(enseignes.size());
        hb.getChildren().addAll(oa);
        //hb.getChildren().add(sb);

        AP.getChildren().setAll(hb);
        /*scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVmax(1320);
        scrollPane.setPrefSize(1320, 900);
        scrollPane.setLayoutX(1320);
        scrollPane.setLayoutY(900);
        scrollPane.setContent(AP);*/
        /*AP.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {

                if (event.getDeltaY() > 0)
                    scrollPane.setHvalue(pos == minPos ? minPos : pos--);
                else
                    scrollPane.setHvalue(pos == maxPos ? maxPos : pos++);

            }
        });

        scrollPane = new ScrollPane();
        scrollPane.setHmin(minPos);
        scrollPane.setHmax(maxPos);
        scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        scrollPane.setPannable(true);
        scrollPane.setFitToHeight(true);*/
        

    }

}
