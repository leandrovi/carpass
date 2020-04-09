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
import model.dao.AppointmentDao;
import model.entities.Appointment;
import model.entities.Brand;
import model.entities.Client;
import model.entities.Model;
import model.entities.Plan;
import model.entities.Provider;
import model.entities.Specialty;
import model.entities.Vehicle;

/**
 *
 * @author leandro
 */
public class AppointmentDaoJDBC implements AppointmentDao {
    
    private Connection conn;
    
    public AppointmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Appointment appointment) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "INSERT INTO appointment " +
                "(id_provider, id_vehicle, vehicle_plate, id_specialty, date, cancelled_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            st.setInt(1, appointment.getProvider().getId());
            st.setInt(2, appointment.getVehicle().getId());
            st.setString(3, appointment.getVehicle().getPlate());
            st.setInt(4, appointment.getSpecialty().getId());
            st.setDate(5, new java.sql.Date(appointment.getDate().getTime()));
            st.setDate(6, new java.sql.Date(appointment.getCancelledAt().getTime()));

            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected!");
            }

            ResultSet rs = st.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);

                appointment.setId(id);
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
    public void update(Appointment appointment) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "UPDATE appointment " +
                "SET "+
                "   id_provider = ?, " +
                "   id_vehicle = ?, " +
                "   vehicle_plate = ?, " +
                "   id_specialty = ?, " +
                "   date = ?, " +
                "   cancelled_at = ? " +
                "WHERE id = ?;"
            );

            st.setInt(1, appointment.getProvider().getId());
            st.setInt(2, appointment.getVehicle().getId());
            st.setString(3, appointment.getVehicle().getPlate());
            st.setInt(4, appointment.getSpecialty().getId());
            st.setDate(5, new java.sql.Date(appointment.getDate().getTime()));
            st.setDate(6, new java.sql.Date(appointment.getCancelledAt().getTime()));
            st.setInt(7, appointment.getId());
            
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
            st = conn.prepareStatement("DELETE FROM appointment WHERE id = ?;");
            
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
    public Appointment findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   appointment.*, " +
                "   plan.id as plan_id, " +
                "   plan.name as plan_name, " +
                "   plan.value as plan_value, " +
                "   provider.name as provider_name, " +
                "   provider.email as provider_email, " +
                "   provider.password as provider_password, " +
                "   client.id as client_id, " +
                "   client.name as client_name, " +
                "   client.email as client_email, " +
                "   client.password as client_password, " +
                "   brand.id as brand_id, " +
                "   brand.name as brand_name, " +
                "   model.id as model_id, " +
                "   model.name as model_name, " +
                "   vehicle.year as vehicle_year, " +
                "   vehicle.renavam as vehicle_renavam, " +
                "   vehicle.color as vehicle_color, " +
                "   specialty.name as specialty " +
                "FROM appointment " +
                "INNER JOIN provider " +
                "ON appointment.id_provider = provider.id " +
                "INNER JOIN plan " +
                "ON provider.id_plan = plan.id " +
                "INNER JOIN vehicle " +
                "ON appointment.id_vehicle = vehicle.id " +
                "INNER JOIN client " +
                "ON vehicle.id_client = client.id " +
                "INNER JOIN brand " +
                "ON vehicle.id_brand = brand.id " +
                "INNER JOIN model " +
                "ON vehicle.id_model = model.id " +
                "INNER JOIN specialty " +
                "ON appointment.id_specialty = specialty.id " +
                "WHERE appointment.id = ?"
            );
            
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Plan plan = instantiatePlan(rs);
                Provider provider = instantiateProvider(rs, plan);
                Client client = instantiateClient(rs);
                Brand brand = instantiateBrand(rs);
                Model model = instantiateModel(rs, brand);
                Vehicle vehicle = instantiateVehicle(rs, client, brand, model);
                Specialty specialty = instantiateSpecialty(rs);
                
                Appointment appointment = instantiateAppointment(
                    rs,
                    provider,
                    vehicle,
                    specialty
                );

                return appointment;
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
    public List<Appointment> findByProvider(Provider provider) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   appointment.*, " +
                "   plan.id as plan_id, " +
                "   plan.name as plan_name, " +
                "   plan.value as plan_value, " +
                "   provider.name as provider_name, " +
                "   provider.email as provider_email, " +
                "   provider.password as provider_password, " +
                "   client.id as client_id, " +
                "   client.name as client_name, " +
                "   client.email as client_email, " +
                "   client.password as client_password, " +
                "   brand.id as brand_id, " +
                "   brand.name as brand_name, " +
                "   model.id as model_id, " +
                "   model.name as model_name, " +
                "   vehicle.year as vehicle_year, " +
                "   vehicle.renavam as vehicle_renavam, " +
                "   vehicle.color as vehicle_color, " +
                "   specialty.name as specialty " +
                "FROM appointment " +
                "INNER JOIN provider " +
                "ON appointment.id_provider = provider.id " +
                "INNER JOIN plan " +
                "ON provider.id_plan = plan.id " +
                "INNER JOIN vehicle " +
                "ON appointment.id_vehicle = vehicle.id " +
                "INNER JOIN client " +
                "ON vehicle.id_client = client.id " +
                "INNER JOIN brand " +
                "ON vehicle.id_brand = brand.id " +
                "INNER JOIN model " +
                "ON vehicle.id_model = model.id " +
                "INNER JOIN specialty " +
                "ON appointment.id_specialty = specialty.id " +
                "WHERE appointment.id_provider = ?"
            );
            
            st.setInt(1, provider.getId());
            
            rs = st.executeQuery();
            
            List<Appointment> list = new ArrayList<>();
            
            Map<Integer, Plan> planMap = new HashMap<>();
            Map<Integer, Provider> providerMap = new HashMap<>();
            Map<Integer, Client> clientMap = new HashMap<>();
            Map<Integer, Brand> brandMap = new HashMap<>();
            Map<Integer, Model> modelMap = new HashMap<>();
            Map<Integer, Vehicle> vehicleMap = new HashMap<>();
            Map<Integer, Specialty> specialtyMap = new HashMap<>();
            
            while (rs.next()) {
                Plan planInstance = planMap.get(rs.getInt("plan_id"));
                Provider providerInstance = providerMap.get(rs.getInt("id_provider"));
                Client clientInstance = clientMap.get(rs.getInt("client_id"));
                Brand brandInstance = brandMap.get(rs.getInt("brand_id"));
                Model modelInstance = modelMap.get(rs.getInt("client_id"));
                Vehicle vehicleInstance = vehicleMap.get(rs.getInt("id_vehicle"));
                Specialty specialtyInstance = specialtyMap.get(rs.getInt("id_specialty"));
                
                if (planInstance == null) {
                    planInstance = instantiatePlan(rs);
                    planMap.put(rs.getInt("plan_id"), planInstance);
                } 
                
                if (providerInstance == null) {
                    providerInstance = instantiateProvider(rs, planInstance);
                    providerMap.put(rs.getInt("id_provider"), providerInstance);
                } 
                
                if (clientInstance == null) {
                    clientInstance = instantiateClient(rs);
                    clientMap.put(rs.getInt("id_client"), clientInstance);
                } 
                
                if (brandInstance == null) {
                    brandInstance = instantiateBrand(rs);
                    brandMap.put(rs.getInt("brand_id"), brandInstance);
                } 
                
                if (modelInstance == null) {
                    modelInstance = instantiateModel(rs, brandInstance);
                    modelMap.put(rs.getInt("model_id"), modelInstance);
                } 
                
                if (vehicleInstance == null) {
                    vehicleInstance = instantiateVehicle(
                        rs,
                        clientInstance,
                        brandInstance,
                        modelInstance
                    );
                    vehicleMap.put(rs.getInt("id_vehicle"), vehicleInstance);
                } 
                
                if (specialtyInstance == null) {
                    specialtyInstance = instantiateSpecialty(rs);
                    specialtyMap.put(rs.getInt("id_specialty"), specialtyInstance);
                } 
                
                Appointment appointment = instantiateAppointment(
                    rs,
                    providerInstance,
                    vehicleInstance,
                    specialtyInstance
                );

                list.add(appointment);
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
    public List<Appointment> findByVehicle(Vehicle vehicle) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   appointment.*, " +
                "   plan.id as plan_id, " +
                "   plan.name as plan_name, " +
                "   plan.value as plan_value, " +
                "   provider.name as provider_name, " +
                "   provider.email as provider_email, " +
                "   provider.password as provider_password, " +
                "   client.id as client_id, " +
                "   client.name as client_name, " +
                "   client.email as client_email, " +
                "   client.password as client_password, " +
                "   brand.id as brand_id, " +
                "   brand.name as brand_name, " +
                "   model.id as model_id, " +
                "   model.name as model_name, " +
                "   vehicle.year as vehicle_year, " +
                "   vehicle.renavam as vehicle_renavam, " +
                "   vehicle.color as vehicle_color, " +
                "   specialty.name as specialty " +
                "FROM appointment " +
                "INNER JOIN provider " +
                "ON appointment.id_provider = provider.id " +
                "INNER JOIN plan " +
                "ON provider.id_plan = plan.id " +
                "INNER JOIN vehicle " +
                "ON appointment.id_vehicle = vehicle.id " +
                "INNER JOIN client " +
                "ON vehicle.id_client = client.id " +
                "INNER JOIN brand " +
                "ON vehicle.id_brand = brand.id " +
                "INNER JOIN model " +
                "ON vehicle.id_model = model.id " +
                "INNER JOIN specialty " +
                "ON appointment.id_specialty = specialty.id " +
                "WHERE appointment.id_vehicle = ?, " +
                "ORDER BY vehicle.plate;"
            );
            
            st.setInt(1, vehicle.getId());
            
            rs = st.executeQuery();
            
            List<Appointment> list = new ArrayList<>();
            
            Map<Integer, Plan> planMap = new HashMap<>();
            Map<Integer, Provider> providerMap = new HashMap<>();
            Map<Integer, Client> clientMap = new HashMap<>();
            Map<Integer, Brand> brandMap = new HashMap<>();
            Map<Integer, Model> modelMap = new HashMap<>();
            Map<Integer, Vehicle> vehicleMap = new HashMap<>();
            Map<Integer, Specialty> specialtyMap = new HashMap<>();
            
            while (rs.next()) {
                Plan planInstance = planMap.get(rs.getInt("plan_id"));
                Provider providerInstance = providerMap.get(rs.getInt("id_provider"));
                Client clientInstance = clientMap.get(rs.getInt("client_id"));
                Brand brandInstance = brandMap.get(rs.getInt("brand_id"));
                Model modelInstance = modelMap.get(rs.getInt("client_id"));
                Vehicle vehicleInstance = vehicleMap.get(rs.getInt("id_vehicle"));
                Specialty specialtyInstance = specialtyMap.get(rs.getInt("id_specialty"));
                
                if (planInstance == null) {
                    planInstance = instantiatePlan(rs);
                    planMap.put(rs.getInt("plan_id"), planInstance);
                } 
                
                if (providerInstance == null) {
                    providerInstance = instantiateProvider(rs, planInstance);
                    providerMap.put(rs.getInt("id_provider"), providerInstance);
                } 
                
                if (clientInstance == null) {
                    clientInstance = instantiateClient(rs);
                    clientMap.put(rs.getInt("id_client"), clientInstance);
                } 
                
                if (brandInstance == null) {
                    brandInstance = instantiateBrand(rs);
                    brandMap.put(rs.getInt("brand_id"), brandInstance);
                } 
                
                if (modelInstance == null) {
                    modelInstance = instantiateModel(rs, brandInstance);
                    modelMap.put(rs.getInt("model_id"), modelInstance);
                } 
                
                if (vehicleInstance == null) {
                    vehicleInstance = instantiateVehicle(
                        rs,
                        clientInstance,
                        brandInstance,
                        modelInstance
                    );
                    vehicleMap.put(rs.getInt("id_vehicle"), vehicleInstance);
                } 
                
                if (specialtyInstance == null) {
                    specialtyInstance = instantiateSpecialty(rs);
                    specialtyMap.put(rs.getInt("id_specialty"), specialtyInstance);
                } 
                
                Appointment appointment = instantiateAppointment(
                    rs,
                    providerInstance,
                    vehicleInstance,
                    specialtyInstance
                );

                list.add(appointment);
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
    public List<Appointment> findBySpecialty(Specialty specialty) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   appointment.*, " +
                "   plan.id as plan_id, " +
                "   plan.name as plan_name, " +
                "   plan.value as plan_value, " +
                "   provider.name as provider_name, " +
                "   provider.email as provider_email, " +
                "   provider.password as provider_password, " +
                "   client.id as client_id, " +
                "   client.name as client_name, " +
                "   client.email as client_email, " +
                "   client.password as client_password, " +
                "   brand.id as brand_id, " +
                "   brand.name as brand_name, " +
                "   model.id as model_id, " +
                "   model.name as model_name, " +
                "   vehicle.year as vehicle_year, " +
                "   vehicle.renavam as vehicle_renavam, " +
                "   vehicle.color as vehicle_color, " +
                "   specialty.name as specialty " +
                "FROM appointment " +
                "INNER JOIN provider " +
                "ON appointment.id_provider = provider.id " +
                "INNER JOIN plan " +
                "ON provider.id_plan = plan.id " +
                "INNER JOIN vehicle " +
                "ON appointment.id_vehicle = vehicle.id " +
                "INNER JOIN client " +
                "ON vehicle.id_client = client.id " +
                "INNER JOIN brand " +
                "ON vehicle.id_brand = brand.id " +
                "INNER JOIN model " +
                "ON vehicle.id_model = model.id " +
                "INNER JOIN specialty " +
                "ON appointment.id_specialty = specialty.id " +
                "WHERE appointment.id_specialty = ?, " +
                "ORDER BY specialty.name;"
            );
            
            st.setInt(1, specialty.getId());
            
            rs = st.executeQuery();
            
            List<Appointment> list = new ArrayList<>();
            
            Map<Integer, Plan> planMap = new HashMap<>();
            Map<Integer, Provider> providerMap = new HashMap<>();
            Map<Integer, Client> clientMap = new HashMap<>();
            Map<Integer, Brand> brandMap = new HashMap<>();
            Map<Integer, Model> modelMap = new HashMap<>();
            Map<Integer, Vehicle> vehicleMap = new HashMap<>();
            Map<Integer, Specialty> specialtyMap = new HashMap<>();
            
            while (rs.next()) {
                Plan planInstance = planMap.get(rs.getInt("plan_id"));
                Provider providerInstance = providerMap.get(rs.getInt("id_provider"));
                Client clientInstance = clientMap.get(rs.getInt("client_id"));
                Brand brandInstance = brandMap.get(rs.getInt("brand_id"));
                Model modelInstance = modelMap.get(rs.getInt("client_id"));
                Vehicle vehicleInstance = vehicleMap.get(rs.getInt("id_vehicle"));
                Specialty specialtyInstance = specialtyMap.get(rs.getInt("id_specialty"));
                
                if (planInstance == null) {
                    planInstance = instantiatePlan(rs);
                    planMap.put(rs.getInt("plan_id"), planInstance);
                } 
                
                if (providerInstance == null) {
                    providerInstance = instantiateProvider(rs, planInstance);
                    providerMap.put(rs.getInt("id_provider"), providerInstance);
                } 
                
                if (clientInstance == null) {
                    clientInstance = instantiateClient(rs);
                    clientMap.put(rs.getInt("id_client"), clientInstance);
                } 
                
                if (brandInstance == null) {
                    brandInstance = instantiateBrand(rs);
                    brandMap.put(rs.getInt("brand_id"), brandInstance);
                } 
                
                if (modelInstance == null) {
                    modelInstance = instantiateModel(rs, brandInstance);
                    modelMap.put(rs.getInt("model_id"), modelInstance);
                } 
                
                if (vehicleInstance == null) {
                    vehicleInstance = instantiateVehicle(
                        rs,
                        clientInstance,
                        brandInstance,
                        modelInstance
                    );
                    vehicleMap.put(rs.getInt("id_vehicle"), vehicleInstance);
                } 
                
                if (specialtyInstance == null) {
                    specialtyInstance = instantiateSpecialty(rs);
                    specialtyMap.put(rs.getInt("id_specialty"), specialtyInstance);
                } 
                
                Appointment appointment = instantiateAppointment(
                    rs,
                    providerInstance,
                    vehicleInstance,
                    specialtyInstance
                );

                list.add(appointment);
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
    public List<Appointment> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT " +
                "   appointment.*, " +
                "   plan.id as plan_id, " +
                "   plan.name as plan_name, " +
                "   plan.value as plan_value, " +
                "   provider.name as provider_name, " +
                "   provider.email as provider_email, " +
                "   provider.password as provider_password, " +
                "   client.id as client_id, " +
                "   client.name as client_name, " +
                "   client.email as client_email, " +
                "   client.password as client_password, " +
                "   brand.id as brand_id, " +
                "   brand.name as brand_name, " +
                "   model.id as model_id, " +
                "   model.name as model_name, " +
                "   vehicle.year as vehicle_year, " +
                "   vehicle.renavam as vehicle_renavam, " +
                "   vehicle.color as vehicle_color, " +
                "   specialty.name as specialty " +
                "FROM appointment " +
                "INNER JOIN provider " +
                "ON appointment.id_provider = provider.id " +
                "INNER JOIN plan " +
                "ON provider.id_plan = plan.id " +
                "INNER JOIN vehicle " +
                "ON appointment.id_vehicle = vehicle.id " +
                "INNER JOIN client " +
                "ON vehicle.id_client = client.id " +
                "INNER JOIN brand " +
                "ON vehicle.id_brand = brand.id " +
                "INNER JOIN model " +
                "ON vehicle.id_model = model.id " +
                "INNER JOIN specialty " +
                "ON appointment.id_specialty = specialty.id " +
                "ORDER BY appointment.date DESC;"
            );
            
            rs = st.executeQuery();
            
            List<Appointment> list = new ArrayList<>();
            
            Map<Integer, Plan> planMap = new HashMap<>();
            Map<Integer, Provider> providerMap = new HashMap<>();
            Map<Integer, Client> clientMap = new HashMap<>();
            Map<Integer, Brand> brandMap = new HashMap<>();
            Map<Integer, Model> modelMap = new HashMap<>();
            Map<Integer, Vehicle> vehicleMap = new HashMap<>();
            Map<Integer, Specialty> specialtyMap = new HashMap<>();
            
            while (rs.next()) {
                Plan planInstance = planMap.get(rs.getInt("plan_id"));
                Provider providerInstance = providerMap.get(rs.getInt("id_provider"));
                Client clientInstance = clientMap.get(rs.getInt("client_id"));
                Brand brandInstance = brandMap.get(rs.getInt("brand_id"));
                Model modelInstance = modelMap.get(rs.getInt("client_id"));
                Vehicle vehicleInstance = vehicleMap.get(rs.getInt("id_vehicle"));
                Specialty specialtyInstance = specialtyMap.get(rs.getInt("id_specialty"));
                
                if (planInstance == null) {
                    planInstance = instantiatePlan(rs);
                    planMap.put(rs.getInt("plan_id"), planInstance);
                } 
                
                if (providerInstance == null) {
                    providerInstance = instantiateProvider(rs, planInstance);
                    providerMap.put(rs.getInt("id_provider"), providerInstance);
                } 
                
                if (clientInstance == null) {
                    clientInstance = instantiateClient(rs);
                    clientMap.put(rs.getInt("id_client"), clientInstance);
                } 
                
                if (brandInstance == null) {
                    brandInstance = instantiateBrand(rs);
                    brandMap.put(rs.getInt("brand_id"), brandInstance);
                } 
                
                if (modelInstance == null) {
                    modelInstance = instantiateModel(rs, brandInstance);
                    modelMap.put(rs.getInt("model_id"), modelInstance);
                } 
                
                if (vehicleInstance == null) {
                    vehicleInstance = instantiateVehicle(
                        rs,
                        clientInstance,
                        brandInstance,
                        modelInstance
                    );
                    vehicleMap.put(rs.getInt("id_vehicle"), vehicleInstance);
                } 
                
                if (specialtyInstance == null) {
                    specialtyInstance = instantiateSpecialty(rs);
                    specialtyMap.put(rs.getInt("id_specialty"), specialtyInstance);
                } 
                
                Appointment appointment = instantiateAppointment(
                    rs,
                    providerInstance,
                    vehicleInstance,
                    specialtyInstance
                );

                list.add(appointment);
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
        
        client.setId(rs.getInt("client_id"));
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

    private Brand instantiateBrand(ResultSet rs) throws SQLException {
        Brand brand = new Brand();

        brand.setId(rs.getInt("brand_id"));
        brand.setName(rs.getString("brand_name"));
        
        return brand;
    }

    private Model instantiateModel(ResultSet rs, Brand brand) throws SQLException {
        Model model = new Model();

        model.setId(rs.getInt("model_id"));
        model.setName(rs.getString("model_name"));
        model.setBrand(brand);
        
        return model;
    }
    
    private Vehicle instantiateVehicle(
        ResultSet rs,
        Client client,
        Brand brand,
        Model model
    ) throws SQLException {
        Vehicle vehicle = new Vehicle();
        
        vehicle.setId(rs.getInt("id_vehicle"));
        vehicle.setClient(client);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setYear(rs.getInt("vehicle_year"));
        vehicle.setRenavam(rs.getString("vehicle_renavam"));
        vehicle.setColor(rs.getString("vehicle_color"));
        
        return vehicle;
    }
    
    private Specialty instantiateSpecialty(ResultSet rs) throws SQLException {
        Specialty specialty = new Specialty();
        
        specialty.setId(rs.getInt("id_specialty"));
        specialty.setName(rs.getString("specialty"));
        
        return specialty;
    }
    
    private Appointment instantiateAppointment(
        ResultSet rs,
        Provider provider,
        Vehicle vehicle,
        Specialty specialty
    ) throws SQLException {
        Appointment appointment = new Appointment();
        
        appointment.setProvider(provider);
        appointment.setVehicle(vehicle);
        appointment.setSpecialty(specialty);
        appointment.setDate(
            new java.util.Date(rs.getTimestamp("date").getTime())
        );
        appointment.setCancelledAt(
            new java.util.Date(rs.getTimestamp("cancelled_at").getTime())
        );
        
        return appointment;
    }
    
}
