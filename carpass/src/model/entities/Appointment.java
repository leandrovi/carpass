/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author leandro
 */
public class Appointment {
    
    private Integer id;
    private Date date;
    private Date cancelledAt;
    
    private Provider provider;
    private Vehicle vehicle;
    private Specialty specialty;
    
    public Appointment() {}

    public Appointment(Integer id, Date date, Date cancelledAt, Provider provider, Vehicle vehicle, Specialty specialty) {
        this.id = id;
        this.date = date;
        this.cancelledAt = cancelledAt;
        this.provider = provider;
        this.vehicle = vehicle;
        this.specialty = specialty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(Date cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final Appointment other = (Appointment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Appointment{" + "id=" + id +
                ", date=" + date +
                ", cancelledAt=" + cancelledAt +
                ", provider=" + provider +
                ", vehicle=" + vehicle +
                ", specialty=" + specialty + '}';
    }
    
}
