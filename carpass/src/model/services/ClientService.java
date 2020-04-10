/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.services;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.ClientDao;
import model.entities.Client;

/**
 *
 * @author leandro
 */
public class ClientService {
    
    private ClientDao dao = DaoFactory.createClientDao();
    
    public List<Client> findAll() {
        return dao.findAll();
    }
    
    public Client findById(Integer id) {
        return dao.findById(id);
    }
	
    public void saveOrUpdate(Client obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        }
        else {
            dao.update(obj);
        }
    }

    public void remove(Client obj) {
        dao.deleteById(obj.getId());
    }
    
}
