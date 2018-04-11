/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Colors;
import com.orsoncharts.TitleAnchor;
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.data.StandardPieDataset3D;
import com.orsoncharts.fx.Chart3DViewer;
import com.orsoncharts.label.StandardPieLabelGenerator;
import static com.orsoncharts.label.StandardPieLabelGenerator.PERCENT_TEMPLATE;
import com.orsoncharts.plot.PiePlot3D;
import edu.cupcake.entities.Bakery;
import edu.cupcake.entities.Enseigne;
import edu.cupcake.entities.Product;
import edu.cupcake.services.EnseigneServices;
import edu.cupcake.services.PatisserieServices;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Image;
import java.awt.Desktop;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class StatistiquesController implements Initializable {

    @FXML
    private Button InfoBrandButton;
    @FXML
    private Button BakeryButton;
    @FXML
    private Button map;
    @FXML
    private Button stat;
    @FXML
    private Pane pane;

    @FXML
    private BarChart<String, Double> barchart;
    @FXML
    private BarChart<String, Double> barchart2;

    /**
     * Initializes the controller class.
     */
    private static PieDataset3D<String> createDataset() throws SQLException {

        EnseigneServices es = new EnseigneServices();
        PatisserieServices ps = new PatisserieServices();
        List<Enseigne> types = new ArrayList<>();
        StandardPieDataset3D<String> dataset = new StandardPieDataset3D<>();

        types = es.selectAllEnseigne();

        for (Enseigne t : types) {

            //@SuppressWarnings("UnusedAssignment")
            List<Bakery> bakeries = new ArrayList<>();
            bakeries = ps.getBakerybyBrand(t.getId());
            int nb = bakeries.size();
            dataset.add(t.getName(), nb);

        }

        return dataset;
    }
    @FXML
    private JFXButton pdf;
    @FXML
    private JFXButton afficher;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EnseigneServices es = new EnseigneServices();
        PatisserieServices ps = new PatisserieServices();
        ///Stat1
        /*barchart.setTitle("Patisseries Par Enseigne");
        XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
        List<Enseigne> types = new ArrayList<>();
        types = es.selectAllEnseigne();
        for (Enseigne t : types) {
        
        //@SuppressWarnings("UnusedAssignment")
        List<Bakery> bakeries = new ArrayList<>();
        bakeries = ps.getBakerybyBrand(t.getId());
        int nb = bakeries.size();
        XYChart.Data<String, Integer> data = new XYChart.Data<String, Integer>(t.getName(), nb);
        series1.getData().add(data);
        
        }
        barchart.getData().addAll(series1);*/
        /*barchart.setTitle("vente par produit");
        XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
        List<Product> prods1 = new ArrayList<>();
        prods1 = es.TopSalesByBrand(es.getEnseignebyUserId((int)(long)main.user.getId()).getId());
        for (Product t : prods1) {
        
        //@SuppressWarnings("UnusedAssignment")
        XYChart.Data<String, Integer> data1 = new XYChart.Data<String, Integer>(t.getName(), t.getSales());
        series1.getData().add(data1);
        
        }
        barchart.getData().addAll(series1);*/
        barchart.setTitle("Revenu Par produit");
        XYChart.Series<String, Double> series1 = new XYChart.Series<>();
        List<Product> prods1 = new ArrayList<>();
        try {
            prods1 = es.TopSalesByBrand(es.getEnseignebyUserId(cupcake.Cupcake.user.getId()).getId());
        } catch (SQLException ex) {
            Logger.getLogger(StatistiquesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Product t : prods1) {
            
            //@SuppressWarnings("UnusedAssignment")
            XYChart.Data<String, Double> data1 = new XYChart.Data<String, Double>(t.getName(), t.getSales()*t.getPrice());
            series1.getData().add(data1);
            
        }
        barchart.getData().addAll(series1);
        ////stat3
        barchart2.setTitle("Produit par note");
        XYChart.Series<String, Double> series2 = new XYChart.Series<>();
        List<Product> prods = new ArrayList<>();
        try {
            prods = es.TopProductByBrand(es.getEnseignebyUserId(cupcake.Cupcake.user.getId()).getId());
        } catch (SQLException ex) {
            Logger.getLogger(StatistiquesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Product t : prods) {
            
            //@SuppressWarnings("UnusedAssignment")
            XYChart.Data<String, Double> data = new XYChart.Data<String, Double>(t.getName(), t.getRating());
            series2.getData().add(data);
            
        }
        barchart2.getData().addAll(series2);
        ////Stat2
        try {
            // TODO
            Node n = createDemo();
            pane.getChildren().add(n);
        } catch (SQLException ex) {
            Logger.getLogger(StatistiquesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ShowBrand(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoBrand.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) InfoBrandButton.getScene().getWindow();
        stage.close();
        Stage s = new Stage();
        s.setScene(new Scene(root));
        s.show();
    }

    @FXML
    private void ShowBakery(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoBakery.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) BakeryButton.getScene().getWindow();
        stage.close();
        Stage s = new Stage();
        s.setScene(new Scene(root));
        s.show();
    }

    @FXML
    private void localisation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Localisation.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) BakeryButton.getScene().getWindow();
        stage.close();
        Stage s = new Stage();
        s.setScene(new Scene(root));
        s.show();
    }

    @FXML
    private void statistiques(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Statistiques.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) BakeryButton.getScene().getWindow();
        stage.close();
        Stage s = new Stage();
        s.setScene(new Scene(root));
        s.show();
    }

    private static Chart3D createChart(PieDataset3D<String> dataset) throws SQLException {
        final Chart3D chart = Chart3DFactory.createPieChart("Nombre de patisseries par enseigne", "", createDataset());

        chart.setTitleAnchor(TitleAnchor.TOP_LEFT);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setLegendLabelGenerator(new StandardPieLabelGenerator(PERCENT_TEMPLATE));
        plot.setSectionLabelGenerator(new StandardPieLabelGenerator(PERCENT_TEMPLATE));
        plot.setSectionColors(Colors.createFancyLightColors());
        return chart;
    }

    public static Node createDemo() throws SQLException {
        PieDataset3D<String> dataset = createDataset();
        Chart3D chart = createChart(dataset);
        Chart3DViewer viewer = new Chart3DViewer(chart);
        viewer.setMaxSize(500, 392);

        return viewer;

    }
    String chemin = "src/edu/CupTest2/images/" + "stat.pdf";

    @FXML
    private void pdf(ActionEvent event) throws IOException, DocumentException {
        Document d = new Document();

        SnapshotParameters s = new SnapshotParameters();
        WritableImage i = new WritableImage(100, 100);
        WritableImage img = barchart2.snapshot(new SnapshotParameters(), null);
        String url = "snapshot" + new Date().getTime() + ".png";
        File output = new File(url);
        ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", output);

        PdfWriter.getInstance(d, new FileOutputStream(chemin));

        d.open();
        Image is = Image.getInstance(url);
        d.add(is);
        d.close();
        File file = new File(chemin);
            Desktop.getDesktop().open(file);
    }

@FXML
        private void afficher(ActionEvent event) {
    }
     
}
