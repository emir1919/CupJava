/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import edu.cupcake.entities.Line_Order;
import edu.cupcake.entities.Planning;
import edu.cupcake.entities.Users;
import edu.cupcake.services.Line_OrderService;
import edu.cupcake.services.PlanningService;
import edu.cupcake.services.UsersService;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class AffectationLivreurController implements Initializable {

    @FXML
    private AnchorPane calendarpane;
    public static Line_Order selected;
    public static Users selecteduser;

    public static Line_Order getSelected() {
        return selected;
    }

    public static void setSelected(Line_Order selected) {
        AffectationLivreurController.selected = selected;
    }
    public static Users getSelecteduser() {
        return selecteduser;
    }

    public static void setSelecteduser(Users selecteduser) {
        AffectationLivreurController.selecteduser = selecteduser;
    }


    /**
     * Initializes the controller class.
     */
   @Override
    public void initialize(URL url, ResourceBundle rb) {
          CalendarView calendarView = new CalendarView(); 
          
                Calendar disponible = new Calendar("Disponible"); 
                Calendar affected = new Calendar("Affected");
                disponible.setStyle(Calendar.Style.STYLE1); 
                affected.setStyle(Calendar.Style.STYLE6);

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
                            
                              
                               
                            affected.addEventHandler((CalendarEvent event) -> {
                             
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
                              Line_OrderService linesr= new Line_OrderService();
                            
                          affected.addEventHandler((CalendarEvent event) -> {
                                  if (event.isEntryAdded()) {
                                    Planning add = new Planning();
                                    
                                    add.setDatestart(event.getEntry().getInterval().getStartDateTime());
                                    add.setDateend(event.getEntry().getInterval().getEndDateTime());
                                    add.setLine_order(selected.getId());
                                      
                                    add.setUtilisateur_id(selecteduser.getId());
                                   try {
                                       int id=psr.addPlanningAffected(add);
                                       selected.setAffected("affected");
                                       linesr.EditAffectedLineorder(selected);

                                       event.getEntry().setId(""+id);
                                       event.getEntry().setTitle("Affectée");
                                       System.out.println("Is added nice id="+id);
                                   } catch (SQLException ex) {
                                       Logger.getLogger(LivreurController.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                      calendarView.refreshData();
                                }
                                    });  
                             
                             
                             affected.addEventHandler((CalendarEvent event) -> {
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
            calendarView.setDefaultCalendarProvider(param -> affected);

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
            plannings=psr.getPlanningsbyUserId(selecteduser.getId());
        } catch (SQLException ex) {
            Logger.getLogger(LivreurController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        Iterator<Planning> it = plannings.iterator();
        UsersService usr= new UsersService();
                               while (it.hasNext()) {
            Planning next = it.next();
                                   
                                   System.err.println(next.getDatestart());
                                   System.err.println(next.getId());
                                   if (next.getLine_order()==0) {
                 Entry<String> dentistAppointment = new Entry<>("Disponible");
                try {
                    dentistAppointment.setTitle("Disponible ("+usr.getUserById(next.getUtilisateur_id()).getUsername()+")");
                } catch (SQLException ex) {
                    Logger.getLogger(AffectationLivreurController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    
   /* public void Affect(CalendarEvent event) throws IOException
    {
        
        Parent root = FXMLLoader.load(getClass().getResource("/edu/cupcake/gui/AffectPlanningPopup.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
text.setText(""+event.getEntry().getId());
        stage.setMaximized(true);
        stage.setScene(new Scene(root));
        stage.show();


    }*/

    
    
}
