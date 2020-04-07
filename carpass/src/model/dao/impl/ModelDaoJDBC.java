/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao.impl;

import db.DB;
import db.DbException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.dao.ModelDao;
import model.entities.Brand;
import model.entities.Model;

/**
 *
 * @author leandro
 */
public class ModelDaoJDBC implements ModelDao {
    
    private Connection conn;
    
    public ModelDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Model findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT model.*, brand.name as brand " +
                "FROM model INNER JOIN brand " +
                "ON model.id_brand = brand.id " +
                "WHERE model.id = ?"
            );
            
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Brand brand = instantiateBrand(rs);                
                Model model = instantiateModel(rs, brand);

                return model;
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
    public List<Model> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT model.*, brand.name as brand " +
                "FROM model INNER JOIN brand " +
                "ON model.id_brand = brand.id " +
                "ORDER BY name; "
            );
            
            rs = st.executeQuery();
            
            List<Model> list = new ArrayList<>();
            
            Map<Integer, Brand> map = new HashMap<>();
            
            while (rs.next()) {
                Brand brandInstance = map.get(rs.getInt("id_brand"));
                
                if (brandInstance == null) {
                    brandInstance = instantiateBrand(rs);
                    map.put(rs.getInt("id_brand"), brandInstance);
                }                
                               
                Model model = instantiateModel(rs, brandInstance);                
                list.add(model);
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
    public List<Model> findByBrand(Brand brand) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT model.*, brand.name as brand " +
                "FROM model INNER JOIN brand " +
                "ON model.id_brand = brand.id " +
                "WHERE id_brand = ? " +
                "ORDER BY name; "
            );
            
            st.setInt(1, brand.getId());
            rs = st.executeQuery();
            
            List<Model> list = new ArrayList<>();
            
            Map<Integer, Brand> map = new HashMap<>();
            
            while (rs.next()) {
                Brand brandInstance = map.get(rs.getInt("id_brand"));
                
                if (brandInstance == null) {
                    brandInstance = instantiateBrand(rs);
                    map.put(rs.getInt("id_brand"), brandInstance);
                }                
                               
                Model model = instantiateModel(rs, brandInstance);                
                list.add(model);
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
    
}
