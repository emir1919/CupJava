/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cupcake;

import com.jfoenix.controls.JFXDecorator;
import edu.cupcake.entities.Adresses;
import edu.cupcake.entities.Panier;
import edu.cupcake.entities.Product;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import edu.cupcake.entities.Users;
import edu.cupcake.services.ProductService;
import edu.cupcake.utils.Routing;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.persistence.sessions.Session;

/**
 *
 * @author berrahal
 */
public class Cupcake extends Application {

    public static Users user = null;
    public static   List<Panier> Panier = new ArrayList<Panier>();
    public static String currentroute = Routing.LOGIN;
    public static String lastroute = null;

    @Override
    public void start(Stage primaryStage) {
        //test
        ProductService psr = new ProductService();
        Product p1 = new Product();
        Product p2 = new Product();
        p1=psr.getProductById(1);
        p2=psr.getProductById(2);
        Panier pa1 = new Panier();
        Panier pa2 = new Panier();

        pa1.setProduit(p1);
        pa1.setQte(3);
        
        pa2.setProduit(p2);
        pa2.setQte(1);
        
        Panier.add(pa1);
        Panier.add(pa2);
        
        
        
        
        //endtest
        try {
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Login.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource(Routing.HOME));

            //AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
            primaryStage.setMaximized(true);
            primaryStage.setResizable(true);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Cupcake");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
