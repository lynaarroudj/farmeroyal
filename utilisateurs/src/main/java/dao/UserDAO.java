package dao;

import entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class UserDAO {

    @Inject
    EntityManager em;

    public boolean isUserPresent(String email) {
        return !em.createQuery("SELECT u FROM User u WHERE u.email = :email")
                .setParameter("email", email)
                .getResultList().isEmpty();
    }

    public void saveUser(User user) {
        em.persist(user);
    }

    public User getById(Long id) {
        return em.find(User.class, id);
    }
}
