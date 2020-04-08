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
public class Provider extends User {
    
    private Plan plan;
    
    public Provider() {
        super();
    }

    public Provider(
        Integer id,
        String name,
        String email,
        String password,
        Plan plan
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.token = null;
        this.tokenUpdatedAt = null;
        this.plan = plan;
    }

    public Provider(
        Integer id,
        String name,
        String email,
        String password,
        String token,
        Date tokenUpdatedAt,
        Plan plan
    ) {
        super(id, name, email, password, token, tokenUpdatedAt);
        this.plan = plan;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "Provider{" + "id=" + id +
                ", name=" + name +
                ", email=" + email +
                ", password=" + password +
                ", token=" + token +
                ", tokenUpdatedAt=" + tokenUpdatedAt +
                ", plan=" + plan + '}';
    }
    
}
