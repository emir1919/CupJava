/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import edu.cupcake.entities.PaiementStripe;
import edu.cupcake.services.OrdersService;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import tray.notification.TrayNotification;
import tray.notification.NotificationType;
import tray.animations.AnimationType;   

/**
 * FXML Controller class
 *
 * @author berrahal
 */
public class CommandePaiementController implements Initializable {

    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField numeroCarte;
    @FXML
    private JFXTextField MoisValidite;
    @FXML
    private JFXTextField AnneeValidite;
    @FXML
    private JFXPasswordField ccvTextField;
    @FXML
    private JFXButton btnValider;
    @FXML
    private JFXButton btnAnnuler;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void validerFunction(ActionEvent event) throws SQLException, IOException {
        
        int mois = Integer.parseInt(MoisValidite.getText());
        int annee = Integer.parseInt(AnneeValidite.getText());
        
        
        
   
           
        
        
        Token token = PaiementStripe.getToken("pk_test_AuAMdXwE57NnBcd4Xld65Ez4", numeroCarte.getText(), mois, annee, ccvTextField.getText(), email.getText());
      
             if(token !=null){
            Charge ch= PaiementStripe.ChargePayement("rk_test_oGfrFNOjpnRPklUVzjelPHgf", "usd", "tok_visa", ValidationLivraisonCommandeController.currentorder.getAmount(),"sk_test_yIqEVjLUzA1vwKhr1PjhnS9I", numeroCarte.getText(), mois, annee, ccvTextField.getText(), email.getText());
                 OrdersService osr= new OrdersService();
                 osr.OrderPaid(ValidationLivraisonCommandeController.currentorder.getId());
            String tit = "Paiement réussi";
            String message = "Votre paiement a été traité avec succès";
            NotificationType notification = NotificationType.SUCCESS;
    
            TrayNotification tray = new TrayNotification(tit, message, notification);          
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(javafx.util.Duration.seconds(2));
       
        HomeController.afficherprofile=1;
        ProfileController.affichercommandes=1;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Home.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
       sendEmail();
        email.getScene().setRoot(root);
        
      
    }
        
    }

    @FXML
    private void AnnulerFunction(ActionEvent event) throws IOException {
         HomeController.afficherprofile=1;
        ProfileController.affichercommandes=1;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/cupcake/gui/Home.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        email.getScene().setRoot(root);
    }
    
    public void sendEmail(){
     try{
            String host ="smtp.gmail.com" ;
            String user = "berrahal.ryadh@gmail.com";
            String pass = "Alpha499789";
            String to = "ryadh.berrahal@esprit.tn";
            String from = "berrahal.ryadh@gmail.com";
            String subject = "Cupcake - Paiement effectué";
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
           
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new Date());
                
            
            MimeBodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        
         String file = "/home/berrahal/NetBeansProjects/" + "facture.pdf";
        String fileName = "commande.pdf";
    messageBodyPart = new MimeBodyPart();   
    DataSource source = new FileDataSource(file);      
    messageBodyPart.setDataHandler(new DataHandler(source));
    messageBodyPart.setFileName(fileName);
    messageBodyPart.setText("Bonjour "+ cupcake.Cupcake.user.getFirstname()+" "+cupcake.Cupcake.user.getLastname()+", votre paiement a été traité avec succès");
    multipart.addBodyPart(messageBodyPart);
    
        
     msg.setContent(multipart);

       
        
        
        
           Transport transport=mailSession.getTransport("smtp");
           transport.connect(host, user, pass);
           transport.sendMessage(msg, msg.getAllRecipients());
           transport.close();
                   System.out.println("Email envoyé");

        }catch(Exception ex)
        {
            System.out.println(ex);
        }
}
    
}
