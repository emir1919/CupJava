/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.entities;

import java.sql.Date;

/**
 *
 * @author yassi
 */
public class Subcategory {
    private int id;
    private String name;
    private String image_name;
    private Date updated_at;
    private int category_id;
    
    public Subcategory(){}

    public Subcategory(String name, String image_name, Date updated_at, int category_id) {
        this.name = name;
        this.image_name = image_name;
        this.updated_at = updated_at;
        this.category_id = category_id;
    }

    public Subcategory(int id, String name, String image_name, Date updated_at, int category_id) {
        this.id = id;
        this.name = name;
        this.image_name = image_name;
        this.updated_at = updated_at;
        this.category_id = category_id;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return this.getName();
    }
    
    
    
}
