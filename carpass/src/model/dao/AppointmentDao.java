/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.Appointment;

/**
 *
 * @author leandro
 */
public interface AppointmentDao {
    
    void insert(Appointment obj);
    void update(Appointment obj);
    void deleteById(Integer id);
    Appointment findById(Integer id);
    List<Appointment> findAll();
    
}
