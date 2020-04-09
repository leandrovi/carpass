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
import model.dao.RatingDao;
import model.entities.Client;
import model.entities.Plan;
import model.entities.Provider;
import model.entities.Rating;

/**
 *
 * @author leandro
 */
public class RatingDaoJDBC implements RatingDao {
    
    private Connection conn;
    
    public RatingDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Rating rating) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "INSERT INTO rating " +
                "(id_provider, id_client, score, comment, created_at) " +
                "VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            st.setInt(1, rating.getProvider().getId());
            st.setInt(2, rating.getClient().getId());
            st.setInt(3, rating.getScore());
            st.setString(4, rating.getComment());
            st.setDate(5, new java.sql.Date(rating.getCreatedAt().getTime()));

            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected!");
            }

            ResultSet rs = st.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);

                rating.setId(id);
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
    public void update(Rating rating) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "UPDATE rating " +
                "SET "+
                "   id_provider = ?, " +
                "   id_client = ?, " +
                "   score = ?, " +
                "   comment = ?, " +
                "   created_at = ? " +
                "WHERE id = ?;"
            );

            st.setInt(1, rating.getProvider().getId());
            st.setInt(2, rating.getClient().getId());
            st.setInt(3, rating.getScore());
            st.setString(4, rating.getComment());
            st.setDate(5, new java.sql.Date(rating.getCreatedAt().getTime()));
            st.setInt(6, rating.getId());
            
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
            st = conn.prepareStatement("DELETE FROM rating WHERE id = ?;");
            
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
    public Rating findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   rating.*, " +
                "   provider.name as provider_name, " +
                "   provider.email as provider_email, " +
                "   provider.password as provider_password, " +
                "   plan.id as plan_id, " +
                "   plan.name as plan_name, " +
                "   plan.value as plan_value, " +
                "   client.name as client_name, " +
                "   client.email as client_email, " +
                "   client.password as client_password " +
                "FROM rating " +
                "INNER JOIN provider " +
                "ON rating.id_provider = provider.id " +
                "INNER JOIN client " +
                "ON rating.id_client = client.id " +
                "INNER JOIN plan " +
                "ON provider.id_plan = plan.id " +
                "WHERE rating.id = ?"
            );
            
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Plan plan = instantiatePlan(rs);
                Provider provider = instantiateProvider(rs, plan);
                Client client = instantiateClient(rs);
                Rating rating = instantiateRating(rs, provider, client);

                return rating;
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
    public List<Rating> findByProvider(Provider provider) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   rating.*, " +
                "   provider.name as provider_name, " +
                "   provider.email as provider_email, " +
                "   provider.password as provider_password, " +
                "   plan.id as plan_id, " +
                "   plan.name as plan_name, " +
                "   plan.value as plan_value, " +
                "   client.name as client_name, " +
                "   client.email as client_email, " +
                "   client.password as client_password " +
                "FROM rating " +
                "INNER JOIN provider " +
                "ON rating.id_provider = provider.id " +
                "INNER JOIN client " +
                "ON rating.id_client = client.id " +
                "INNER JOIN plan " +
                "ON provider.id_plan = plan.id " +
                "WHERE rating.id_provider = ? " +
                "ORDER BY created_at DESC"
            );
            
            st.setInt(1, provider.getId());
            
            rs = st.executeQuery();
            
            List<Rating> list = new ArrayList<>();
            
            Map<Integer, Plan> planMap = new HashMap<>();
            Map<Integer, Provider> providerMap = new HashMap<>();
            Map<Integer, Client> clientMap = new HashMap<>();
            
            while (rs.next()) {
                Plan planInstance = planMap.get(rs.getInt("plan_id"));
                Provider providerInstance = providerMap.get(rs.getInt("id_provider"));
                Client clientInstance = clientMap.get(rs.getInt("id_client"));
                
                if (planInstance == null) {
                    planInstance = instantiatePlan(rs);
                    planMap.put(rs.getInt("id_brand"), planInstance);
                } 
                
                if (providerInstance == null) {
                    providerInstance = instantiateProvider(rs, planInstance);
                    providerMap.put(rs.getInt("id_brand"), providerInstance);
                } 
                
                if (clientInstance == null) {
                    clientInstance = instantiateClient(rs);
                    clientMap.put(rs.getInt("id_client"), clientInstance);
                } 
                
                Rating rating = instantiateRating(
                    rs,
                    providerInstance,
                    clientInstance
                );

                list.add(rating);
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

    @Override
    public List<Rating> findByClient(Client client) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   rating.*, " +
                "   provider.name as provider_name, " +
                "   provider.email as provider_email, " +
                "   provider.password as provider_password, " +
                "   plan.id as plan_id, " +
                "   plan.name as plan_name, " +
                "   plan.value as plan_value, " +
                "   client.name as client_name, " +
                "   client.email as client_email, " +
                "   client.password as client_password " +
                "FROM rating " +
                "INNER JOIN provider " +
                "ON rating.id_provider = provider.id " +
                "INNER JOIN client " +
                "ON rating.id_client = client.id " +
                "INNER JOIN plan " +
                "ON provider.id_plan = plan.id " +
                "WHERE rating.id_client = ? " +
                "ORDER BY created_at DESC"
            );
            
            st.setInt(1, client.getId());
            
            rs = st.executeQuery();
            
            List<Rating> list = new ArrayList<>();
            
            Map<Integer, Plan> planMap = new HashMap<>();
            Map<Integer, Provider> providerMap = new HashMap<>();
            Map<Integer, Client> clientMap = new HashMap<>();
            
            while (rs.next()) {
                Plan planInstance = planMap.get(rs.getInt("plan_id"));
                Provider providerInstance = providerMap.get(rs.getInt("id_provider"));
                Client clientInstance = clientMap.get(rs.getInt("id_client"));
                
                if (planInstance == null) {
                    planInstance = instantiatePlan(rs);
                    planMap.put(rs.getInt("id_brand"), planInstance);
                } 
                
                if (providerInstance == null) {
                    providerInstance = instantiateProvider(rs, planInstance);
                    providerMap.put(rs.getInt("id_brand"), providerInstance);
                } 
                
                if (clientInstance == null) {
                    clientInstance = instantiateClient(rs);
                    clientMap.put(rs.getInt("id_client"), clientInstance);
                } 
                
                Rating rating = instantiateRating(
                    rs,
                    providerInstance,
                    clientInstance
                );

                list.add(rating);
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

    @Override
    public List<Rating> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   rating.*, " +
                "   provider.name as provider_name, " +
                "   provider.email as provider_email, " +
                "   provider.password as provider_password, " +
                "   plan.id as plan_id, " +
                "   plan.name as plan_name, " +
                "   plan.value as plan_value, " +
                "   client.name as client_name, " +
                "   client.email as client_email, " +
                "   client.password as client_password " +
                "FROM rating " +
                "INNER JOIN provider " +
                "ON rating.id_provider = provider.id " +
                "INNER JOIN client " +
                "ON rating.id_client = client.id " +
                "INNER JOIN plan " +
                "ON provider.id_plan = plan.id " +
                "ORDER BY created_at DESC"
            );
            
            rs = st.executeQuery();
            
            List<Rating> list = new ArrayList<>();
            
            Map<Integer, Plan> planMap = new HashMap<>();
            Map<Integer, Provider> providerMap = new HashMap<>();
            Map<Integer, Client> clientMap = new HashMap<>();
            
            while (rs.next()) {
                Plan planInstance = planMap.get(rs.getInt("plan_id"));
                Provider providerInstance = providerMap.get(rs.getInt("id_provider"));
                Client clientInstance = clientMap.get(rs.getInt("id_client"));
                
                if (planInstance == null) {
                    planInstance = instantiatePlan(rs);
                    planMap.put(rs.getInt("plan_id"), planInstance);
                } 
                
                if (providerInstance == null) {
                    providerInstance = instantiateProvider(rs, planInstance);
                    providerMap.put(rs.getInt("id_brand"), providerInstance);
                } 
                
                if (clientInstance == null) {
                    clientInstance = instantiateClient(rs);
                    clientMap.put(rs.getInt("id_client"), clientInstance);
                } 
                
                Rating rating = instantiateRating(
                    rs,
                    providerInstance,
                    clientInstance
                );

                list.add(rating);
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
    
    private Client instantiateClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        
        client.setId(rs.getInt("id_client"));
        client.setName(rs.getString("client_name"));
        client.setEmail(rs.getString("client_email"));
        client.setPassword(rs.getString("client_password"));
        //client.setToken(rs.getString("token"));
        //client.setTokenUpdatedAt(
        //    new java.util.Date(rs.getTimestamp("token_updated_at").getTime())
        //);
        client.setToken(null);
        client.setTokenUpdatedAt(null);
        
        return client;
    }
    
    private Rating instantiateRating(
        ResultSet rs,
        Provider provider,
        Client client
    ) throws SQLException {
        Rating rating = new Rating();
        
        rating.setId(rs.getInt("id"));
        rating.setProvider(provider);
        rating.setClient(client);
        rating.setScore(rs.getInt("score"));
        rating.setComment(rs.getString("comment"));
        rating.setCreatedAt(
            new java.util.Date(rs.getTimestamp("created_at").getTime())
        );
        
        return rating;
    }
    
}
