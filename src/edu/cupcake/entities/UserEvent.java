/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.entities;

/**
 *
 * @author El Kamel
 */
public class UserEvent {
    
    private int id;
   
    private boolean confirmed;
    
    private int scanned;
 
    private int eventId;
  
    private int userId;

    public UserEvent() {
        this.id =0;
        this.confirmed = false;
        this.scanned =0;
        this.userId = 0;
    }

    public UserEvent(int id, boolean confirmed, int scanned, int eventId, int userId) {
        this.id = id;
        this.confirmed = confirmed;
        this.scanned = scanned;
        this.eventId = eventId;
        this.userId = userId;
    }
    
    public UserEvent(boolean confirmed, int scanned, int eventId, int userId) {
        
        this.confirmed = confirmed;
        this.scanned = scanned;
        this.eventId = eventId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public int getScanned() {
        return scanned;
    }

    public void setScanned(int scanned) {
        this.scanned = scanned;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public boolean getConfirmed() {
        return confirmed;
    }


    
    
    

    @Override
    public String toString() {
        return "UserEvent{" + "id=" + id + ", confirmed=" + confirmed + ", scanned=" + scanned + ", eventId=" + eventId + ", userId=" + userId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final UserEvent other = (UserEvent) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.confirmed != other.confirmed) {
            return false;
        }
        if (this.scanned != other.scanned) {
            return false;
        }
        if (this.eventId != other.eventId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
    
}
