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
public class TypeClaim {
    
    private int id;
  
    private String name;
   
    private int nbClaims;

    public TypeClaim() {
    }

    public TypeClaim(int id, String name, int nbClaims) {
        this.id = id;
        this.name = name;
        this.nbClaims = nbClaims;
    }
    
    public TypeClaim(String name, int nbClaims) {
        this.name = name;
        this.nbClaims = nbClaims;
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

    public int getNbClaims() {
        return nbClaims;
    }

    public void setNbClaims(int nbClaims) {
        this.nbClaims = nbClaims;
    }

    @Override
    public String toString() {
        return "TypeClaim{" + "id=" + id + ", name=" + name + ", nbClaims=" + nbClaims + '}';
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
        final TypeClaim other = (TypeClaim) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.nbClaims != other.nbClaims) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}
