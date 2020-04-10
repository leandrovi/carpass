/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.services;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.RatingDao;
import model.entities.Rating;

/**
 *
 * @author leandro
 */
public class RatingService {
    
    private RatingDao dao = DaoFactory.createRatingDao();
    
    public List<Rating> findAll() {
        return dao.findAll();
    }
	
    public void saveOrUpdate(Rating obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        }
        else {
            dao.update(obj);
        }
    }

    public void remove(Rating obj) {
        dao.deleteById(obj.getId());
    }
    
}
