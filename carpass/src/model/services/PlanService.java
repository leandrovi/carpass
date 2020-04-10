/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.services;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.PlanDao;
import model.entities.Plan;

/**
 *
 * @author leandro
 */
public class PlanService {
    
    private PlanDao dao = DaoFactory.createPlanDao();
    
    public List<Plan> findAll() {
        return dao.findAll();
    }
    
}
