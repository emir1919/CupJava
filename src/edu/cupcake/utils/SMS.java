/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import edu.cupcake.entities.Favorite;
import edu.cupcake.entities.Product;
import edu.cupcake.services.FavoriteService;
import java.util.ArrayList;

/**
 *
 * @author yassi
 */
public class SMS {
    
    public static final String ACCOUNT_SID = "ACbfd5caa728659293cead5ef9c4dad0f0";
    public static final String AUTH_TOKEN = "ca12f5d45505bfc36005ca568e259e8e";
    
    
    public void sendSMS(String msg, String toPhone) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(toPhone),
            new PhoneNumber("+12565790584"), 
            msg).create();

        System.out.println(message.getSid());
    }
    
    public void onProductAdded(Product p){
        FavoriteService service = new FavoriteService();
        String msg = "Le produit " + p.getName() + " est disponible a un prix de "+p.getPrice();
        ArrayList<String> list = service.getUsersByFav(p.getSubcategory_id());
        for (String number : list) {
            sendSMS(msg, "+216"+number);
        }
    }
    
    
}
