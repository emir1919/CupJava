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
 * @author Emir
 */
public class Msg {
    private int id;
    private int emetteur_id;
    private int recepteur_id;
    private String subject;
    private String body;
    private String piece;
    private int lu;
    private String DateEnvoi;

    public Msg(int id, int emetteur_id, int recepteur_id, String subject, String body, String piece, int lu, String DateEnvoi) {
        this.id = id;
        this.emetteur_id = emetteur_id;
        this.recepteur_id = recepteur_id;
        this.subject = subject;
        this.body = body;
        this.piece = piece;
        this.lu = lu;
        this.DateEnvoi = DateEnvoi;
    }

    public Msg() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + this.emetteur_id;
        hash = 89 * hash + this.recepteur_id;
        hash = 89 * hash + Objects.hashCode(this.subject);
        hash = 89 * hash + Objects.hashCode(this.body);
        hash = 89 * hash + Objects.hashCode(this.piece);
        hash = 89 * hash + this.lu;
        hash = 89 * hash + Objects.hashCode(this.DateEnvoi);
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
        final Msg other = (Msg) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.emetteur_id != other.emetteur_id) {
            return false;
        }
        if (this.recepteur_id != other.recepteur_id) {
            return false;
        }
        if (this.lu != other.lu) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        if (!Objects.equals(this.body, other.body)) {
            return false;
        }
        if (!Objects.equals(this.piece, other.piece)) {
            return false;
        }
        if (!Objects.equals(this.DateEnvoi, other.DateEnvoi)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Msg{" + "id=" + id + ", emetteur_id=" + emetteur_id + ", recepteur_id=" + recepteur_id + ", subject=" + subject + ", body=" + body + ", piece=" + piece + ", lu=" + lu + ", DateEnvoi=" + DateEnvoi + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmetteur_id() {
        return emetteur_id;
    }

    public void setEmetteur_id(int emetteur_id) {
        this.emetteur_id = emetteur_id;
    }

    public int getRecepteur_id() {
        return recepteur_id;
    }

    public void setRecepteur_id(int recepteur_id) {
        this.recepteur_id = recepteur_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public int getLu() {
        return lu;
    }

    public void setLu(int lu) {
        this.lu = lu;
    }

    public String getDateEnvoi() {
        return DateEnvoi;
    }

    public void setDateEnvoi(String DateEnvoi) {
        this.DateEnvoi = DateEnvoi;
    }
    
}
