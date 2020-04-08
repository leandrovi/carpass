/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import db.DB;
import model.dao.impl.BrandDaoJDBC;
import model.dao.impl.ClientDaoJDBC;
import model.dao.impl.ModelDaoJDBC;

/**
 *
 * @author leandro
 */
public class DaoFactory {
    
    public static BrandDao createBrandDao() {
        return new BrandDaoJDBC(DB.getConnection());
    }
    
    public static ClientDao createClientDao() {
        return new ClientDaoJDBC(DB.getConnection());
    }
    
    public static ModelDao createModelDao() {
        return new ModelDaoJDBC(DB.getConnection());
    }
    
}
