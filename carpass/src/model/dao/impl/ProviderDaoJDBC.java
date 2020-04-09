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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.dao.ProviderDao;
import model.entities.Plan;
import model.entities.Provider;

/**
 *
 * @author leandro
 */
public class ProviderDaoJDBC implements ProviderDao {
    
    private Connection conn;
    
    public ProviderDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Provider provider) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "INSERT INTO provider(id_plan, name, email, password) " +
                "VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            st.setInt(1, provider.getPlan().getId());
            st.setString(2, provider.getName());
            st.setString(3, provider.getEmail());
            st.setString(4, provider.getPassword());

            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected!");
            }

            ResultSet rs = st.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);

                provider.setId(id);
            }

            DB.closeResultSet(rs);
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Provider provider) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "UPDATE provider " +
                "SET id_plan = ?, name = ?, email = ?, password = ? " +
                "WHERE id = ?;"
            );

            st.setInt(1, provider.getPlan().getId());
            st.setString(2, provider.getName());
            st.setString(3, provider.getEmail());
            st.setString(4, provider.getPassword());
            st.setInt(5, provider.getId());
            
            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement("DELETE FROM provider WHERE id = ?;");
            
            st.setInt(1, id);
            
            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Provider findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   provider.*, " +
                "   plan.name as plan_name, " +
                "   plan.value as plan_value " +
                "FROM provider " +
                "INNER JOIN plan " +
                "ON provider.id_plan = plan.id " +
                "WHERE provider.id = ?"
            );
            
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Plan plan = instantiatePlan(rs);         
                Provider provider = instantiateProvider(rs, plan); 
                
                return provider;
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
    public List<Provider> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   provider.*, " +
                "   plan.name as plan_name, " +
                "   plan.value as plan_value " +
                "FROM provider " +
                "INNER JOIN plan " +
                "ON provider.id_plan = plan.id " +
                "ORDER BY name"
            );
            
            rs = st.executeQuery();
            
            List<Provider> list = new ArrayList<>();
            
            Map<Integer, Plan> map = new HashMap<>();
            
            while (rs.next()) {
                Plan planInstance = map.get(rs.getInt("id_plan"));
                
                if (planInstance == null) {
                    planInstance = instantiatePlan(rs);
                    map.put(rs.getInt("id_plan"), planInstance);
                }               
                               
                Provider provider = instantiateProvider(rs, planInstance);
                
                list.add(provider);
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
    
    private Plan instantiatePlan(ResultSet rs) throws SQLException {
        Plan plan = new Plan();
        
        plan.setId(rs.getInt("id_plan"));
        plan.setName(rs.getString("plan_name"));
        plan.setValue(rs.getDouble("plan_value"));
        
        return plan;
    }
    
    private Provider instantiateProvider(ResultSet rs, Plan plan) throws SQLException {
        Provider provider = new Provider();
        
        provider.setId(rs.getInt("id"));
        provider.setPlan(plan);
        provider.setName(rs.getString("name"));
        provider.setEmail(rs.getString("email"));
        provider.setPassword(rs.getString("password"));
        //provider.setToken(rs.getString("token"));
        //provider.setTokenUpdatedAt(
        //    new java.util.Date(
        //        rs.getTimestamp("token_updated_at").getTime()
        //    )
        //);
        provider.setToken(null);
        provider.setTokenUpdatedAt(null);
        
        return provider;
    }
    
}
