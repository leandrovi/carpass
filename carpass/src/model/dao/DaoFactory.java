/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import model.dao.impl.BrandDaoJDBC;
import model.dao.impl.ModelDaoJDBC;

/**
 *
 * @author leandro
 */
public class DaoFactory {
    
    public static BrandDao createBrandDao() {
        return new BrandDaoJDBC();
    }
    
    public static ModelDao createModelDao() {
        return new ModelDaoJDBC();
    }
    
}
