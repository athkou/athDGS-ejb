/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.jpa;

import gr.kourtzis.dgs.entity.Game;
import gr.kourtzis.dgs.entity.Review;
import gr.kourtzis.dgs.entity.User;
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
public class ReviewAdministrationDao {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * The method returns all review object saved in the database
     * @return A list of review objects.
     */
    public List<Review> readEntries() {
        Query query = entityManager.createQuery("SELECT rew FROM Review rew");
        return query.getResultList();
    }
    
    /**
     * The method returns a list of reviews with a specified boolean variable
     * in the parameter.
     * @param reviewFlagged The boolean variable used as parameter. We choose
     *                      true if we want all reported reviews or false if we
     *                      the non-reported reviews.
     * @return A list of review objects.
     */
    public List<Review> readEntries(boolean reviewFlagged) {
        Query query = entityManager.createQuery("SELECT rev FROM Review rev WHERE rev.reviewFlagged = :revFlagged");
        query.setParameter("revFlagged", reviewFlagged);
        
        return query.getResultList();
    }
    
    /**
     * The method creates a review object in the database
     * @param review The review object to be created.
     */
    public void create(final Review review) {
        if(review.getReviewId() == 0)
            entityManager.persist(review);
        else
            entityManager.merge(review);
        
        entityManager.flush();
    }
    
    /**
     * The method updates an existing review object in the database.
     * @param review The review object to be updated.
     */
    public void update(final Review review) {
        create(review);
    }

    /**
     * The method returns a list of review object created from 
     * the user specified in the parameters.
     * @param user A user object from which we want the reviews from the database.
     * @return A list of review object.
     */
    public List<Review> readEntriesByUser(User user) {
        Query query = entityManager.createQuery("SELECT rew FROM Review rew WHERE rew.user.userId = :user_id");
        query.setParameter("user_id", user.getUserId());
        
        return query.getResultList();
    }

    /**
     * 
     * @param game
     * @return 
     */
    public List<Review> readEntriesByGame(Game game) {
//        String nativeQuery = 
//                "SELECT * " +
//                "FROM review " +
//                "WHERE review_id IN " + 
//                    "(SELECT review_id " +
//                    " FROM review_games " +
//                    " WHERE game_id = 13)";
        Query query = entityManager.createQuery("SELECT rev FROM Review rev WHERE rev.reviewId IN (SELECT re FROM review_games re WHERE re.game_id = :game_id)");
        query.setParameter("game_id", game.getGameId());
        
        return query.getResultList();
    }

    /**
     * The method returns a review object with a specified id.
     * @param id The id of review we want from the database.
     * @return The review object if it was found, otherwise false.
     */
    public Review readEntry(int id) {
        try {
            Query query = entityManager.createQuery("SELECT rew FROM Review rew WHERE rew.reviewId = :review_id");
            query.setParameter("review_id", id);
        
            return (Review) query.getSingleResult();
        }
        catch(NoResultException ex) {
            System.out.println("An exception occured: " + ex.getMessage());
            System.out.println("Did not found a review with id: " + id);
            
            return null;
        }
    }

    /**
     * The method returns the size of the review object saved in the database.
     * @return A long variable.
     */
    public Long count() {
        try {
            Query query = entityManager.createQuery("SELECT COUNT(inv) FROM Inventory inv");
            return (Long)query.getSingleResult();
        }
        catch(NoResultException ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.out.println("Returning 0 since there are zero entries in the database.");
            
            return 0L;
        }
    }
}
