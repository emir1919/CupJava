/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author berrahal
 */
public class ObservableLineOrders extends Line_Order {

    private int id;

   
    private Date date;
    private String produit;
    private float prix;
    private String payment;
    private int bakeryId;

    public int getBakeryId() {
        return bakeryId;
    }

    public void setBakeryId(int bakeryId) {
        this.bakeryId = bakeryId;
    }

  public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

  

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

   

    public ObservableLineOrders(int id,Date date, String produit, float prix, String payment, int qte, int commande_id, int product_id, String affected) {
      super(qte, commande_id, product_id, affected);
      this.id=id;  
      this.date = date;
        this.produit = produit;
        this.prix = prix;
        this.payment = payment;
        //this.bakeryId = bakeryId;
    }
    
    
}
