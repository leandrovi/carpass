/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.Rating;

/**
 *
 * @author leandro
 */
public interface RatingDao {
    
    void insert(Rating rating);
    void update(Rating rating);
    void deleteById(Integer id);
    Rating findById(Integer id);
    Rating findByProvider(Integer id);
    Rating findByClient(Integer id);
    List<Rating> findAll();
    
}
