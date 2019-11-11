/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.jpa;

import gr.kourtzis.dgs.entity.Game;
import gr.kourtzis.dgs.entity.Inventory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Athanasios Kourtzis
 */
@Stateless
public class InventoryDao {
    @PersistenceContext
    private EntityManager entityManager;
    
    /**
     * The method returns a list with all Inventory object
     * saved in the database.
     * @return A list of Inventory objects.
     */
    public List<Inventory> readEntries() {
        Query query = entityManager.createQuery("SELECT inv FROM Inventory inv");
        return query.getResultList();
    }
    
     /** 
     * The method returns a list of integer variables. The variables contain 
     * the id of every Inventory object.
     * @return A list of Integers.
     */
    public List<Integer> readIdEntries() {
        Query query = entityManager.createQuery("SELECT inv.inventoryId FROM Inventory inv");
        return query.getResultList();
    }
    
    /**
     * The method returns a Inventory object from the database. 
     * A Game object is used as a parameter to match the id's from 
     * both game and the returning inventory objects.
     * @param game A Game object.
     * @return An Inventory object or null.
     */
    public Inventory readEntry(final Game game) {
        return entityManager.find(Inventory.class, game.getGameId());
    }
    
    /**
     * The method creates a new Inventory object in the database.
     * @param inventory The Inventory object to be created.
     */
    public void create(final Inventory inventory) {
        if(inventory.getInventoryId() == 0)
            entityManager.persist(inventory);
        else 
            entityManager.merge(inventory);
        
        entityManager.flush();
    }
    
    /**
     * The method updates an existing inventory object
     * in the database.
     * @param inventory The Inventory object to be updated.
     */
    public void update(final Inventory inventory) {
        create(inventory);
    }
    
    /**
     * The method deletes an existing inventory object from the database.
     * @param inventory The Inventory object to be deleted.
     */
    public void delete(final Inventory inventory) {
        if(inventory.getInventoryId() != 0) {
            Inventory temp = entityManager.merge(inventory);
            entityManager.remove(temp);
        }
        
        entityManager.flush();
    }
    
    /**
     * The method returns the size of the inventory objects
     * saved in the database.
     * @return A Long variable.
     */
    public Long count() {
        Long result = null;
        try {
            Query query = entityManager.createQuery("SELECT COUNT(inv) FROM Inventory inv");
            result = (Long)query.getSingleResult();
        }
        catch(NoResultException ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.out.println("Returning 0 since there are zero entries in the database.");
            
            return 0L;
        }
        
        return result;
    }
}
