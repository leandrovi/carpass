/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao.impl;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.dao.PlanDao;
import model.entities.Plan;

/**
 *
 * @author leandro
 */
public class PlanDaoJDBC implements PlanDao {
    
    private Connection conn;
    
    public PlanDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Plan findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT * " +
                "FROM plan " +
                "WHERE plan.id = ?"
            );
            
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Plan plan = new Plan();
                
                plan.setId(rs.getInt("id"));
                plan.setName(rs.getString("name"));
                plan.setValue(rs.getDouble("value"));

                return plan;
            }
            
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
          
    }

    @Override
    public List<Plan> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT * " +
                "FROM plan " +
                "ORDER BY name;"
            );
            
            rs = st.executeQuery();
            
            List<Plan> list = new ArrayList<>();

            while (rs.next()) {
                Plan plan = new Plan();
                
                plan.setId(rs.getInt("id"));
                plan.setName(rs.getString("name"));
                plan.setValue(rs.getDouble("value"));
                
                list.add(plan);
            }
            
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
}
