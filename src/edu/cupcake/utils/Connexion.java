/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author berrahal
 */
public class Connexion {
      private final String url="jdbc:mysql://localhost:3306/cupcup";
    private final String user="root";
    private final String password="";
    private Connection connection;
    private static Connexion data;

    private Connexion() {
        try {
            connection= DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }
    
    public static Connexion getInstance()
       {
           if(data == null)
           {
               data = new Connexion();
           }
           return data;
       }
    
}
