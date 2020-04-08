/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.Plan;

/**
 *
 * @author leandro
 */
public interface PlanDao {
    
    Plan findById(Integer id);
    List<Plan> findAll();
    
}
