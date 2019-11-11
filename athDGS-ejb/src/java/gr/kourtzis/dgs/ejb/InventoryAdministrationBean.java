/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.ejb;

import gr.kourtzis.dgs.entity.Game;
import gr.kourtzis.dgs.entity.Inventory;
import gr.kourtzis.dgs.jpa.InventoryDao;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Athanasios Kourtzis
 */
@Stateless(mappedName = "ejb/inventoryAdministration")
public class InventoryAdministrationBean implements InventoryAdministrationBeanRemote {
    @EJB
    InventoryDao inventoryDao;

    /**
     * The method returns a list with all Inventory object
     * saved in the database.
     * @return A list of Inventory objects.
     */
    @Override
    public List<Inventory> readEntries() {
        return inventoryDao.readEntries();
    }
    
    /** 
     * The method returns a list of integer variables. The variables contain 
     * the id of every Inventory object.
     * @return A list of Integers.
     */
    @Override
    public List<Integer> readIdEntries() {
        return inventoryDao.readIdEntries();
    }

    /**
     * The method returns a Inventory object from the database. 
     * A Game object is used as a parameter to match the id's from 
     * both game and the returning inventory objects.
     * @param game A Game object.
     * @return An Inventory object or null.
     */
    @Override
    public Inventory readEntry(Game game) {
        return inventoryDao.readEntry(game);
    }

    /**
     * The method creates a new Inventory object in the database.
     * @param inventory The Inventory object to be created.
     */
    @Override
    public void create(Inventory inventory) {
        inventoryDao.create(inventory);
    }

    /**
     * The method updates an existing inventory object
     * in the database.
     * @param inventory The Inventory object to be updated.
     */
    @Override
    public void update(Inventory inventory) {
        inventoryDao.update(inventory);
    }

    /**
     * The method deletes an existing inventory object from the database.
     * @param inventory The Inventory object to be deleted.
     */
    @Override
    public void delete(Inventory inventory) {
        inventoryDao.delete(inventory);
    }

    /**
     * The method returns the size of the inventory objects
     * saved in the database.
     * @return A Long variable.
     */
    @Override
    public Long count() {
        return inventoryDao.count();
    }
}
