/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.ejb;

import gr.kourtzis.dgs.entity.Customer;
import gr.kourtzis.dgs.entity.User;
import gr.kourtzis.dgs.jpa.CustomerAdministrationDao;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author akourtzis
 */

@Stateless(mappedName = "ejb/customerAdministration")
public class CustomerAdministrationBean implements CustomerAdministrationBeanRemote {
    @EJB
    private CustomerAdministrationDao customerAdministrationDao;

    /**
     * The method returns all customer objects saved in the database.
     * @return A list of customer objects.
     */
    @Override
    public List<Customer> readEntries() {
        return customerAdministrationDao.readEntries();
    }

    /**
     * The method returns a customer object. A user object is passed by 
     * as a parameter. The customer returned must have the same id as
     * the if from the user object.
     * @param user A User object.
     * @return A customer object or null.
     */
    @Override
    public Customer readEntry(final User user) {
        return customerAdministrationDao.readEntry(user);
    }

    /**
     * The method creates a customer entry in the database.
     * @param customer The Customer object to be added.
     */
    @Override
    public void create(final Customer customer) {
        customerAdministrationDao.create(customer);
    }

    /**
     * The method updates an existing customer object from the database.
     * @param customer The customer object to be updated.
     */
    @Override
    public void update(final Customer customer) {
        customerAdministrationDao.update(customer);
    }

    /**
     * The method deletes an existing customer object from the database.
     * @param customer The customer object to be deleted.
     */
    @Override
    public void delete(final Customer customer) {
        customerAdministrationDao.delete(customer);
    }

    /**
     * The method returns the size of the customer object saved
     * in the database.
     * @return A Long variable.
     */
    @Override
    public Long count() {
        return customerAdministrationDao.count();
    }
}
