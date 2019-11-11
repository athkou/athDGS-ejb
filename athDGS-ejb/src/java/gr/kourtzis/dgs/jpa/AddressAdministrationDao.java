/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.jpa;

import gr.kourtzis.dgs.entity.Address;
import gr.kourtzis.dgs.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author akourtzis
 */
@Stateless
public class AddressAdministrationDao {
    @PersistenceContext
    private EntityManager entityManager;
    
    /**
     * The method returns all address objects saved in the database.
     * @return A list of address objects.
     */
    public List<Address> readEntries() {
        Query query = entityManager.createQuery("SELECT addr FROM Address addr");
        List<Address> address = query.getResultList();
        
        return address;
    }
    
    /**
     * The method returns a address object. The address object  
     * must have the same id with the id from the user object.
     * @param user A user object.
     * @return An address object or null.
     */
    public Address readEntry(final User user) {
        return entityManager.find(Address.class, user.getUserId());
    }
    
    /**
     * The method adds an address object to the database.
     * @param address The Address object to be added.  
     */
    public void create(final Address address) {
        if(address.getAddressId() == 0)
            entityManager.persist(address);
        else
            entityManager.merge(address);
        
        entityManager.flush();
    }
    
    /**
     * The method updates an existing address object from the database.
     * @param address The Address object to be updated.
     */
    public void update(final Address address) {
        create(address);
    }
    
    /**
     * The method deletes an existing address object from the database.
     * @param address The Address object to be deleted.
     */
    public void delete(final Address address) {
        if(address.getAddressId() != 0) {
            Address temp = entityManager.merge(address);
            entityManager.remove(temp);
        }
        
        entityManager.flush();
    }
    
    /**
     * The method returns the size of the Address objects 
     * saved in the database.
     * @return A Long variable.
     */
    public Long count() { 
        Long result = null;
        try {
            Query query = entityManager.createQuery("SELECT COUNT(addr) FROM Address addr");
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
