/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.Provider;

/**
 *
 * @author leandro
 */
public interface ProviderDao {
    
    void insert(Provider obj);
    void update(Provider obj);
    void deleteById(Integer id);
    Provider findById(Integer id);
    List<Provider> findAll();
    
}
