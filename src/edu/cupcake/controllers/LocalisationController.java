/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.lynden.gmapsfx.GoogleMapView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;

import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.google.code.geocoder.Geocoder;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;
import edu.cupcake.entities.Bakery;
import edu.cupcake.entities.Enseigne;
import static edu.cupcake.controllers.AddBakeryController.IdEnseigne;
import edu.cupcake.services.EnseigneServices;
import edu.cupcake.services.PatisserieServices;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class LocalisationController extends JavascriptObject implements Initializable,MapComponentInitializedListener {

    private Button InfoBrandButton;
    private Button BakeryButton;
    @FXML
    private GoogleMapView mapView;


    private StringProperty address = new SimpleStringProperty();
    private GeocodingService geocodingService;
    private GoogleMap googlemap;
    private List<Marker> markers = null;

    String adresseEvent = "";
    private float longitude;
    private float latitude;
    LatLong latLong = null;
    String path = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        mapView.addMapInializedListener(this);
    }

    public void addressTextFieldAction(String adresse) {
        
       geocodingService.geocode(adresse, new GeocodingServiceCallback() {
            @Override
            public void geocodedResultsReceived(GeocodingResult[] results, GeocoderStatus status) {
                LatLong latLong = null;
                
                if (status == GeocoderStatus.ZERO_RESULTS) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No matching address found");
                    alert.show();
                    return ;
                } else if (results.length > 1) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Multiple results found, showing the first one.");
                    alert.show();
                    latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
                    latitude = (float) results[0].getGeometry().getLocation().getLatitude();
                    longitude = (float) results[0].getGeometry().getLocation().getLongitude();
                } else {
                    latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
                    latitude = (float) results[0].getGeometry().getLocation().getLatitude();
                    longitude = (float) results[0].getGeometry().getLocation().getLongitude();
                }
                System.out.println(latLong);
                Marker marker=new Marker(new MarkerOptions().position(latLong));

        googlemap.addMarker(marker);
            }
        });  
        
    }

   

    @Override
    public void mapInitialized() {
        geocodingService = new GeocodingService();
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(36.899527163883356, 10.189837873680062))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

       googlemap= mapView.createMap(mapOptions);
        //Marker marker=new Marker(new MarkerOptions().position(new LatLong(36.800399, 10.186620)));

        //googlemap.addMarker(marker);
        EnseigneServices es=new EnseigneServices();
        
        PatisserieServices ps=new PatisserieServices();
        List<Bakery> bakeries=new ArrayList<Bakery>();
        bakeries=ps.getBakerybyBrand(es.getEnseignebyUserId(cupcake.Cupcake.user.getId()).getId());
        for(Bakery b : bakeries)
        {
                      addressTextFieldAction(b.getAddress());

        }


    }

    

}
