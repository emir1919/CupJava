/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import com.restfb.types.User;
import java.io.InputStream;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;




/**
 *
 * @author El Kamel
 */
public class ShareService {
    
    public void partager(){
    
         String domain="https://www.google.tn/";
         String appId="234205360478243";
         String appSecret="4a9918ab8152c7fde7f7c43411b3c597";
         String authURL="https://graph.facebook.com/oauth/authorize?type=user_agent&client_id="+appId
                 +"&redirect_uri="+domain+"&scope=publish_actions";
//         
//         
         System.setProperty("webdriver.chrome.driver", "src/tn/esprit/cupcake/API/chromedriver_win32/chromedriver.exe");
         WebDriver driver = new ChromeDriver();
         driver.get(authURL);
//         String accessToken="EAACEdEose0cBAICfEUyMS4RY6aboYR30ZBZAIIYZCov2xnR2BRNBBHGwpF9pZAik5sugg5cKHCDV4himLKfVteeeyO300sLTiwYupWCc4e8tusUZCiOm0Jtr20CEJbgAoqTRJnAr9qlRBuZCBMgZCR26C1O39DGvowZC6aPEkzI73pMKyzUhUde6M7eX9bGlkCZBgCAqwI5yYiOVzncaSsXxS" ;
         String accessToken="" ;

         boolean ok=true;
         while(ok)
         {
             
             if ( ! driver.getCurrentUrl().contains("facebook.com")  )
//                 && (driver.getCurrentUrl()!=authURL)
             {
                 String url =driver.getCurrentUrl();
                 accessToken =url.replaceAll(".*#access_token=(.+)&.*", "$1");
                 System.out.println(accessToken);
                
                 ok=false;
              }
             
         }
     
         System.out.println("hédhi el token : **************  "+accessToken);
         driver.get("https://fb.com/me");
         
         
         FacebookClient fbClient = new DefaultFacebookClient(accessToken);
//         User me = fbClient.fetchObject("me", User.class);
        
//         System.out.println(me.getName());
       
         FacebookType publishMessageResponse =
         fbClient.publish(
                 "me/feed", 
                 FacebookType.class,
                 com.restfb.Parameter.with("message", "This is a test"),
                 com.restfb.Parameter.with("link", "http://197.8.185.224/TestFOS/web/app_dev.php/event"));
         
         
         System.out.println("Published message ID: " + publishMessageResponse.getId());

           driver.quit(); 
    }
    
}
