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
public class Stock {
    private int qte;
    private int product_id;
    private int bakery_id;
    private int sales;

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public Stock() {
    }

    public int getBakery_id() {
        return bakery_id;
    }

    public void setBakery_id(int bakery_id) {
        this.bakery_id = bakery_id;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public Stock(int qte, int product_id, int bakery_id, int sales) {
        this.qte = qte;
        this.product_id = product_id;
        this.bakery_id = bakery_id;
        this.sales = sales;
    }
    
    
}
