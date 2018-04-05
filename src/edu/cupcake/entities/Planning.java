/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.entities;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.time.LocalDateTime;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 *
 * @author berrahal
 */
@Entity
public class Planning implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int utilisateur_id;
    private LocalDateTime datestart;
    private LocalDateTime dateend;
    private int line_order;

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public LocalDateTime getDatestart() {
        return datestart;
    }

    public void setDatestart(LocalDateTime datestart) {
        this.datestart = datestart;
    }

    public LocalDateTime getDateend() {
        return dateend;
    }

    public void setDateend(LocalDateTime dateend) {
        this.dateend = dateend;
    }

    public int getLine_order() {
        return line_order;
    }

    public void setLine_order(int line_order) {
        this.line_order = line_order;
    }

    public Planning(int utilisateur_id, LocalDateTime datestart, LocalDateTime dateend, int line_order) {
        this.utilisateur_id = utilisateur_id;
        this.datestart = datestart;
        this.dateend = dateend;
        this.line_order = line_order;
    }

    public Planning() {
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
        if (!(object instanceof Planning)) {
            return false;
        }
        Planning other = (Planning) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.cupcake.entities.Planning[ id=" + id + " ]";
    }
    
}
