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
public class ObservableProduct extends Product {

    private String brand;
    private String subCat;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSubCat() {
        return subCat;
    }

    public void setSubCat(String subCat) {
        this.subCat = subCat;
    }

    public ObservableProduct() {
    }

    public ObservableProduct(int id, String name, double price, String description, double rating, double reduction, String image_name, int sales, Date updated_at, Date added_at, int subcategory_id, int enseigne_id, String brand, String subCat) {
        super(id, name, price, description, rating, reduction, image_name, sales, updated_at, added_at, subcategory_id, enseigne_id);
        this.brand = brand;
        this.subCat = subCat;
    }

    public ObservableProduct(String name, double price, String description, double rating, double reduction, String image_name, int sales, Date updated_at, Date added_at, int subcategory_id, int enseigne_id, String brand, String subCat) {
        super(name, price, description, rating, reduction, image_name, sales, updated_at, added_at, subcategory_id, enseigne_id);
        this.brand = brand;
        this.subCat = subCat;
    }
    
    
    
}
