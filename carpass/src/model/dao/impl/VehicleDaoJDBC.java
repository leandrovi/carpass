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
import model.dao.VehicleDao;
import model.entities.Brand;
import model.entities.Client;
import model.entities.Model;
import model.entities.Vehicle;

/**
 *
 * @author leandro
 */
public class VehicleDaoJDBC implements VehicleDao {
    
    private Connection conn;
    
    public VehicleDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Vehicle vehicle) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "INSERT INTO vehicle " +
                "(plate, id_client, id_brand, id_model, year, renavam, color) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, vehicle.getPlate());
            st.setInt(2, vehicle.getClient().getId());
            st.setInt(3, vehicle.getBrand().getId());
            st.setInt(4, vehicle.getModel().getId());
            st.setInt(5, vehicle.getYear());
            st.setString(6, vehicle.getRenavam());
            st.setString(7, vehicle.getColor());

            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected!");
            }

            ResultSet rs = st.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);

                vehicle.setId(id);
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
    public void update(Vehicle vehicle) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "UPDATE vehicle " +
                "SET "+
                "   plate = ?, " +
                "   id_client = ?, " +
                "   id_brand = ?, " +
                "   id_model = ?, " +
                "   year = ?, " +
                "   renavam = ?, " +
                "   color = ?, " +
                "WHERE id = ?;"
            );

            st.setString(1, vehicle.getPlate());
            st.setInt(2, vehicle.getClient().getId());
            st.setInt(3, vehicle.getBrand().getId());
            st.setInt(4, vehicle.getModel().getId());
            st.setInt(5, vehicle.getYear());
            st.setString(6, vehicle.getRenavam());
            st.setString(7, vehicle.getColor());
            st.setInt(8, vehicle.getId());
            
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
            st = conn.prepareStatement("DELETE FROM vehicle WHERE id = ?;");
            
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
    public Vehicle findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   vehicle.*, " +
                "   client.name as client, " +
                "   brand.name as brand, " +
                "   model.name as model " +
                "FROM vehicle " +
                "INNER JOIN client " +
                "ON vehicle.id_client = client.id " +
                "INNER JOIN brand " +
                "ON vehicle.id_brand = brand.id " +
                "INNER JOIN model " +
                "ON vehicle.id_model = model.id " +
                "WHERE vehicle.id = ?"
            );
            
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Client client = instantiateClient(rs);
                Brand brand = instantiateBrand(rs);                
                Model model = instantiateModel(rs, brand);
                Vehicle vehicle = instantiateVehicle(rs, client, brand, model);

                return vehicle;
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
    public Vehicle findByPlate(String plate) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   vehicle.*, " +
                "   client.name as client, " +
                "   brand.name as brand, " +
                "   model.name as model " +
                "FROM vehicle " +
                "INNER JOIN client " +
                "ON vehicle.id_client = client.id " +
                "INNER JOIN brand " +
                "ON vehicle.id_brand = brand.id " +
                "INNER JOIN model " +
                "ON vehicle.id_model = model.id " +
                "WHERE vehicle.plate = ?"
            );
            
            st.setString(1, plate);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Client client = instantiateClient(rs);
                Brand brand = instantiateBrand(rs);                
                Model model = instantiateModel(rs, brand);
                Vehicle vehicle = instantiateVehicle(rs, client, brand, model);

                return vehicle;
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
    public Vehicle findByRenavam(String renavam) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   vehicle.*, " +
                "   client.name as client, " +
                "   brand.name as brand, " +
                "   model.name as model " +
                "FROM vehicle " +
                "INNER JOIN client " +
                "ON vehicle.id_client = client.id " +
                "INNER JOIN brand " +
                "ON vehicle.id_brand = brand.id " +
                "INNER JOIN model " +
                "ON vehicle.id_model = model.id " +
                "WHERE vehicle.renavam = ?"
            );
            
            st.setString(1, renavam);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Client client = instantiateClient(rs);
                Brand brand = instantiateBrand(rs);                
                Model model = instantiateModel(rs, brand);
                Vehicle vehicle = instantiateVehicle(rs, client, brand, model);

                return vehicle;
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
    public List<Vehicle> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   vehicle.*, " +
                "   client.name as client, " +
                "   brand.name as brand, " +
                "   model.name as model " +
                "FROM vehicle " +
                "INNER JOIN client " +
                "ON vehicle.id_client = client.id " +
                "INNER JOIN brand " +
                "ON vehicle.id_brand = brand.id " +
                "INNER JOIN model " +
                "ON vehicle.id_model = model.id " +
                "ORDER BY model, plate"
            );
            
            rs = st.executeQuery();
            
            List<Vehicle> list = new ArrayList<>();
            
            Map<Integer, Client> clientMap = new HashMap<>();
            Map<Integer, Brand> brandMap = new HashMap<>();
            Map<Integer, Model> modelMap = new HashMap<>();
            
            while (rs.next()) {
                Client clientInstance = clientMap.get(rs.getInt("id_client"));
                Brand brandInstance = brandMap.get(rs.getInt("id_brand"));
                Model modelInstance = modelMap.get(rs.getInt("id_model"));
                
                if (clientInstance == null) {
                    clientInstance = instantiateClient(rs);
                    clientMap.put(rs.getInt("id_client"), clientInstance);
                } 
                
                if (brandInstance == null) {
                    brandInstance = instantiateBrand(rs);
                    brandMap.put(rs.getInt("id_brand"), brandInstance);
                } 
                
                if (modelInstance == null) {
                    modelInstance = instantiateModel(rs, brandInstance);
                    modelMap.put(rs.getInt("id_brand"), modelInstance);
                }                
                               
                Vehicle vehicle = instantiateVehicle(
                    rs,
                    clientInstance,
                    brandInstance,
                    modelInstance
                );
                
                list.add(vehicle);
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

    private Brand instantiateBrand(ResultSet rs) throws SQLException {
        Brand brand = new Brand();

        brand.setId(rs.getInt("id_brand"));
        brand.setName(rs.getString("brand"));
        
        return brand;
    }

    private Model instantiateModel(ResultSet rs, Brand brand) throws SQLException {
        Model model = new Model();

        model.setId(rs.getInt("id"));
        model.setName(rs.getString("name"));
        model.setBrand(brand);
        
        return model;
    }
    
    private Client instantiateClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        
        client.setId(rs.getInt("id"));
        client.setName(rs.getString("name"));
        client.setEmail(rs.getString("email"));
        client.setPassword(rs.getString("password"));
        client.setToken(rs.getString("token"));
        client.setTokenUpdatedAt(
            new java.util.Date(rs.getTimestamp("token_updated_at").getTime())
        );
        
        return client;
    }
    
    private Vehicle instantiateVehicle(
        ResultSet rs,
        Client client,
        Brand brand,
        Model model
    ) throws SQLException {
        Vehicle vehicle = new Vehicle();
        
        vehicle.setId(rs.getInt("id"));
        vehicle.setClient(client);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setYear(rs.getInt("year"));
        vehicle.setRenavam(rs.getString("renavam"));
        vehicle.setColor(rs.getString("color"));
        
        return vehicle;
    }
    
}
