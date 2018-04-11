/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.entities;

/**
 *
 * @author Emir
 */
public class favory_brand {
    private int id;
    private int user_id;
    private int enseigne_id;

    public favory_brand(int id, int user_id, int enseigne_id) {
        this.id = id;
        this.user_id = user_id;
        this.enseigne_id = enseigne_id;
    }

    public favory_brand() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEnseigne_id() {
        return enseigne_id;
    }

    public void setEnseigne_id(int enseigne_id) {
        this.enseigne_id = enseigne_id;
    }

    @Override
    public String toString() {
        return "FavoryBrand{" + "id=" + id + ", user_id=" + user_id + ", enseigne_id=" + enseigne_id + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.id;
        hash = 41 * hash + this.user_id;
        hash = 41 * hash + this.enseigne_id;
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
        final favory_brand other = (favory_brand) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.user_id != other.user_id) {
            return false;
        }
        if (this.enseigne_id != other.enseigne_id) {
            return false;
        }
        return true;
    }
    
}
