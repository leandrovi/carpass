/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author leandro
 */
public class Rating {
    
    private Integer id;
    private Integer score;
    private String comment;
    private Date createdAt;
    
    private Provider provider;
    private Client client;
    
    public Rating() {}

    public Rating(Integer id, Integer score, String comment, Date createdAt, Provider provider, Client client) {
        this.id = id;
        this.score = score;
        this.comment = comment;
        this.createdAt = createdAt;
        this.provider = provider;
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final Rating other = (Rating) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rating{" + "id=" + id + ", score=" + score + ", comment=" + comment + ", createdAt=" + createdAt + ", provider=" + provider + ", client=" + client + '}';
    }
    
}
