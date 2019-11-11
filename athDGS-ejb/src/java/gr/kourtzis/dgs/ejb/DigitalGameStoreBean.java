/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.ejb;

import gr.kourtzis.dgs.entity.Category;
import gr.kourtzis.dgs.entity.Game;
import gr.kourtzis.dgs.jpa.CategoryDao;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author akourtzis
 */
@Stateless(mappedName = "ejb/athDigitalGameStore")
public class DigitalGameStoreBean implements DigitalGameStoreBeanRemote {
    @EJB
    private CategoryDao categoryDao;
    
    /**
     * The method reads all categories from the database
     * @return A list of category objects
     */
    @Override
    public List<Category> getCategories() {
        return categoryDao.readEntries();
    }

    /**
     * The method adds a category to the database.
     * @param category The category to be added. 
     */
    @Override
    public void addCategory(final Category category) {
        categoryDao.create(category);
    }
    
    /**
     * The method updates a category that is already saved in the database
     * @param category The category to be updated.
     */
    @Override
    public void update(final Category category) {
        categoryDao.update(category);
    }
    
    /**
     * The method deletes a category from the database
     * @param category The category to be deleted.
     */
    @Override
    public void delete(final Category category) {
        categoryDao.delete(category);
    }
    
    /**
     * The method returns the total amount of category objects
     * stored in the database
     * @return A Long variable
     */
    @Override
    public Long count() {
        return categoryDao.count();
    }

    /**
     * The method returns the games for a specific category.
     * @param category_id An integer which is the primary key
     *                    representing a category.
     * @return A list of game objects
     */
    @Override
    public List<Game> getGamesByCategory(int category_id) {
        return categoryDao.readGamesByCategory(category_id);
    }

    /**
     * The method returns all the games saved in the database.
     * @return A list of game objects.
     */
    @Override
    public List<Game> getAllGames() {
        return categoryDao.readAllGames();
    }
}
