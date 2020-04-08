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
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.dao.ClientDao;
import model.entities.Client;

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
    public void insert(Client client) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "INSERT INTO client(name, email, password) " +
                "VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, client.getName());
            st.setString(2, client.getEmail());
            st.setString(3, client.getPassword());

            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected!");
            }

            ResultSet rs = st.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);

                client.setId(id);
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
    public void update(Client client) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "UPDATE client " +
                "SET name = ?, email = ?, password = ? " +
                "WHERE id = ?;"
            );

            st.setString(1, client.getName());
            st.setString(2, client.getEmail());
            st.setString(3, client.getPassword());
            st.setInt(4, client.getId());
            
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
            st = conn.prepareStatement("DELETE FROM client WHERE id = ?;");
            
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
                //client.setToken(rs.getString("token"));
                //client.setTokenUpdatedAt(
                //    new java.util.Date(
                //        rs.getTimestamp("token_updated_at").getTime()
                //    )
                //);
                client.setToken(null);
                client.setTokenUpdatedAt(null);

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
                //client.setToken(rs.getString("token"));
                //client.setTokenUpdatedAt(
                //    new java.util.Date(
                //        rs.getTimestamp("token_updated_at").getTime()
                //    )
                //);
                client.setToken(null);
                client.setTokenUpdatedAt(null);
                
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
