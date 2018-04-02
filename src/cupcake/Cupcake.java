/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cupcake;

import com.jfoenix.controls.JFXDecorator;
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
import edu.cupcake.utils.Routing;
import org.eclipse.persistence.sessions.Session;

/**
 *
 * @author berrahal
 */
public class Cupcake extends Application {

    public static Users user = null;
    public static String currentroute = Routing.LOGIN;
    public static String lastroute = null;

    @Override
    public void start(Stage primaryStage) {
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
