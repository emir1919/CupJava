/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CupTest2.entities;

import java.sql.Date;

/**
 *
 * @author Emir
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private double rating;
    private double reduction;
    private String image_name;
    private int sales;
    private Date updated_at;
    private Date added_at;
    private int subcategory_id;
    private int enseigne_id;
    //private int bakery_id;

    public Product(){}
    public Product(int id, String name, double price, String description, double rating, double reduction, String image_name, int sales, Date updated_at, Date added_at, int subcategory_id, int enseigne_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.rating = rating;
        this.reduction = reduction;
        this.image_name = image_name;
        this.sales = sales;
        this.updated_at = updated_at;
        this.added_at = added_at;
        this.subcategory_id = subcategory_id;
        this.enseigne_id = enseigne_id;
    }

    public Product(String name, double price, String description, double rating, double reduction, String image_name, int sales, Date updated_at, Date added_at, int subcategory_id, int enseigne_id) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.rating = rating;
        this.reduction = reduction;
        this.image_name = image_name;
        this.sales = sales;
        this.updated_at = updated_at;
        this.added_at = added_at;
        this.subcategory_id = subcategory_id;
        this.enseigne_id = enseigne_id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getAdded_at() {
        return added_at;
    }

    public void setAdded_at(Date added_at) {
        this.added_at = added_at;
    }

    public int getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public int getEnseigne_id() {
        return enseigne_id;
    }

    public void setEnseigne_id(int enseigne_id) {
        this.enseigne_id = enseigne_id;
    }

    @Override
    public String toString() {
        return this.getName();
    }
    
}
