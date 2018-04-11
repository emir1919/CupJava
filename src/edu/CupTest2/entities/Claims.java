/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.entities;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author El Kamel
 */
public class Claims {
    
    private Integer id;
 
    private Date date;
  
    private String description;
  
    private String name;
 
    private String email;
  
    private int phone;
   
    private int typeclaimId;
   
    private int enseigneId;
    
    private String EnsName;

    public Claims() {
    }

    public Claims(Integer id, Date date, String description, String name, String email, int phone, int typeclaimId, int enseigneId) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.typeclaimId = typeclaimId;
        this.enseigneId = enseigneId;
    }
    
    public Claims(Integer id, Date date, String description, String name, String email, int phone, int typeclaimId, int enseigneId,String ensName) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.typeclaimId = typeclaimId;
        this.enseigneId = enseigneId;
        this.EnsName = ensName;
    }
    
    public Claims(Date date, String description, String name, String email, int phone, int typeclaimId, int enseigneId) {
        
        this.date = date;
        this.description = description;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.typeclaimId = typeclaimId;
        this.enseigneId = enseigneId;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getTypeclaimId() {
        return typeclaimId;
    }

    public void setTypeclaimId(int typeclaimId) {
        this.typeclaimId = typeclaimId;
    }

    public int getEnseigneId() {
        return enseigneId;
    }

    public void setEnseigneId(int enseigneId) {
        this.enseigneId = enseigneId;
    }

    public String getEnsName() {
        return EnsName;
    }

    public void setEnsName(String EnsName) {
        this.EnsName = EnsName;
    }
    
    

    @Override
    public String toString() {
        return "Claims{" + "id=" + id + ", date=" + date + ", description=" + description + ", name=" + name + ", email=" + email + ", phone=" + phone + ", typeclaimId=" + typeclaimId + ", enseigneId=" + enseigneId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Claims other = (Claims) obj;
        if (this.phone != other.phone) {
            return false;
        }
        if (this.typeclaimId != other.typeclaimId) {
            return false;
        }
        if (this.enseigneId != other.enseigneId) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
}
