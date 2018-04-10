/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.entities;

/**
 *
 * @author yassi
 */
public class Favorite {
    private int user_id;
    private int subcategory_id;

    public Favorite(int user_id, int subcategory_id) {
        this.user_id = user_id;
        this.subcategory_id = subcategory_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
    }
    
    
}
