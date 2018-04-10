/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.entities;

import java.util.Objects;


/**
 *
 * @author El Kamel
 */
public class Adds {
    
private int id;
private int enseigne_id;
private String Title;
private String Image;
private String Description;
private int Rank ;

    public Adds() {
    }



    public Adds(int enseigne_id, String Title, String Image, String Description, int Rank) {
        this.enseigne_id = enseigne_id;
        this.Title = Title;
        this.Image = Image;
        this.Description = Description;
        this.Rank = Rank;
    }

    public Adds(int id, int enseigne_id, String Title, String Image, String Description, int Rank) {
        this.id = id;
        this.enseigne_id = enseigne_id;
        this.Title = Title;
        this.Image = Image;
        this.Description = Description;
        this.Rank = Rank;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnseigne_id() {
        return enseigne_id;
    }

    public void setEnseigne_id(int enseigne_id) {
        this.enseigne_id = enseigne_id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int Rank) {
        this.Rank = Rank;
    }

    @Override
    public String toString() {
        return "Adds{" + "id=" + id + ", enseigne_id=" + enseigne_id + ", Title=" + Title + ", Image=" + Image + ", Description=" + Description + ", Rank=" + Rank + '}';
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
        final Adds other = (Adds) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.enseigne_id != other.enseigne_id) {
            return false;
        }
        if (this.Rank != other.Rank) {
            return false;
        }
        if (!Objects.equals(this.Title, other.Title)) {
            return false;
        }
        if (!Objects.equals(this.Image, other.Image)) {
            return false;
        }
        if (!Objects.equals(this.Description, other.Description)) {
            return false;
        }
        return true;
        
    }
    
    


    
}
