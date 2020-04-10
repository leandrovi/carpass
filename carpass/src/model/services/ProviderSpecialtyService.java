/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.services;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.ProviderSpecialtyDao;
import model.entities.ProviderSpecialty;

/**
 *
 * @author leandro
 */
public class ProviderSpecialtyService {
    
    private ProviderSpecialtyDao dao = DaoFactory.createProviderSpecialtyDao();
    
    public List<ProviderSpecialty> findAll() {
        return dao.findAll();
    }
	
    public void saveOrUpdate(ProviderSpecialty obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        }
        else {
            dao.update(obj);
        }
    }

    public void remove(ProviderSpecialty obj) {
        dao.deleteById(obj.getId());
    }
    
}
