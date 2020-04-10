/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.services;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.AppointmentDao;
import model.entities.Appointment;

/**
 *
 * @author leandro
 */
public class AppointmentService {
    
    private AppointmentDao dao = DaoFactory.createAppointmentDao();
    
    public List<Appointment> findAll() {
        return dao.findAll();
    }
	
    public void saveOrUpdate(Appointment obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        }
        else {
            dao.update(obj);
        }
    }

    public void remove(Appointment obj) {
        dao.deleteById(obj.getId());
    }
    
}
