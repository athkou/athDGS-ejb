/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.ejb;

import gr.kourtzis.dgs.entity.Address;
import gr.kourtzis.dgs.entity.User;
import gr.kourtzis.dgs.jpa.AddressAdministrationDao;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author akourtzis
 */

@Stateless(mappedName = "ejb/addressAdministration")
public class AddressAdministrationBean implements AddressAdministrationBeanRemote {
    @EJB
    private AddressAdministrationDao addressAdministrationDao;

    /**
     * The method returns all address objects saved in the database.
     * @return A list of address objects.
     */
    @Override
    public List<Address> readEntries() {
        return addressAdministrationDao.readEntries();
    }

    /**
     * The method returns a address object. The address object  
     * must have the same id with the id from the user object.
     * @param user A user object.
     * @return An address object or null.
     */
    @Override
    public Address readEntry(final User user) {
        return addressAdministrationDao.readEntry(user);
    }

    /**
     * The method adds an address object to the database.
     * @param address The Address object to be added.  
     */
    @Override
    public void create(final Address address) {
        addressAdministrationDao.create(address);
    }

    /**
     * The method updates an existing address object from the database.
     * @param address The Address object to be updated.
     */
    @Override
    public void update(final Address address) {
        addressAdministrationDao.update(address);
    }

    /**
     * The method deletes an existing address object from the database.
     * @param address The Address object to be deleted.
     */
    @Override
    public void delete(final Address address) {
        addressAdministrationDao.delete(address);
    }

    /**
     * The method returns the size of the Address objects 
     * saved in the database.
     * @return A Long variable.
     */
    @Override
    public Long count() {
        return addressAdministrationDao.count();
    }
}
