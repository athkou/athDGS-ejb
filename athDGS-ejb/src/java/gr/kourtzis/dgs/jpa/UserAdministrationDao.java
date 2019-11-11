/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.jpa;

import gr.kourtzis.dgs.entity.User;
import java.util.ArrayList;
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
public class UserAdministrationDao {
    @PersistenceContext
    private EntityManager entityManager;
    
    /**
     * The method returns all users saved in the database.
     * @return A list of User objects.
     */
    public List<User> readEntries() {
        Query query = entityManager.createQuery("SELECT usr FROM User usr");
        List<User> users = query.getResultList();
        
        return users;
    }
    
    /**
     * The method searches for all the emails saved in the database.
     * @return A list of String objects.
     */
    public List<String> readAllEmails() {
        List<String> emails = new ArrayList<>();
        try {
            Query query = entityManager.createQuery("SELECT usr.email FROM User usr");
            emails = query.getResultList();
        }
        catch(NoResultException ex) {
            System.out.println("Exception:" + ex.getMessage());
            System.out.println("Returning empty list");
        }
        return emails;
    }

    /**
     * The method searches the database for a user with a
     * specified id
     * @param id An integer variable, the id of the user we are looking for
     * @return The user with the specified id, or null.
     */
    public User readEntry(int id) {
        try {
            Query query = entityManager.createQuery("SELECT usr FROM User usr WHERE usr.userId = :userId");
            query.setParameter("userId", id);
            
            return (User) query.getSingleResult();
        }
        catch(NoResultException noResEx) {
            System.out.println("An exception occured: " + noResEx.getMessage());
            System.out.println("Did not found an user with an id: " + id);
            
            return null;
        }
    }
    
    /**
     * The method searches the database for a user with a 
     * specific email address.
     * @param email A String object which contains the email address.
     * @return A User object.
     */
    public User readEntryByEmail(final String email) {
       try {
            Query query = entityManager.createQuery("SELECT usr FROM User usr WHERE usr.email = :email");
            query.setParameter("email", email);
            
            return (User) query.getSingleResult();
        }
        catch(NoResultException noResEx) {
            System.out.println("An exception occured: " + noResEx.getMessage());
            System.out.println("Did not found an user with email: " + email);
            
            return null;
        }
    }
    
    /**
     * The method creates a User object in the database
     * @param user The User object to be created.
     */
    public void create(final User user) {
        if(user.getUserId() == 0)
            entityManager.persist(user);
        else
            entityManager.merge(user);
        
        entityManager.flush();
    }
    
    /**
     * The method updates an existing user object in the database.
     * @param user The User object to be updated.
     */
    public void update(final User user) {
        create(user);
    }
    
    /**
     * The method deletes an existing user object in the database.
     * @param user The user object to be deleted.
     */
    public void delete(final User user) {
        if(user.getUserId() != 0) {
            User usr = entityManager.merge(user);
            entityManager.remove(usr);
        }
        
        entityManager.flush();
    }
}
