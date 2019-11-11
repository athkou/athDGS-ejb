/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.jpa;

import gr.kourtzis.dgs.entity.Activation;
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
public class ActivationAdministrationDao {
    @PersistenceContext
    private EntityManager entityManager;
    
    /**
     * The method returns all activation entries saved in the database
     * @return A list of Activation objects.
     */
    public List<Activation> readEntries() {
        Query query = entityManager.createQuery("SELECT act FROM Activation act");
        List<Activation> activations = query.getResultList();
        
        return activations;
    }
    
    /**
     * The method returns a Activation object that contains 
     * a specific token
     * @param token A string parameter.
     * @return An Activation object or null.
     */
    public Activation readEntry(final String token) {
        if(token == null || token.isEmpty())
            return null;
        try {
            Query query = entityManager.createQuery("SELECT act FROM Activation act WHERE act.activationToken = :token");
            query.setParameter("token", token);
            
            return (Activation) query.getSingleResult();
        }
        catch(NoResultException ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.out.println("Returning null");
            
            //return new Activation();
            return null;
        }
    }
    
    /**
     * The method returns a User object. An activation object is used
     * to narrow down the user who has the same id as the activation object.
     * @param activation An activation object
     * @return A user object or null.
     */
    public User readUser(final Activation activation) {
        try {
            Query query = entityManager.createQuery(
                    "SELECT usr " +
                    "FROM User usr, Activation act " +
                    "WHERE usr.userId = act.activationId " +
                    "AND act.activationId = :activationId");
            query.setParameter("activationId", activation.getActivationId());
            
            return (User) query.getSingleResult();
        }
        catch(NoResultException ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.out.println("Returning an empty object since the are no entries in the database");
            
            //return new User();
            return null;
        }
    }
    
    /**
     * The method returns a Activation object. A User object is used as
     * a parameter to match the id of the Activation object we are looking for.
     * @param user A user object
     * @return An activation object or null.
     */
    public Activation readEntryByUser(final User user) {
        return entityManager.find(Activation.class, user.getUserId());
    }
    
    /**
     * The method adds an Activation object to the database.
     * @param activation The activation object to be added.
     */
    public void create(final Activation activation) {
        if(activation.getActivationId() == 0)
            entityManager.persist(activation);
        else
            entityManager.merge(activation);
        
        entityManager.flush();
    }
    
    /**
     * The method updates an existing activation object from the database.
     * @param activation The activation object to be updated.
     */
    public void update(final Activation activation) {
        create(activation);
    }
    
    /**
     * The method deletes an activation object from the database.
     * @param activation The activation object to be deleted.
     */
    public void delete(final Activation activation) {
        if(activation.getActivationId() != 0) {
            Activation temp = entityManager.merge(activation);
            entityManager.remove(temp);
        }
        
        entityManager.flush();
    }
    
    /**
     * The method returns the size of the activation objects saved 
     * in the database.
     * @return A Long variable.
     */
    public Long count() { 
        Long result = null;
        try {
            Query query = entityManager.createQuery("SELECT COUNT(act) FROM Activation act");
            result = (Long) query.getSingleResult();
        }
        catch(NoResultException ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.out.println("Returning 0 since the are zero entries in the database");
            
            return 0L;
        }
        
        return result;
    }
}
