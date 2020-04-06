/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import db.DB;
import model.entities.Specialty;

/**
 *
 * @author leandro
 */
public class Program {
    
    public static void main(String [] args) {
        
        Specialty obj = new Specialty(1, "Reparos Simples");
        System.out.println(obj);
        
    }
    
}
