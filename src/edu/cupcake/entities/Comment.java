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
public class Comment {
    private int id;
    private String content;
    private String DateComment;
    private int user_id;
    private int enseigne_id;

    public Comment() {
    }

    public Comment(int id, String content, String DateComment, int user_id, int enseigne_id) {
        this.id = id;
        this.content = content;
        this.DateComment = DateComment;
        this.user_id = user_id;
        this.enseigne_id = enseigne_id;
    }

    public Comment(String content, String DateComment, int user_id, int enseigne_id) {
        this.content = content;
        this.DateComment = DateComment;
        this.user_id = user_id;
        this.enseigne_id = enseigne_id;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateComment() {
        return DateComment;
    }

    public void setDateComment(String DateComment) {
        this.DateComment = DateComment;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEnseigne_id() {
        return enseigne_id;
    }

    public void setEnseigne_id(int enseigne_id) {
        this.enseigne_id = enseigne_id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.id;
        hash = 19 * hash + Objects.hashCode(this.content);
        hash = 19 * hash + Objects.hashCode(this.DateComment);
        hash = 19 * hash + this.user_id;
        hash = 19 * hash + this.enseigne_id;
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
        final Comment other = (Comment) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.user_id != other.user_id) {
            return false;
        }
        if (this.enseigne_id != other.enseigne_id) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        if (!Objects.equals(this.DateComment, other.DateComment)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", content=" + content + ", DateComment=" + DateComment + ", user_id=" + user_id + ", enseigne_id=" + enseigne_id + '}';
    }
    
}
