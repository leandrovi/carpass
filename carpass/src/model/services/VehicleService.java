/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.services;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.VehicleDao;
import model.entities.Vehicle;

/**
 *
 * @author leandro
 */
public class VehicleService {
    
    private VehicleDao dao = DaoFactory.createVehicleDao();
    
    public List<Vehicle> findAll() {
        return dao.findAll();
    }
	
    public void saveOrUpdate(Vehicle obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        }
        else {
            dao.update(obj);
        }
    }

    public void remove(Vehicle obj) {
        dao.deleteById(obj.getId());
    }
    
}
