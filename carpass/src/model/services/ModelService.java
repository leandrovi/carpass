/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.services;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.ModelDao;
import model.entities.Model;

/**
 *
 * @author leandro
 */
public class ModelService {
    
    private ModelDao dao = DaoFactory.createModelDao();
    
    public List<Model> findAll() {
        return dao.findAll();
    }
    
}
