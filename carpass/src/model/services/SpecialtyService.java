/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.services;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.SpecialtyDao;
import model.entities.Specialty;

/**
 *
 * @author leandro
 */
public class SpecialtyService {
    
    private SpecialtyDao dao = DaoFactory.createSpecialtyDao();
    
    public List<Specialty> findAll() {
        return dao.findAll();
    }
    
}
