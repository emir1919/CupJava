/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.LoadEvent;
import com.calendarfx.view.CalendarView;
import edu.cupcake.entities.Planning;
import edu.cupcake.services.PlanningService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import sun.misc.Signal;

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class LivreurController implements Initializable {

    @FXML
    private AnchorPane calendarpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          CalendarView calendarView = new CalendarView(); 

                Calendar disponible = new Calendar("Disponible"); 
                Calendar affected = new Calendar("Holidays");

                disponible.setStyle(Style.STYLE1); 
                affected.setStyle(Style.STYLE6);

                CalendarSource myCalendarSource = new CalendarSource("My Calendars"); 
                myCalendarSource.getCalendars().addAll(disponible, affected);

                calendarView.getCalendarSources().addAll(myCalendarSource); 

                calendarView.setRequestedTime(LocalTime.now());

                Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
                        @Override
                        public void run() {
                                while (true) {
                                        Platform.runLater(() -> {
                                                calendarView.setToday(LocalDate.now());
                                                calendarView.setTime(LocalTime.now());
                                        });

                                        try {
                                                // update every 10 seconds
                                                sleep(10000);
                                        } catch (InterruptedException e) {
                                                e.printStackTrace();
                                        }

                                }
                        };
                };
                calendarView.setOnMousePressed((event) -> {
             
  
            });
                              PlanningService psr= new PlanningService();

                               List<Planning> plannings = new ArrayList<Planning>();
        try {
            plannings=psr.getPlanningsbyUserId(cupcake.Cupcake.user.getId());
        } catch (SQLException ex) {
            Logger.getLogger(LivreurController.class.getName()).log(Level.SEVERE, null, ex);
        }
                               Iterator<Planning> it = plannings.iterator();
                               while (it.hasNext()) {
            Planning next = it.next();
                                   
                                   System.err.println(next.getDatestart());
                                   System.err.println(next.getLine_order());
                                   if (next.getLine_order()==0) {
                 Entry<String> dentistAppointment = new Entry<>("Disponible");
                dentistAppointment.setTitle("Disponible");
                dentistAppointment.setInterval( next.getDatestart(), next.getDateend());
                dentistAppointment.setId(""+next.getId());
               disponible.addEntry(dentistAppointment);
                                   }
                                   else 
                                   {
                                       Entry<String> dentistAppointment = new Entry<>("Affecté");
                dentistAppointment.setTitle("Affecté");
                dentistAppointment.setInterval( next.getDatestart(), next.getDateend());
                dentistAppointment.setId(""+next.getId());
               affected.addEntry(dentistAppointment);
                                   }
                                   
                                  
            
        }

             //calendarView.addEventHandler(CalendarEvent.CALENDAR_CHANGED, eventHandler);
                
        LoadEvent event;
       
                updateTimeThread.setPriority(Thread.MIN_PRIORITY);
                updateTimeThread.setDaemon(true);
                updateTimeThread.start();
                    calendarpane.getChildren().add(calendarView);
    }    
    
}
