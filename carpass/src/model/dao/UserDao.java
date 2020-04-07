/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.User;

/**
 *
 * @author leandro
 */
public interface UserDao {
    
    void insert(User obj);
    void update(User obj);
    void deleteById(Integer id);
    User findById(Integer id);
    List<User> findAll();
    
}
