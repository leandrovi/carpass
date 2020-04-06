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
public class User {
    
    protected Integer id;
    protected String name;
    protected String email;
    protected String password;
    protected String token;
    protected Date tokenUpdatedAt;
    
    public User() {}

    public User(Integer id, String name, String email, String password, String token, Date tokenUpdatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.token = token;
        this.tokenUpdatedAt = tokenUpdatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenUpdatedAt() {
        return tokenUpdatedAt;
    }

    public void setTokenUpdatedAt(Date tokenUpdatedAt) {
        this.tokenUpdatedAt = tokenUpdatedAt;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final User other = (User) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id +
                ", name=" + name +
                ", email=" + email +
                ", password=" + password +
                ", token=" + token +
                ", tokenUpdatedAt=" + tokenUpdatedAt + '}';
    }
    
}
