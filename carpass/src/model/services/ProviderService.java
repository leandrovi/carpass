/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.services;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.ProviderDao;
import model.entities.Provider;

/**
 *
 * @author leandro
 */
public class ProviderService {
    
    private ProviderDao dao = DaoFactory.createProviderDao();
    
    public List<Provider> findAll() {
        return dao.findAll();
    }
    
    public Provider findById(Integer id) {
        return dao.findById(id);
    }
	
    public void saveOrUpdate(Provider obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        }
        else {
            dao.update(obj);
        }
    }

    public void remove(Provider obj) {
        dao.deleteById(obj.getId());
    }
    
}
