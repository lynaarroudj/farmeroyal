package dao;

import entity.Inventaire;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

@ApplicationScoped
public class InventaireDAO {

    @Inject
    EntityManager entityManager;

    public void saveInventory(Inventaire inventory) {
        entityManager.persist(inventory);
    }

    public Inventaire findById(Long id) {
        return entityManager.find(Inventaire.class, id);
    }

    public List<Inventaire> findByUserId(Long userId) {
        return entityManager.createQuery("select i from Inventaire i where i.userId = :userId")
                .setParameter("userId", userId)
                .getResultList();
    }

    public void updateInventory(Inventaire inventory) {
        entityManager.merge(inventory);
    }

    public void deleteInventory(Long id) {
        Inventaire inventory = findById(id);
        if (inventory != null) {
            entityManager.remove(inventory);
        }
    }


}
