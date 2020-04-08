/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.Specialty;

/**
 *
 * @author leandro
 */
public interface SpecialtyDao {
    
    void insert(Specialty specialty);
    void update(Specialty specialty);
    void deleteById(Integer id);
    Specialty findById(Integer id);
    List<Specialty> findAll();
    
}
