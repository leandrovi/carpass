/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.sql.Connection;

import db.DB;

/**
 *
 * @author leandro
 */
public class Program {
    
    public static void main(String [] args) {
        
        Connection conn = DB.getConnection();
        DB.closeConnection();
        
    }
    
}
