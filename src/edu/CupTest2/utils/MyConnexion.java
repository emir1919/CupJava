/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import edu.CupTest2.entities.Bakery;
import edu.CupTest2.entities.Enseigne;


/**
 *
 * @author Emir
 */
public class MyConnexion {
    static MyConnexion instance;
    Connection con;

    private MyConnexion() {
        try {
            String url = "jdbc:mysql://localhost:3306/cupcup";
            String login = "root";
            String pwd = "";
            con = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion etablie");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static MyConnexion getInstance() {
        if (instance == null) {
            instance = new MyConnexion();
        }
        return instance;
    }

    public Connection getConection() {
        return con;
    }
}
