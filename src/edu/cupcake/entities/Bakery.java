/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.entities;

import java.util.Objects;

/**
 *
 * @author Emir
 */
public class Bakery {
         private int id;
         private String name;
         private String address;
         private String PhoneNumber;
         private String Fax;
         private String Email;
         private String image;
         private double longitude;
         private double latitude;
         private int id_enseigne;
         private int id_user;

    @Override
    public String toString() {
        return "Bakery{" + "id=" + id + ", name=" + name + ", address=" + address + ", PhoneNumber=" + PhoneNumber + ", Fax=" + Fax + ", Email=" + Email + ", image=" + image + ", longitude=" + longitude + ", latitude=" + latitude + ", id_enseigne=" + id_enseigne + ", id_user=" + id_user + '}';
    }

    public Bakery(String name, String address, String PhoneNumber, String Fax, String Email, String image, double longitude, double latitude, int id_enseigne, int id_user) {
        this.name = name;
        this.address = address;
        this.PhoneNumber = PhoneNumber;
        this.Fax = Fax;
        this.Email = Email;
        this.image = image;
        this.longitude = longitude;
        this.latitude = latitude;
        this.id_enseigne = id_enseigne;
        this.id_user = id_user;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.address);
        hash = 89 * hash + Objects.hashCode(this.PhoneNumber);
        hash = 89 * hash + Objects.hashCode(this.Fax);
        hash = 89 * hash + Objects.hashCode(this.Email);
        hash = 89 * hash + Objects.hashCode(this.image);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 89 * hash + this.id_enseigne;
        hash = 89 * hash + this.id_user;
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
        final Bakery other = (Bakery) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (this.id_enseigne != other.id_enseigne) {
            return false;
        }
        if (this.id_user != other.id_user) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.PhoneNumber, other.PhoneNumber)) {
            return false;
        }
        if (!Objects.equals(this.Fax, other.Fax)) {
            return false;
        }
        if (!Objects.equals(this.Email, other.Email)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        return true;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
        
    

   

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
    

   

    public Bakery() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String Fax) {
        this.Fax = Fax;
    }

    public int getId_enseigne() {
        return id_enseigne;
    }

    public void setId_enseigne(int id_enseigne) {
        this.id_enseigne = id_enseigne;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
    
}
