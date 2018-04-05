/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.entities;

import edu.cupcake.services.AdressesService;
import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 *
 * @author berrahal
 */
@Entity
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date Date;
    private String reference;
    private int adresse_id;
    private int utilisateur_id;
    private float amount;
    private String PaymentState;
    
    private String adresse;

    
    
    public final  String getAdresseByOrder(int id) throws SQLException
      {
          AdressesService sr = new AdressesService();
          Adresses adresse = new Adresses();
          adresse=sr.getAdressebyId(id);
          String adressestring = adresse.getNom()+" "+adresse.getPrenom()+", "+adresse.getAdresse()+", "+adresse.getVille();
          
          return adressestring;
          
      }

    public Orders(){
        
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Orders(Date Date, String reference, int adresse_id, int utilisateur_id, float amount, String PaymentState) throws SQLException {
        this.Date = Date;
        this.reference = reference;
        this.adresse_id = adresse_id;
        this.utilisateur_id = utilisateur_id;
        this.amount = amount;
        this.PaymentState=PaymentState;
        
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getAdresse_id() {
        return adresse_id;
    }

    public void setAdresse_id(int adresse_id) {
        this.adresse_id = adresse_id;
    }

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getPaymentState() {
        return PaymentState;
    }

    public void setPaymentState(String PaymentState) {
        this.PaymentState = PaymentState;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Orders{" +  ", Date=" + Date + ", reference=" + reference + ", adresse_id=" + adresse_id + ", utilisateur_id=" + utilisateur_id + ", amount=" + amount + ", PaymentState=" + PaymentState + ", adresse=" + adresse + '}';
    }

    
    
}
