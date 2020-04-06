/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.util.Objects;

/**
 *
 * @author leandro
 */
public class Vehicle {
    
    private Integer id;
    private String plate;
    private Integer year;
    private String renavam;
    private String color;
    
    private Client client;
    private Brand brand;
    private Model model;
    
    public Vehicle() {}

    public Vehicle(
        Integer id,
        String plate,
        Integer year,
        String renavam,
        String color,
        Client client,
        Brand brand,
        Model model
    ) {
        this.id = id;
        this.plate = plate;
        this.year = year;
        this.renavam = renavam;
        this.color = color;
        this.client = client;
        this.brand = brand;
        this.model = model;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vehicle other = (Vehicle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vehicle{" + "id=" + id +
                ", plate=" + plate +
                ", year=" + year +
                ", renavam=" + renavam +
                ", color=" + color +
                ", client=" + client +
                ", brand=" + brand +
                ", model=" + model + '}';
    }
    
}
