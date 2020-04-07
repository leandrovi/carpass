/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.Brand;

/**
 *
 * @author leandro
 */
public interface BrandDao {
    
    Brand findById(Integer id);
    List<Brand> findAll();
    
}
