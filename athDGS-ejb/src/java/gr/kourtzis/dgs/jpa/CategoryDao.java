/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.jpa;

import gr.kourtzis.dgs.entity.Category;
import gr.kourtzis.dgs.entity.Game;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Athanasios Kourtzis
 */
@Stateless
public class CategoryDao {
    @PersistenceContext
    private EntityManager entityManager;
    
    /**
     * The method reads all categories from the database
     * @return A list of category objects
     */
    public List<Category> readEntries() {
        final String statement = " SELECT cat FROM Category cat";
        Query query = entityManager.createQuery(statement);
        List<Category> categories = query.getResultList();
        
        return categories;
    }

    public Category readEntry(int id) {
        String statement =
                "SELECT cat FROM Category cat " +
                "WHERE cat.category_id = :category_id";
        Query query = entityManager.createQuery(statement);
        query.setParameter("category_id", id);
        
        return (Category) query.getSingleResult();
    }

    /**
     * The method adds a category to the database.
     * @param category The category to be added. 
     */
    public void create(final Category category) {
        if(category.getCategoryId() == 0) 
            entityManager.persist(category);
        else 
            entityManager.merge(category);
        
        entityManager.flush();
    }

    /**
     * The method updates a category that is already saved in the database
     * @param category The category to be updated.
     */
    public void update(final Category category) {
        create(category);
    }

    /**
     * The method deletes a category from the database
     * @param category The category to be deleted.
     */
    public void delete(final Category category) {
        if(category.getCategoryId() != 0) {
            Category cat = entityManager.merge(category);
            entityManager.remove(cat);
        }
        
        entityManager.flush();
    }

    /**
     * The method returns the total amount of category objects
     * stored in the database
     * @return A Long variable
     */
    public Long count() {
        String statement =
                "SELECT COUNT(cat) FROM Category cat";
        Query query = entityManager.createQuery(statement);
        
        return (Long) query.getSingleResult();
    }

    /**
     * The method returns the games for a specific category.
     * @param category_id An integer which is the primary key
     *                    representing a category.
     * @return A list of game objects
     */
    public List<Game> readGamesByCategory(int category_id) {
        
        Query query = entityManager.createQuery("SELECT g FROM Game g  WHERE g.category.categoryId = :category_id");
        query.setParameter("category_id", category_id);
        
        List<Game> games = query.getResultList();
        
        return games;
    }

    /**
     * The method returns all the games saved in the database.
     * @return A list of game objects.
     */
    public List<Game> readAllGames() {
        String statement = " SELECT g FROM Game g ";
        Query query = entityManager.createQuery(statement);
        
        List<Game> games = query.getResultList();
        
        return games;
    }
}
