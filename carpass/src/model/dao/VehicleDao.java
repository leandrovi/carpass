/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.Vehicle;

/**
 *
 * @author leandro
 */
public interface VehicleDao {
    
    void insert(Vehicle obj);
    void update(Vehicle obj);
    void deleteById(Integer id);
    Vehicle findById(Integer id);
    List<Vehicle> findAll();
    
}
