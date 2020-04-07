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
    
    void insert(Rating obj);
    void update(Rating obj);
    void deleteById(Integer id);
    Rating findById(Integer id);
    List<Rating> findAll();
    
}
