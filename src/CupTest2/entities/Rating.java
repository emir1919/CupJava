/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.entities;

import javafx.css.Styleable;
import javafx.event.EventTarget;
import javafx.scene.control.Control;
import javafx.scene.control.Skinnable;



/**
 *
 * @author Emir
 */
public class Rating{
    private int id;
    private int enseigne_id;
    private int user_id;
    private float note;
    public Rating(int id, int enseigne_id, int user_id, float note) {
        this.id = id;
        this.enseigne_id = enseigne_id;
        this.user_id = user_id;
        this.note = note;
    }

    public Rating() {
    }

    @Override
    public String toString() {
        return "Rating{" + "id=" + id + ", enseigne_id=" + enseigne_id + ", user_id=" + user_id + ", note=" + note + '}';
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
        hash = 47 * hash + this.enseigne_id;
        hash = 47 * hash + this.user_id;
        hash = 47 * hash + Float.floatToIntBits(this.note);
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
        final Rating other = (Rating) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.enseigne_id != other.enseigne_id) {
            return false;
        }
        if (this.user_id != other.user_id) {
            return false;
        }
        if (Float.floatToIntBits(this.note) != Float.floatToIntBits(other.note)) {
            return false;
        }
        return true;
    }

   
}


