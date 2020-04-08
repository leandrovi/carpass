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
import java.util.List;
import java.util.Scanner;
import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.dao.ModelDao;
import model.entities.Brand;
import model.entities.Client;
import model.entities.Model;
import model.entities.Specialty;

/**
 *
 * @author leandro
 */
public class Program {
    
    public static void main(String [] args) {
        
        Scanner sc = new Scanner(System.in);
        
        ModelDao modelDao = DaoFactory.createModelDao();
        
        System.out.println("=== TEST 1: model findById ===");        
        Model model = modelDao.findById(1);        
        System.out.println(model);
        
        System.out.println("\n=== TEST 2: model findByBrand ==="); 
        Brand brand = new Brand(11, null);
        List<Model> list = modelDao.findByBrand(brand);
        
        for (Model item : list) {
            System.out.println(item);
        }
        
        System.out.println("\n=== TEST 3: model findAll ==="); 
        list = modelDao.findAll();
        
        for (Model item : list) {
            System.out.println(item);
        }
        
        ClientDao clientDao = DaoFactory.createClientDao();
        
        System.out.println("\n=== TEST 4: client insert ===");
        //Client client = new Client(null, "Leandro Vieira", "leandro@gmail.com", "123456");
        //clientDao.insert(client);
        //System.out.println("Inserted! New id = " + client.getId());
        
        
        System.out.println("\n=== TEST 5: client update ===");
        Client client = clientDao.findById(1);
        client.setEmail("leandrovi@gmail.com");
        clientDao.update(client);
        System.out.println("Update completed");
        
        System.out.println("\n=== TEST 6: client delete ===");
        System.out.print("Enter id for delete test: ");
        int id = sc.nextInt();
        clientDao.deleteById(id);
        System.out.println("Delete completed");
        
        sc.close();
    }
    
}
