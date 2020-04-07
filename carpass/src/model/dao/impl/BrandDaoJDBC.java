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
import model.dao.BrandDao;
import model.entities.Brand;

/**
 *
 * @author leandro
 */
public class BrandDaoJDBC implements BrandDao {
    
    private Connection conn;
    
    public BrandDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Brand findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT * " +
                "FROM brand " +
                "WHERE brand.id = ?"
            );
            
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Brand brand = new Brand();
                
                brand.setId(rs.getInt("id"));
                brand.setName(rs.getString("name"));

                return brand;
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
    public List<Brand> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT * " +
                "FROM brand " +
                "ORDER BY name;"
            );
            
            rs = st.executeQuery();
            
            List<Brand> list = new ArrayList<>();

            while (rs.next()) {
                Brand brand = new Brand();
                
                brand.setId(rs.getInt("id"));
                brand.setName(rs.getString("name"));
                
                list.add(brand);
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
