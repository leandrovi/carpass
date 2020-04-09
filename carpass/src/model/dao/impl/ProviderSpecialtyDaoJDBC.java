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
import model.dao.ProviderSpecialtyDao;
import model.entities.Plan;
import model.entities.Provider;
import model.entities.ProviderSpecialty;
import model.entities.Specialty;

/**
 *
 * @author leandro
 */
public class ProviderSpecialtyDaoJDBC implements ProviderSpecialtyDao {
    
    private Connection conn;
    
    public ProviderSpecialtyDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(ProviderSpecialty providerSpecialty) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "INSERT INTO provider_specialty(id_provider, id_specialty) " +
                "VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            st.setInt(1, providerSpecialty.getProvider().getId());
            st.setInt(2, providerSpecialty.getSpecialty().getId());

            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected!");
            }

            ResultSet rs = st.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);

                providerSpecialty.setId(id);
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
    public void update(ProviderSpecialty providerSpecialty) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "UPDATE provider_specialty " +
                "SET id_provider = ?, id_specialty = ? " +
                "WHERE id = ?;"
            );

            st.setInt(1, providerSpecialty.getProvider().getId());
            st.setInt(2, providerSpecialty.getSpecialty().getId());
            st.setInt(3, providerSpecialty.getId());
            
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
            st = conn.prepareStatement("DELETE FROM provider_specialty WHERE id = ?;");
            
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
    public ProviderSpecialty findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   provider_specialty.*, " +
                "   specialty.name as specialty, " +
                "   provider.name as provider_name, " +
                "   provider.email as provider_email, " +
                "   provider.password as provider_password, " +
                "   plan.id as plan_id, " +
                "   plan.name as plan_name, " +
                "   plan.value as plan_value " +
                "FROM provider_specialty " +
                "INNER JOIN provider " +
                "ON provider_specialty.id_provider = provider.id " +
                "INNER JOIN specialty " +
                "ON provider_specialty.id_specialty = specialty.id " +
                "INNER JOIN plan " +
                "ON provider.id_plan = plan.id " +
                "WHERE provider_specialty.id = ?"
            );
            
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Plan plan = instantiatePlan(rs);
                Provider provider = instantiateProvider(rs, plan);
                Specialty specialty = instantiateSpecialty(rs);
                
                ProviderSpecialty providerSpecialty = 
                    instantiateProviderSpecialty(rs, provider, specialty);

                return providerSpecialty;
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
    public List<ProviderSpecialty> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   provider_specialty.*, " +
                "   specialty.name as specialty, " +
                "   provider.name as provider_name, " +
                "   provider.email as provider_email, " +
                "   provider.password as provider_password, " +
                "   plan.id as plan_id, " +
                "   plan.name as plan_name, " +
                "   plan.value as plan_value " +
                "FROM provider_specialty " +
                "INNER JOIN provider " +
                "ON provider_specialty.id_provider = provider.id " +
                "INNER JOIN specialty " +
                "ON provider_specialty.id_specialty = specialty.id " +
                "INNER JOIN plan " +
                "ON provider.id_plan = plan.id " +
                "ORDER BY id"
            );
            
            rs = st.executeQuery();
            
            List<ProviderSpecialty> list = new ArrayList<>();
            
            Map<Integer, Plan> planMap = new HashMap<>();
            Map<Integer, Provider> providerMap = new HashMap<>();
            Map<Integer, Specialty> specialtyMap = new HashMap<>();
            
            while (rs.next()) {
                Plan planInstance = planMap.get(rs.getInt("plan_id"));
                Provider providerInstance = providerMap.get(rs.getInt("id_provider"));
                Specialty specialtyInstance = specialtyMap.get(rs.getInt("id_specialty"));
                
                if (planInstance == null) {
                    planInstance = instantiatePlan(rs);
                    planMap.put(rs.getInt("plan_id"), planInstance);
                } 
                
                if (providerInstance == null) {
                    providerInstance = instantiateProvider(rs, planInstance);
                    providerMap.put(rs.getInt("id_brand"), providerInstance);
                } 
                
                if (specialtyInstance == null) {
                    specialtyInstance = instantiateSpecialty(rs);
                    specialtyMap.put(rs.getInt("id_specialty"), specialtyInstance);
                } 
                
                ProviderSpecialty providerSpecialty = 
                    instantiateProviderSpecialty(
                        rs,
                        providerInstance,
                        specialtyInstance
                    );

                list.add(providerSpecialty);
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
        
        plan.setId(rs.getInt("plan_id"));
        plan.setName(rs.getString("plan_name"));
        plan.setValue(rs.getDouble("plan_value"));
        
        return plan;
    }
    
    private Provider instantiateProvider(ResultSet rs, Plan plan) throws SQLException {
        Provider provider = new Provider();
        
        provider.setId(rs.getInt("id_provider"));
        provider.setPlan(plan);
        provider.setName(rs.getString("provider_name"));
        provider.setEmail(rs.getString("provider_email"));
        provider.setPassword(rs.getString("provider_password"));
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
    
    private Specialty instantiateSpecialty(ResultSet rs) throws SQLException {
        Specialty specialty = new Specialty();
        
        specialty.setId(rs.getInt("id_specialty"));
        specialty.setName(rs.getString("specialty"));
        
        return specialty;
    }
    
    private ProviderSpecialty instantiateProviderSpecialty(
        ResultSet rs,
        Provider provider,
        Specialty specialty
    ) throws SQLException {
        ProviderSpecialty providerSpecialty = new ProviderSpecialty();
        
        providerSpecialty.setId(rs.getInt("id"));
        providerSpecialty.setProvider(provider);
        providerSpecialty.setSpecialty(specialty);
        
        return providerSpecialty;
    }
    
}
