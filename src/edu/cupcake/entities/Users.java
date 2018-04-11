/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author berrahal
 */
@Entity
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String email;
    private String password;
    private Date birthday;
    private String roles;
    private String firstname;
    private String lastname;
    private long phonenumber;
    private int enabled;
    private int CIN;
    private String image;
    private int confirmed;

    public int getCIN() {
        return CIN;
    }

    public void setCIN(int CIN) {
        this.CIN = CIN;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getPF() {
        return PF;
    }

    public void setPF(int PF) {
        this.PF = PF;
    }

    public int getReduction() {
        return Reduction;
    }

    public void setReduction(int Reduction) {
        this.Reduction = Reduction;
    }
    private int PF;
    private int Reduction;

    
    
     public Users(int id, String username, String email, String password, Date birthday, String roles, String firstname, String lastname, long phonenumber, int enabled) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.roles = roles;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.enabled = enabled;
    }
      public Users(int id, String FirstName, String LastName, String email, String password, int PhoneNumber, int CIN,String image) {
        this.id = id;
        this.firstname = FirstName;
        this.lastname = LastName;
        this.email = email;
        this.password = password;
        this.phonenumber = PhoneNumber;
        this.CIN = CIN;
        this.image = image;
    }

    public Users(int id, String FirstName, String LastName, String email, String password, int PhoneNumber, int CIN, String image,int confirmed) {
        this.id = id;
        this.firstname = FirstName;
        this.lastname = LastName;
        this.email = email;
        this.password = password;
        this.phonenumber = PhoneNumber;
        this.CIN = CIN;
        this.image = image;
        this.confirmed = confirmed;
    }
    
    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(long phonenumber) {
        this.phonenumber = phonenumber;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
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
        final Users other = (Users) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return username;
    }

   

    

    public Users(String username, String email, String password, Date birthday, String roles, String firstname, String lastname, long phonenumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.roles = roles;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
    }
    public Users(){};

  
    
}
