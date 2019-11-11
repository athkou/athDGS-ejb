/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.ejb;

import gr.kourtzis.dgs.entity.Activation;
import gr.kourtzis.dgs.entity.User;
import gr.kourtzis.dgs.jpa.ActivationAdministrationDao;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Athanasios Kourtzis
 */

@Stateless(mappedName = "ejb/activationAdministration")
public class ActivationAdministrationBean implements ActivationAdministrationBeanRemote {
    @EJB
    private ActivationAdministrationDao activationAdministrationDao;

    /**
     * The method returns all activation entries saved in the database
     * @return A list of Activation objects.
     */
    @Override
    public List<Activation> readEntries() {
        return activationAdministrationDao.readEntries();
    }
    
    /**
     * The method returns a Activation object that contains 
     * a specific token
     * @param token A string parameter.
     * @return An Activation object or null.
     */
    @Override
    public Activation readEntry(final String token) {
        return activationAdministrationDao.readEntry(token);
    }
    
    /**
     * The method returns a User object. An activation object is used
     * to narrow down the user who has the same id as the activation object.
     * @param activation An activation object
     * @return A user object or null.
     */
    @Override
    public User readUser(final Activation activation) {
        return activationAdministrationDao.readUser(activation);
    }

    /**
     * The method returns a Activation object. A User object is used as
     * a parameter to match the id of the Activation object we are looking for.
     * @param user A user object
     * @return An activation object or null.
     */
    @Override
    public Activation readEntryByUser(final User user) {
        return activationAdministrationDao.readEntryByUser(user);
    }

    /**
     * The method adds an Activation object to the database.
     * @param activation The activation object to be added.
     */
    @Override
    public void create(final Activation activation) {
        activationAdministrationDao.create(activation);
    }

    /**
     * The method updates an existing activation object from the database.
     * @param activation The activation object to be updated.
     */
    @Override
    public void update(final Activation activation) {
        activationAdministrationDao.update(activation);
    }

    /**
     * The method deletes an activation object from the database.
     * @param activation The activation object to be deleted.
     */
    @Override
    public void delete(final Activation activation) {
        activationAdministrationDao.delete(activation);
    }

    /**
     * The method returns the size of the activation objects saved 
     * in the database.
     * @return A Long variable.
     */
    @Override
    public Long count() {
        return activationAdministrationDao.count();
    }
}
