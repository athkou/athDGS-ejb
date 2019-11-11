/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.ejb;

import gr.kourtzis.dgs.entity.User;
import gr.kourtzis.dgs.jpa.UserAdministrationDao;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author akourtzis
 */

@Stateless(mappedName = "ejb/userAdministration")
public class UserAdministrationBean implements UserAdministrationBeanRemote {
    @EJB
    private UserAdministrationDao userAdministrationDao;

    /**
     * The method returns all users saved in the database.
     * @return A list of User objects.
     */
    @Override
    public List<User> readAllUsers() {
        return userAdministrationDao.readEntries();
    }

    /**
     * The method searches the database for a user with a 
     * specific email address.
     * @param email A String object which contains the email address.
     * @return A User object.
     */
    @Override
    public User readUser(String email) {
        return userAdministrationDao.readEntryByEmail(email);
    }
    
    @Override
    public User readUser(int id) {
        return userAdministrationDao.readEntry(id);
    }
    
    /**
     * The method searches for all the emails saved in the database.
     * @return A list of String objects.
     */
    @Override
    public List<String> readAllEmails() {
        return userAdministrationDao.readAllEmails();
    }

    /**
     * The method creates a User object in the database
     * @param user The User object to be created.
     */
    @Override
    public void create(final User user) {
        userAdministrationDao.create(user);
    }

    /**
     * The method updates an existing user object in the database.
     * @param user The User object to be updated.
     */
    @Override
    public void update(final User user) {
        userAdministrationDao.update(user);
    }
    
    @Override
    public void delete(final User user) {
        userAdministrationDao.delete(user);
    }
}
