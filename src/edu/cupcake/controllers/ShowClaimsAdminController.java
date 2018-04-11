/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import edu.cupcake.entities.Claims;
import edu.cupcake.entities.Enseigne;
import edu.cupcake.entities.TypeClaim;
import edu.cupcake.services.ClaimsService;
import edu.cupcake.services.EnseigneServices;
import edu.cupcake.services.TypeClaimService;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Colors;
import com.orsoncharts.TitleAnchor;
import com.orsoncharts.fx.Chart3DViewer;
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.data.StandardPieDataset3D;
import com.orsoncharts.label.StandardPieLabelGenerator;
import com.orsoncharts.plot.PiePlot3D;
import static com.orsoncharts.label.StandardPieLabelGenerator.PERCENT_TEMPLATE;
import edu.cupcake.utils.Connexion;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import edu.cupcake.utils.Routes;




/**
 * FXML Controller class
 *
 * @author El Kamel
 */
public class ShowClaimsAdminController implements Initializable {
       
    @FXML
    private ImageView logo;

    @FXML
    private JFXButton GP;

    @FXML
    private TableView<Claims> tableClaims;

    @FXML
    private TableColumn<Claims,String> txtNom;

    @FXML
    private TableColumn<Claims,String> txtEmail;

    @FXML
    private TableColumn<Claims,Integer> txtTel;

    @FXML
    private TableColumn<Claims,Integer> txtType;

    @FXML
    private TableColumn txtEns;

    @FXML
    private TableColumn<Claims,String> txtDescription;

    @FXML
    private JFXTextField searchField;

    @FXML
    private TableView<TypeClaim> tableType;

    @FXML
    private TableColumn<TypeClaim,String> txtTypeType;

    @FXML
    private TableColumn<TypeClaim,Integer> txtTybeNBC;

    @FXML
    private TableView<Enseigne> tableEns;

    @FXML
    private TableColumn<Enseigne,String> txtEnsName;

    @FXML
    private TableColumn<Enseigne,String> txtEnsNBC;
       
    @FXML
    private AnchorPane pane;
    
    @FXML
    private AnchorPane pane2;
    
    private ScrollPane sc;
    
    ObservableList<Claims> data1;
    ObservableList<TypeClaim> data2;
    ObservableList<Enseigne> data3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(Routes.ShowClaimsAdminVIEW));
