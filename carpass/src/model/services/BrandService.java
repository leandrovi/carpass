/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.services;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.BrandDao;
import model.entities.Brand;

/**
 *
 * @author leandro
 */
public class BrandService {
    
    private BrandDao dao = DaoFactory.createBrandDao();
    
    public List<Brand> findAll() {
        return dao.findAll();
    }
    
}
