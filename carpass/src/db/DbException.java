/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

/**
 *
 * @author leandro
 */
public class DbException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public DbException(String msg) {
        super(msg);
    }
    
}
