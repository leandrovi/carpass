/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.services;

import java.util.ArrayList;
import java.util.List;
import model.entities.Brand;
import model.entities.Client;
import model.entities.Model;
import model.entities.Vehicle;

/**
 *
 * @author leandro
 */
public class VehicleService {
    
    public List<Vehicle> findAll() {
        Brand brand = new Brand(1, "Hyundai");
        
        Model firstModel = new Model(1, "HB20", brand);
        Model secondModel = new Model(2, "i30", brand);
        Model thirdModel = new Model(3, "HR-V", brand);
        
        Client client = new Client(1, "Leandro Vieira", "leandro@gmail.com", "12345");
        
        List<Vehicle> list = new ArrayList<>();
        
        list.add(new Vehicle(1, "FNM2742", 2017, "38345960154", "Prata", client, brand, firstModel));
        list.add(new Vehicle(1, "CPZ7617", 2017, "63227407324", "Branco", client, brand, secondModel));
        list.add(new Vehicle(1, "ABC1234", 2017, "22320807135", "Preto", client, brand, thirdModel));
        
        return list;
    }
    
}
