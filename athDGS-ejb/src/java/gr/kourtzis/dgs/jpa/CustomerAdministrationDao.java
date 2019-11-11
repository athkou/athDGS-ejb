/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.jpa;

import gr.kourtzis.dgs.entity.Customer;
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
public class CustomerAdministrationDao {
    @PersistenceContext
    private EntityManager entityManager;
    
    /**
     * The method returns all customer objects saved in the database.
     * @return A list of customer objects.
     */
    public List<Customer> readEntries() {
        Query query = entityManager.createQuery("SELECT cust FROM Customer cust");
        List<Customer> customers = query.getResultList();
        
        return customers;
    }
    
    /**
     * The method returns a customer object. A user object is passed by 
     * as a parameter. The customer returned must have the same id as
     * the if from the user object.
     * @param user A User object.
     * @return A customer object or null.
     */
    public Customer readEntry(final User user) {
        return entityManager.find(Customer.class, user.getUserId());
    }
    
    /**
     * The method creates a customer entry in the database.
     * @param customer The Customer object to be added.
     */
    public void create(final Customer customer) {
        if(customer.getCustomerId() == 0)
            entityManager.persist(customer);
        else
            entityManager.merge(customer);
        
        entityManager.flush();
    }
    
    /**
     * The method updates an existing customer object from the database.
     * @param customer The customer object to be updated.
     */
    public void update(final Customer customer) {
        create(customer);
    }
    
    /**
     * The method deletes an existing customer object from the database.
     * @param customer The customer object to be deleted.
     */
    public void delete(final Customer customer) {
        if(customer.getCustomerId() != 0) {
            Customer temp = entityManager.merge(customer);
            entityManager.remove(temp);
        }
        
        entityManager.flush();
    }
    
    /**
     * The method returns the size of the customer object saved
     * in the database.
     * @return A Long variable.
     */
    public Long count() { 
        Long result = null;
        try {
            Query query = entityManager.createQuery("SELECT COUNT(cust) FROM Customer cust");
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
