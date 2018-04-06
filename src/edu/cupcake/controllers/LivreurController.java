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
import com.calendarfx.view.DateSelectionModel;
import edu.cupcake.entities.Planning;
import edu.cupcake.services.PlanningService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import com.calendarfx.view.DeveloperConsole;
import java.sql.Timestamp;
import javafx.collections.ListChangeListener.Change;
import javafx.event.Event;

import javafx.application.Application;
import javafx.application.Platform;
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

                              getPlannings(disponible, affected);
                            
                              
                               
                            disponible.addEventHandler((CalendarEvent event) -> {
                             
                               Planning p = new Planning();
                               p.setDatestart(event.getEntry().getInterval().getStartDateTime());
                               p.setDateend(event.getEntry().getInterval().getEndDateTime());
              try {
                  psr.EditPlanningUser(p, Integer.parseInt(event.getEntry().getId()));
              } catch (SQLException ex) {
                  Logger.getLogger(LivreurController.class.getName()).log(Level.SEVERE, null, ex);
              }

              int n=Integer.parseInt(event.getEntry().getId());
                            
                                System.err.println("Modified nice");
                               
                                
                                
                            });
                              
                            
                             disponible.addEventHandler((CalendarEvent event) -> {
                                  if (event.isEntryAdded()) {
                                    Planning add = new Planning();
                                    add.setDatestart(event.getEntry().getInterval().getStartDateTime());
                                    add.setDateend(event.getEntry().getInterval().getEndDateTime());
                                    add.setUtilisateur_id(cupcake.Cupcake.user.getId());
                                   try {
                                       int id=psr.addPlanningDisponible(add);
                                       event.getEntry().setId(""+id);
                                       event.getEntry().setTitle("Disponible");
                                       System.out.println("Is added nice id="+id);
                                   } catch (SQLException ex) {
                                       Logger.getLogger(LivreurController.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                      calendarView.refreshData();
                                }
                                    });  
                             
                             
                             disponible.addEventHandler((CalendarEvent event) -> {
                                  if (event.isEntryRemoved()) {
                                      try {
                                          psr.DeletePlanning(Integer.parseInt(event.getEntry().getId()));
                                          System.err.println("Removed is"+event.getEntry().getId());
                                      } catch (SQLException ex) {
                                          Logger.getLogger(LivreurController.class.getName()).log(Level.SEVERE, null, ex);
                                      }
                                      
                                }
                                    });  
                             
                             
                             
                             
                            
                            
calendarView.getCalendars().get(0).setName("lol");
            calendarView.setDefaultCalendarProvider(param -> disponible);

                updateTimeThread.setPriority(Thread.MAX_PRIORITY);
                updateTimeThread.setDaemon(true);
                updateTimeThread.start();
                    calendarpane.getChildren().add(calendarView);
                    
    }   
    
    
    
    public void getPlannings(Calendar disponible, Calendar affected)
    {
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
                                   System.err.println(next.getId());
                                   if (next.getLine_order()==0) {
                 Entry<String> dentistAppointment = new Entry<>("Disponible");
                dentistAppointment.setTitle("Disponible");
                dentistAppointment.setInterval( next.getDatestart(), next.getDateend());
                dentistAppointment.setId(""+next.getId());
               disponible.addEntry(dentistAppointment);
                     System.err.println(dentistAppointment.getId());

                                   }
                                   else 
                                   {
                                       Entry<String> dentistAppointment = new Entry<>("Affecté");
                dentistAppointment.setTitle("Affecté");
                dentistAppointment.setInterval( next.getDatestart(), next.getDateend());
                dentistAppointment.setId(""+next.getId());
               affected.addEntry(dentistAppointment);
                                    System.err.println(dentistAppointment.getId());

                                   }
                                   
                                  
            
        }
    }
    
}
