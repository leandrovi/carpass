/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import db.DB;
import model.dao.impl.AppointmentDaoJDBC;
import model.dao.impl.BrandDaoJDBC;
import model.dao.impl.ClientDaoJDBC;
import model.dao.impl.ModelDaoJDBC;
import model.dao.impl.PlanDaoJDBC;
import model.dao.impl.ProviderDaoJDBC;
import model.dao.impl.ProviderSpecialtyDaoJDBC;
import model.dao.impl.RatingDaoJDBC;
import model.dao.impl.SpecialtyDaoJDBC;
import model.dao.impl.VehicleDaoJDBC;

/**
 *
 * @author leandro
 */
public class DaoFactory {
    
    public static AppointmentDao createAppointmentDao() {
        return new AppointmentDaoJDBC(DB.getConnection());
    }
    
    public static BrandDao createBrandDao() {
        return new BrandDaoJDBC(DB.getConnection());
    }
    
    public static ClientDao createClientDao() {
        return new ClientDaoJDBC(DB.getConnection());
    }
    
    public static ModelDao createModelDao() {
        return new ModelDaoJDBC(DB.getConnection());
    }
    
    public static PlanDao createPlanDao() {
        return new PlanDaoJDBC(DB.getConnection());
    }
    
    public static ProviderDao createProviderDao() {
        return new ProviderDaoJDBC(DB.getConnection());
    }
    
    public static ProviderSpecialtyDao createProviderSpecialtyDao() {
        return new ProviderSpecialtyDaoJDBC(DB.getConnection());
    }
    
    public static RatingDao createRatingDao() {
        return new RatingDaoJDBC(DB.getConnection());
    }
    
    public static SpecialtyDao createSpecialtyDao() {
        return new SpecialtyDaoJDBC(DB.getConnection());
    }
    
    public static VehicleDao createVehicleDao() {
        return new VehicleDaoJDBC(DB.getConnection());
    }
    
}
