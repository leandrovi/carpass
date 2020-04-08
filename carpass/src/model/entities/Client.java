/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.util.Date;

/**
 *
 * @author leandro
 */
public class Client extends User {
    
    public Client() {
        super();
    }

    public Client(Integer id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.token = null;
        this.tokenUpdatedAt = null;
    }

    public Client(
        Integer id,
        String name,
        String email,
        String password,
        String token,
        Date tokenUpdatedAt
    ) {
        super(id, name, email, password, token, tokenUpdatedAt);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id +
                ", name=" + name +
                ", email=" + email +
                ", password=" + password +
                ", token=" + token +
                ", tokenUpdatedAt=" + tokenUpdatedAt + '}';
    }
    
}
