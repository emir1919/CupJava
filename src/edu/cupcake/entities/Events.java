/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.entities;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author El Kamel
 */
public class Events {
    
    private Integer id;
    
    private Date dateStart;
   
    private String title;
   
    private String description;
 
    private String image;
   
    private String type;
   
    private String adress;
 
    private int nbPlaces;
   
    private int nbPart;
    
    private int enseigneId;

    public Events() {
    }
    
    
    public Events(Integer id, Date dateStart, String title, String description, String image, String type, String adress, int nbPlaces, int nbPart, int enseigneId) {
        this.id = id;
        this.dateStart = dateStart;
        this.title = title;
        this.description = description;
        this.image = image;
        this.type = type;
        this.adress = adress;
        this.nbPlaces = nbPlaces;
        this.nbPart = nbPart;
        this.enseigneId = enseigneId;
    }
    
     public Events(Date dateStart, String title, String description, String image, String type, String adress, int nbPlaces, int nbPart, int enseigneId) {
        this.dateStart = dateStart;
        this.title = title;
        this.description = description;
        this.image = image;
        this.type = type;
        this.adress = adress;
        this.nbPlaces = nbPlaces;
        this.nbPart = nbPart;
        this.enseigneId = enseigneId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public int getNbPart() {
        return nbPart;
    }

    public void setNbPart(int nbPart) {
        this.nbPart = nbPart;
    }

    public int getEnseigneId() {
        return enseigneId;
    }

    public void setEnseigneId(int enseigneId) {
        this.enseigneId = enseigneId;
    }

    @Override
    public String toString() {
        return "Events{" + "id=" + id + ", dateStart=" + dateStart + ", title=" + title + ", description=" + description + ", image=" + image + ", type=" + type + ", adress=" + adress + ", nbPlaces=" + nbPlaces + ", nbPart=" + nbPart + ", enseigneId=" + enseigneId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Events other = (Events) obj;
        if (this.nbPlaces != other.nbPlaces) {
            return false;
        }
        if (this.nbPart != other.nbPart) {
            return false;
        }
        if (this.enseigneId != other.enseigneId) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.adress, other.adress)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dateStart, other.dateStart)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
