/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.Client;

/**
 *
 * @author leandro
 */
public interface ClientDao {
    
    void insert(Client obj);
    void update(Client obj);
    void deleteById(Integer id);
    Client findById(Integer id);
    List<Client> findAll();
    
}
