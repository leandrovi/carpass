/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.Brand;
import model.entities.Model;

/**
 *
 * @author leandro
 */
public interface ModelDao {
    
    Model findById(Integer id);
    List<Model> findAll();
    List<Model> findByBrand(Brand brand);
    
}
