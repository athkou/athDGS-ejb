/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.ejb;

import gr.kourtzis.dgs.entity.Game;
import gr.kourtzis.dgs.entity.Review;
import gr.kourtzis.dgs.entity.User;
import gr.kourtzis.dgs.jpa.ReviewAdministrationDao;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Athanasios Kourtzis
 */
@Stateless(mappedName = "ejb/reviewAdministration")
public class ReviewAdministrationBean implements ReviewAdministrationBeanRemote {
    @EJB
    private ReviewAdministrationDao reviewAdministrationDao;

    @Override
    public List<Review> readEntries() {
        return reviewAdministrationDao.readEntries();
    }
    
    @Override
    public List<Review> readEntries(boolean reviewFlagged) {
        return reviewAdministrationDao.readEntries(reviewFlagged);
    }
    
    @Override
    public void create(final Review review) {
        reviewAdministrationDao.create(review);
    }
    
    @Override
    public void update(final Review review) {
        reviewAdministrationDao.update(review);
    }

    @Override
    public List<Review> readEntriesByUser(User user) {
        return reviewAdministrationDao.readEntriesByUser(user);
    }

    @Override
    public List<Review> readEntriesByGame(Game game) {
        return reviewAdministrationDao.readEntriesByGame(game);
    }

    @Override
    public Review readEntry(int id) {
        return reviewAdministrationDao.readEntry(id);
    }

    @Override
    public Long count() {
        return reviewAdministrationDao.count();
    }
}
