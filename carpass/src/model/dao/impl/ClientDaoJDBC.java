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
import model.dao.ClientDao;
import model.entities.Brand;
import model.entities.Client;
import model.entities.Model;

/**
 *
 * @author leandro
 */
public class ClientDaoJDBC implements ClientDao {
    
    private Connection conn;
    
    public ClientDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Client obj) {
            PreparedStatement st = null;
            
            try {
                st = conn.prepareStatement(
                    "INSERT INTO client(name, email, password) " +
                    "VALUES (?, ?, ?);"
                );
                
                st.setString(1, obj.getName());
            }
        

    }

    @Override
    public void update(Client obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Client findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT * " +
                "FROM client " +
                "WHERE client.id = ?"
            );
            
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Client client = new Client();
                
                client.setId(rs.getInt("id"));
                client.setName(rs.getString("name"));
                client.setEmail(rs.getString("email"));
                client.setPassword(rs.getString("password"));
                client.setToken(rs.getString("token"));
                client.setTokenUpdatedAt(
                    new java.util.Date(
                        rs.getTimestamp("token_updated_at").getTime()
                    )
                );

                return client;
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
    public List<Client> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT * " +
                "FROM client " +
                "ORDER BY name;"
            );
            
            rs = st.executeQuery();
            
            List<Client> list = new ArrayList<>();

            while (rs.next()) {
                Client client = new Client();
                
                client.setId(rs.getInt("id"));
                client.setName(rs.getString("name"));
                client.setEmail(rs.getString("email"));
                client.setPassword(rs.getString("password"));
                client.setToken(rs.getString("token"));
                client.setTokenUpdatedAt(
                    new java.util.Date(
                        rs.getTimestamp("token_updated_at").getTime()
                    )
                );
                
                list.add(client);
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
