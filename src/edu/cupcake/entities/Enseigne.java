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
public class Enseigne {
    
    private int id;
    private int user_id;
    private String Name;
    private String Address;
    private int PhoneNumber;
    private int Fax;
    private String Email;
    private String Website;
    private String Logo;
    private String CodeRC;
    private String Description;
    private int nbClaims;

    public Enseigne() {
    }
    
    
    public Enseigne(int id, int user_id, String Name, String Address, int PhoneNumber, int Fax, String Email, String Website, String Logo, String CodeRC, String Description, int nbClaims) {
        this.id = id;
        this.user_id = user_id;
        this.Name = Name;
        this.Address = Address;
        this.PhoneNumber = PhoneNumber;
        this.Fax = Fax;
        this.Email = Email;
        this.Website = Website;
        this.Logo = Logo;
        this.CodeRC = CodeRC;
        this.Description = Description;
        this.nbClaims = nbClaims;
    }

    public Enseigne(int user_id, String Name, String Address, int PhoneNumber, int Fax, String Email, String Website, String Logo, String CodeRC, String Description, int nbClaims) {
        this.user_id = user_id;
        this.Name = Name;
        this.Address = Address;
        this.PhoneNumber = PhoneNumber;
        this.Fax = Fax;
        this.Email = Email;
        this.Website = Website;
        this.Logo = Logo;
        this.CodeRC = CodeRC;
        this.Description = Description;
        this.nbClaims = nbClaims;
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

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public int getFax() {
        return Fax;
    }

    public void setFax(int Fax) {
        this.Fax = Fax;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String Website) {
        this.Website = Website;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String Logo) {
        this.Logo = Logo;
    }

    public String getCodeRC() {
        return CodeRC;
    }

    public void setCodeRC(String CodeRC) {
        this.CodeRC = CodeRC;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getNbClaims() {
        return nbClaims;
    }

    public void setNbClaims(int nbClaims) {
        this.nbClaims = nbClaims;
    }

    @Override
    public String toString() {
        return "Enseigne{" + "id=" + id + ", user_id=" + user_id + ", Name=" + Name + ", Address=" + Address + ", PhoneNumber=" + PhoneNumber + ", Fax=" + Fax + ", Email=" + Email + ", Website=" + Website + ", Logo=" + Logo + ", CodeRC=" + CodeRC + ", Description=" + Description + ", nbClaims=" + nbClaims + '}';
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
        final Enseigne other = (Enseigne) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.user_id != other.user_id) {
            return false;
        }
        if (this.PhoneNumber != other.PhoneNumber) {
            return false;
        }
        if (this.Fax != other.Fax) {
            return false;
        }
        if (this.nbClaims != other.nbClaims) {
            return false;
        }
        if (!Objects.equals(this.Name, other.Name)) {
            return false;
        }
        if (!Objects.equals(this.Address, other.Address)) {
            return false;
        }
        if (!Objects.equals(this.Email, other.Email)) {
            return false;
        }
        if (!Objects.equals(this.Website, other.Website)) {
            return false;
        }
        if (!Objects.equals(this.Logo, other.Logo)) {
            return false;
        }
        if (!Objects.equals(this.CodeRC, other.CodeRC)) {
            return false;
        }
        if (!Objects.equals(this.Description, other.Description)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
    
    
}
