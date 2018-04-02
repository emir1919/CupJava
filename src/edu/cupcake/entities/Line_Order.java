/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.entities;

import edu.cupcake.services.ProductService;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author berrahal
 */
@Entity
public class Line_Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int qte;
    private int commande_id;
    private int product_id;
    private String affected;

  
    
    

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getCommande_id() {
        return commande_id;
    }

    public void setCommande_id(int commande_id) {
        this.commande_id = commande_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getAffected() {
        return affected;
    }

    public void setAffected(String affected) {
        this.affected = affected;
    }

    @Override
    public String toString() {
        return "Line_Order{" + "id=" + id + ", qte=" + qte + ", commande_id=" + commande_id + ", product_id=" + product_id + ", affected=" + affected + '}';
    }

    public Line_Order(int qte, int commande_id, int product_id, String affected) {
        this.qte = qte;
        this.commande_id = commande_id;
        this.product_id = product_id;
        this.affected = affected;
    }

    public Line_Order() {
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
        if (!(object instanceof Line_Order)) {
            return false;
        }
        Line_Order other = (Line_Order) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

   
    
}
