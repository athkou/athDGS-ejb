/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.jms;

import gr.kourtzis.dgs.ejb.EmailSessionBean;
import gr.kourtzis.dgs.entity.Account;
import gr.kourtzis.dgs.jpa.ActivationAdministrationDao;
import gr.kourtzis.dgs.jpa.AddressAdministrationDao;
import gr.kourtzis.dgs.jpa.CustomerAdministrationDao;
import gr.kourtzis.dgs.jpa.UserAdministrationDao;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * @author athko
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/athDgsAccountQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class AccountRegisterEmailMessageBean implements MessageListener {
    @EJB
    private UserAdministrationDao userAdministrationDao;
    @EJB
    private ActivationAdministrationDao activationAdministrationDao;
    @EJB
    private CustomerAdministrationDao customerAdministrationDao;
    @EJB
    private AddressAdministrationDao addressAdministrationDao;
    @EJB
    private EmailSessionBean emailSessionBean;
    
    private Account account;
    
    public AccountRegisterEmailMessageBean() {
    }
    
    /**
     * The method reads a message from the message broker, creates the appropriate
     * user, activation, customer and address entries in the database and sends 
     * the email with verification link.
     * @param message 
     */
    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) message;
                account = (Account) objectMessage.getObject();
                
                createAccount();
                if(account.isSendEmail())
                    emailSessionBean.send(account);
            }
        }
        catch (JMSException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
    }
    
    private void createAccount() {
        userAdministrationDao.create(account.getUser());
        activationAdministrationDao.create(account.getActivation());
        customerAdministrationDao.create(account.getCustomer());
        addressAdministrationDao.create(account.getAddress());
    }
}
