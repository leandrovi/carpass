/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.Appointment;
import model.entities.Provider;
import model.entities.Specialty;
import model.entities.Vehicle;

/**
 *
 * @author leandro
 */
public interface AppointmentDao {
    
    void insert(Appointment appointment);
    void update(Appointment appointment);
    void deleteById(Integer id);
    Appointment findById(Integer id);
    List<Appointment> findByProvider(Provider provider);
    List<Appointment> findByVehicle(Vehicle vehicle);
    List<Appointment> findBySpecialty(Specialty specialty);
    List<Appointment> findAll();
    
}
