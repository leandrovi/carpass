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
import model.dao.SpecialtyDao;
import model.entities.Specialty;

/**
 *
 * @author leandro
 */
public class SpecialtyDaoJDBC implements SpecialtyDao {
    
    private Connection conn;
    
    public SpecialtyDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Specialty findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT * " +
                "FROM specialty " +
                "WHERE specialty.id = ?"
            );
            
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Specialty specialty = new Specialty();
                
                specialty.setId(rs.getInt("id"));
                specialty.setName(rs.getString("name"));

                return specialty;
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
    public Specialty findByName(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT * " +
                "FROM specialty " +
                "WHERE specialty.name = ?"
            );
            
            st.setString(1, name);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Specialty specialty = new Specialty();
                
                specialty.setId(rs.getInt("id"));
                specialty.setName(rs.getString("name"));

                return specialty;
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
    public List<Specialty> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT * " +
                "FROM specialty " +
                "ORDER BY name;"
            );
            
            rs = st.executeQuery();
            
            List<Specialty> list = new ArrayList<>();

            while (rs.next()) {
                Specialty specialty = new Specialty();
                
                specialty.setId(rs.getInt("id"));
                specialty.setName(rs.getString("name"));
                
                list.add(specialty);
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