//            AnchorPane BigAN = (AnchorPane) loader.load();
//
//        } catch (IOException ex) {
//            Logger.getLogger(ShowClaimsAdminController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        sc.setPannable(true);
//        sc.setContent(BigAN);
                 
        try {
            
            Node n = createDemo();
            pane.getChildren().add(n);
            
            Node n2 = createDemo2();
            pane2.getChildren().add(n2);
           
            
        } catch (SQLException ex) {
            Logger.getLogger(ShowClaimsAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        loadDataFromDatabase();
        loadDataFromDatabase2();
        loadDataFromDatabase3();    
        
        FilteredList<Claims> filteredData = new FilteredList<>(data1, e -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(c -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (c.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (c.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        
        SortedList<Claims> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableClaims.comparatorProperty());
        tableClaims.setItems(sortedData);

    }    
    
    @FXML
    void GestionAdds(ActionEvent event) throws IOException {
        
        Parent root=null;
        root = FXMLLoader.load(getClass().getResource(Routes.ShowAddsVIEW));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void afficherImage(MouseEvent event) {

    }
    
    @FXML
    private void loadDataFromDatabase() {
         Connection cn = Connexion.getInstance().getConnection();  

        try {
            
            EnseigneServices es = new EnseigneServices();
            Enseigne e = new Enseigne();
            data1 = FXCollections.observableArrayList();

           
            String req = "SELECT * FROM claims";
            PreparedStatement pre = cn.prepareStatement(req);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                
                e = es.getEnseignebyId(rs.getInt("enseigne_id"));

                data1.add(new Claims(
                        rs.getInt("id"),
                        rs.getDate("date"),
                        rs.getString("Description"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("phone"),
                        rs.getInt("typeclaim_id"),
                        rs.getInt("enseigne_id"),
                        e.getName()
                ));
                

            }
            
            

            txtNom.setCellValueFactory(new PropertyValueFactory<>("name"));
            txtEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            txtTel.setCellValueFactory(new PropertyValueFactory<>("phone"));

            txtEns.setCellValueFactory(new PropertyValueFactory("EnsName"));

            
            
            txtType.setCellValueFactory(new PropertyValueFactory<>("typeclaimId"));
            txtDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));


            tableClaims.setItems(data1);

         
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    
    private void loadDataFromDatabase2(){
        
                 Connection cn = Connexion.getInstance().getConnection();  


        try {
        
        ClaimsService cs = new ClaimsService();
        TypeClaimService tcs = new TypeClaimService();
        @SuppressWarnings("UnusedAssignment")
        List<TypeClaim> types = new ArrayList<>();
        types = tcs.FindAll();
        
          for (TypeClaim t : types){
         
            @SuppressWarnings("UnusedAssignment")
            List<Claims> claims = new ArrayList<>();
            claims = cs.findBytypeclaim(t.getId());
            int nb = claims.size();
            t.setNbClaims(nb);
            tcs.EditTypeClaim(t,t.getId());
            
          }
        
            data2 = FXCollections.observableArrayList();

           
            String req2 = "SELECT * FROM type_claim";
            PreparedStatement pre2 = cn.prepareStatement(req2);
            ResultSet rs2 = pre2.executeQuery();

            while (rs2.next()) {

                data2.add(new TypeClaim(
                        rs2.getInt("id"),
                        rs2.getString("name"),
                        rs2.getInt("nbClaims")
                ));
                

            }

            txtTypeType.setCellValueFactory(new PropertyValueFactory<>("name"));
            txtTybeNBC.setCellValueFactory(new PropertyValueFactory<>("nbClaims"));
            
            tableType.setItems(data2); 
        
        
        
        
            } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    private void loadDataFromDatabase3(){
        
                Connection cn = Connexion.getInstance().getConnection();  

        try {
        
        ClaimsService cs = new ClaimsService();
        EnseigneServices es = new EnseigneServices();
        @SuppressWarnings("UnusedAssignment")
        List<Enseigne> enseignes = new ArrayList<>();
        enseignes = es.selectAllEnseigne();
        
 
        
          for (Enseigne e : enseignes){
         
            @SuppressWarnings("UnusedAssignment")
            List<Claims> claims = new ArrayList<>();
            claims = cs.findByenseneig(e.getId());
            int nb = claims.size();
            e.setNbReclamation(nb);
            es.modifierEnseigne(e.getId(),e.getName(),e.getAddress(),e.getPhoneNumber(),e.getFax(),e.getEmail(),e.getWebSite(),e.getLogo(),e.getCodeRc(),e.getDescription());
          }
        
            data3 = FXCollections.observableArrayList();

           
            String req2 = "SELECT * FROM enseigne";
            PreparedStatement pre2 = cn.prepareStatement(req2);
            ResultSet rs2 = pre2.executeQuery();

            while (rs2.next()) {

                data3.add(new Enseigne(
                                 
                rs2.getInt("id") ,  
                rs2.getInt("user_id"), 
                rs2.getString("Name"),
                rs2.getString("Address"),
                rs2.getString("PhoneNumber"),
                rs2.getString("Fax"),
                rs2.getString("Email"),  
                rs2.getString("Website"),
                rs2.getString("Logo"),                  
                rs2.getString("CodeRC"),
                rs2.getString("Description"),
                rs2.getInt("NbReclamation")
                                                    
                ));
                

            }

            txtEnsName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            txtEnsNBC.setCellValueFactory(new PropertyValueFactory<>("nbClaims"));
         
            tableEns.setItems(data3); 
          
        
            } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public static Node createDemo() throws SQLException {
        PieDataset3D<String> dataset = createDataset();
        Chart3D chart = createChart(dataset);
        Chart3DViewer viewer = new Chart3DViewer(chart);
        viewer.setMaxSize(1000,392);
        
       return viewer;      
        
    }
    
    private static PieDataset3D<String> createDataset() throws SQLException {
        
        
        ClaimsService cs = new ClaimsService();
        TypeClaimService tcs = new TypeClaimService();
        @SuppressWarnings("UnusedAssignment")
        List<TypeClaim> types = new ArrayList<>();
        StandardPieDataset3D<String> dataset = new StandardPieDataset3D<>();
        
        
        types = tcs.FindAll();
        
          for (TypeClaim t : types){
         
            @SuppressWarnings("UnusedAssignment")
            List<Claims> claims = new ArrayList<>();
            claims = cs.findBytypeclaim(t.getId());
            int nb = claims.size();
            dataset.add(t.getName(),nb);
        
          }
          
        return dataset; 
    }
    
    private static Chart3D createChart(PieDataset3D<String> dataset) throws SQLException {
        final Chart3D chart = Chart3DFactory.createPieChart("Nombre de réclamation par type","", createDataset());
        
        chart.setTitleAnchor(TitleAnchor.TOP_LEFT);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setLegendLabelGenerator(new StandardPieLabelGenerator(PERCENT_TEMPLATE));
        plot.setSectionLabelGenerator(new StandardPieLabelGenerator(PERCENT_TEMPLATE));
        plot.setSectionColors(Colors.createFancyLightColors());
        return chart;
    }
    
    public static Node createDemo2() throws SQLException {
        PieDataset3D<String> dataset = createDataset2();
        Chart3D chart = createChart2(dataset);
        Chart3DViewer viewer = new Chart3DViewer(chart);
        viewer.setMaxSize(1000,392);
        
       return viewer;      
        
    }
    
    private static PieDataset3D<String> createDataset2() throws SQLException {
        
        ClaimsService cs = new ClaimsService();
        EnseigneServices es = new EnseigneServices();
        @SuppressWarnings("UnusedAssignment")
        List<Enseigne> enseignes = new ArrayList<>();
        StandardPieDataset3D<String> dataset = new StandardPieDataset3D<>();
        
        enseignes = es.selectAllEnseigne();
            
        for (Enseigne e : enseignes){
         
            @SuppressWarnings("UnusedAssignment")
            List<Claims> claims = new ArrayList<>();
            claims = cs.findByenseneig(e.getId());
            int nb = claims.size();
            dataset.add(e.getName(),nb);
        }
        
          
        return dataset; 
    }
    
    private static Chart3D createChart2(PieDataset3D<String> dataset) throws SQLException {
        final Chart3D chart = Chart3DFactory.createPieChart("Nombre de réclamation par enseigne","", createDataset2());
        
        chart.setTitleAnchor(TitleAnchor.TOP_LEFT);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setLegendLabelGenerator(new StandardPieLabelGenerator(PERCENT_TEMPLATE));
        plot.setSectionLabelGenerator(new StandardPieLabelGenerator(PERCENT_TEMPLATE));
        plot.setSectionColors(Colors.createFancyLightColors());
        return chart;
    }   
        

        
        
        
}
