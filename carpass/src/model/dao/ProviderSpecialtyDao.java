/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.ProviderSpecialty;

/**
 *
 * @author leandro
 */
public interface ProviderSpecialtyDao {
    
    void insert(ProviderSpecialty obj);
    void update(ProviderSpecialty obj);
    void deleteById(Integer id);
    ProviderSpecialty findById(Integer id);
    List<ProviderSpecialty> findAll();
    
}
