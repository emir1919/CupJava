/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.gui;

import com.jfoenix.controls.JFXButton;
import edu.CupTest2.entities.Comment;
import edu.CupTest2.entities.Enseigne;
import edu.CupTest2.entities.favory_brand;
import edu.CupTest2.services.CommentServices;
import edu.CupTest2.services.EnseigneServices;
import edu.CupTest2.services.FavoryBrandServices;
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
import javafx.geometry.Orientation;
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
import javax.swing.JScrollPane;
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
    List<VBox> oas1 = new ArrayList<VBox>();

    ObservableList<VBox> oa;
    ObservableList<VBox> oa1;
    FavoryBrandServices fs = new FavoryBrandServices();

    List<Enseigne> enseignes = new ArrayList<Enseigne>();
    EnseigneServices es = new EnseigneServices();
    List<favory_brand> l = new ArrayList<favory_brand>();
    List<Enseigne> l1 = new ArrayList<Enseigne>();
    FavoryBrandServices fbs = new FavoryBrandServices();
    favory_brand fbb1 = new favory_brand();
    VBox vb;
    @FXML
    private AnchorPane AP;
    ScrollPane scrollPane;
    edu.CupTest2.entities.Rating r = new edu.CupTest2.entities.Rating();
    RatingServices rs = new RatingServices();
    int pos = 0;
    final int minPos = 0;
    final int maxPos = 100;
    @FXML
    private ScrollPane sp;
    Boolean test = false;

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
        HBox hb1 = new HBox();
        hb1.setAlignment(Pos.CENTER);
        hb1.setPrefWidth(1500);
        hb1.setPrefHeight(200);
        if (main.user == null) {
            affiche1();

        } else {
            try {
                l = fs.GetFavoriById((int) (long) main.user.getId());
            } catch (SQLException ex) {
                Logger.getLogger(ShowController.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int i = 0; i < l.size(); i++) {
                System.out.println(l.get(i).getEnseigne_id());
                l1.add(es.getEnseignebyId(l.get(i).getEnseigne_id()));

            }
            affiche1();
            affiche2();
        }

        oa = FXCollections.observableArrayList(oas);
        hb.getChildren().addAll(oa);

        oa1 = FXCollections.observableArrayList(oas1);

        hb1.getChildren().addAll(oa1);
        //hb1.setTranslateX(-470);
        hb1.setTranslateY(360);
        sp.setPannable(true);
        //sp.setContent(hb);
        Pane pi = new Pane();
        Text toti = new Text();
        toti.setText("Favoris:");
        pi.getChildren().addAll(hb, hb1);
        sp.setContent(pi);
        toti.setTranslateY(365);
        toti.setFont(Font.font("Verdana", 25));
        toti.setFill(Color.PINK);
        AP.getChildren().setAll(sp, toti);

    }

    public void affiche2() {
        for (int i = 0; i < l1.size(); i++) {
            VBox vbox21 = new VBox();
            vbox21.setAlignment(Pos.TOP_LEFT);
            vbox21.setPrefHeight(135);
            vbox21.setPrefWidth(200);
            Pane p21 = new Pane();
            p21.setPrefHeight(50);
            p21.setPrefWidth(100);
            VBox vbox22 = new VBox();
            vbox21.setStyle(
                    "-fx-border-style: solid;"
                    + "-fx-border-width: 3;"
                    + "-fx-border-color:  white;"
                    + "-fx-background-color: black;"
                    + "-fx-background-radius: 45px");
            vbox22.setAlignment(Pos.TOP_LEFT);
            vbox22.prefHeight(250);
            vbox22.prefWidth(150);
            ImageView img20 = new ImageView();
            img20.setFitHeight(47);
            img20.setFitWidth(50);
            img20.setTranslateX(60);
            FileInputStream file20 = null;
            try {
                file20 = new FileInputStream("C:\\Users\\Emir\\Documents\\NetBeansProjects\\CupTest2\\src\\edu\\CupTest2\\images\\" + l1.get(i).getLogo());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ShowBrandController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //img.setImage(new Image(file));
            Circle cir22 = new Circle(100, 100, 40);
            cir22.setStroke(Color.SEAGREEN);
            cir22.setFill(new ImagePattern(new Image(file20)));
            cir22.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
            cir22.setTranslateX(50);
            cir22.setTranslateY(5);
            // vbox2.getChildren().add(img);
            p21.getChildren().add(vbox22);
            Pane p32 = new Pane();
            Label l32 = new Label("Nom:");
            Text t32 = new Text("Nom:");
            t32.setFill(Color.GREY);
            Text t33 = new Text();
            t33.setText(l1.get(i).getName());
            t33.setFont(Font.font("Verdana", 15));
            t33.setFill(Color.rgb(243, 156, 18));
            //t.setStyle(" -fx-padding: 40 0 0 80;");
            t32.setTranslateY(13);
            t33.setTranslateX(33);
            t33.setTranslateY(13);
            p32.setTranslateX(40);
            p32.setTranslateY(5);
            p32.getChildren().addAll(t32, t33);

            Pane p50 = new Pane();
            JFXButton b51 = new JFXButton();
            int k1 = l1.get(i).getId();
            b51.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    AnchorPane pane52;
                    try {
                        BrandFrontController.id = k1;

                        pane52 = FXMLLoader.load(getClass().getResource("/edu/CupTest2/gui/BrandFront.fxml"));
                        AP.getChildren().setAll(pane52);

                        //AnchorPane pane1=FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/EditProfile.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(ShowController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            b51.setText("More Details");
            ImageView img52 = new ImageView();
            img52.setFitHeight(20);
            img52.setFitWidth(20);
            //img.setTranslateX(30);
            FileInputStream file52 = null;
            try {
                file52 = new FileInputStream("C:\\Users\\Emir\\Documents\\NetBeansProjects\\CupTest2\\src\\edu\\CupTest2\\images\\info-g.png");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ShowBrandController.class.getName()).log(Level.SEVERE, null, ex);
            }
            img52.setImage(new Image(file52));
            b51.setTranslateX(13);
            p50.getChildren().addAll(b51, img52);
            p50.setTranslateX(30);
            p50.setTranslateY(10);
            vbox21.getChildren().addAll(cir22, p21, p32, p50);
            vbox21.setTranslateX(10);
            oas1.add(vbox21);
        }
    }

    public void affiche1() {
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
                Circle cir2 = new Circle(100, 100, 40);
                cir2.setStroke(Color.SEAGREEN);
                cir2.setFill(new ImagePattern(new Image(file)));
                cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
                cir2.setTranslateX(40);
                cir2.setTranslateY(20);
                // vbox2.getChildren().add(img);
                p.getChildren().add(vbox2);
                Pane p3 = new Pane();
                Label l1 = new Label("Nom:");
                Text t211 = new Text("Nom:");
                t211.setFill(Color.GREY);
                Text t = new Text();
                t.setText(enseignes.get(i).getName());
                t.setFont(Font.font("Verdana", 15));
                t.setFill(Color.rgb(243, 156, 18));
                //t.setStyle(" -fx-padding: 40 0 0 80;");
                t211.setTranslateY(13);
                t.setTranslateX(33);
                t.setTranslateY(13);
                p3.setTranslateX(40);
                p3.setTranslateY(40);
                p3.getChildren().addAll(t, t211);
                Pane p4 = new Pane();

                Label l2 = new Label("Description:");
                Text t21 = new Text("Description:");
                t21.setFill(Color.GREY);
                Text t2 = new Text();
                t2.setText(enseignes.get(i).getDescription());
                t2.setFont(Font.font("Verdana", 10));
                t2.setFill(Color.rgb(243, 156, 18));
                //t.setStyle(" -fx-padding: 40 0 0 80;");
                //t21.setTranslateX(73);
                t21.setTranslateY(13);
                t2.setTranslateX(73);
                t2.setTranslateY(13);
                p4.setTranslateX(40);
                p4.setTranslateY(40);
                p4.getChildren().addAll(t21, t2);
                Pane pf = new Pane();
                JFXButton bf = new JFXButton();
                int k1 = enseignes.get(i).getId();

                bf.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        favory_brand fbb = new favory_brand();
                        try {
                            fbb1 = fbs.getfavoryById(k1, (int) (long) main.user.getId());
                        } catch (SQLException ex) {
                            Logger.getLogger(ShowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (fbb1 == null) {
                            test = true;
                        }
                        if (test == true) {
                            fbb.setEnseigne_id(k1);
                            fbb.setUser_id((int) (long) main.user.getId());
                            try {
                                fbs.AjouterFavori(fbb);
                            } catch (SQLException ex) {
                                Logger.getLogger(ShowController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            System.out.println("impo");
                        }
                    }
                });

                bf.setText("Favoris");
                ImageView img22 = new ImageView();
                img22.setFitHeight(20);
                img22.setFitWidth(20);
                //img.setTranslateX(30);
                FileInputStream file22 = null;
                try {
                    file22 = new FileInputStream("C:\\Users\\Emir\\Documents\\NetBeansProjects\\CupTest2\\src\\edu\\CupTest2\\images\\fav.png");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ShowBrandController.class.getName()).log(Level.SEVERE, null, ex);
                }
                img22.setImage(new Image(file22));
                bf.setTranslateX(13);
                pf.getChildren().addAll(bf, img22);
                pf.setTranslateX(30);
                pf.setTranslateY(50);
                Pane p2 = new Pane();
                JFXButton b = new JFXButton();
                int k = enseignes.get(i).getId();

                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        AnchorPane pane;
                        try {
                            BrandFrontController.id = k;

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
                p2.setTranslateY(70);
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
                        r.setUser_id((int) (long) main.user.getId());
                        try {
                            rs.ajouterRating(r);
                        } catch (SQLException ex) {
                            Logger.getLogger(ShowController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                });
                //System.out.println(rate.getRating());
                rate.setTranslateY(90);
                Text lf = new Text(rs.CalculerMoyRatring(enseignes.get(i).getId()) + "/5 (" + rs.NbRating(enseignes.get(i).getId()) + " votes)");
                lf.setFill(Color.GREY);
                lf.setTranslateX(40);
                lf.setTranslateY(100);
                vbox1.getChildren().addAll(cir2, p3, p4, pf, p2, rate, lf);
                oas.add(vbox1);
            } catch (SQLException ex) {
                Logger.getLogger(ShowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
