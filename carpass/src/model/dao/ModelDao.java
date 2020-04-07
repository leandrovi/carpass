/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.Model;

/**
 *
 * @author leandro
 */
public interface ModelDao {
    
    void insert(Model obj);
    void update(Model obj);
    void deleteById(Integer id);
    Model findById(Integer id);
    List<Model> findAll();
    
}
