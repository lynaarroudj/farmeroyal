package DAO;

import entity.TerreBle;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class TerrebleDAO {

    @Inject
    EntityManager em;

    public void save(TerreBle terreBle) {
        em.persist(terreBle);
    }

    public TerreBle getById(Long id) {
        return em.find(TerreBle.class, id);
    }

    public List<TerreBle> getAll() {
        return em.createQuery("SELECT t FROM TerreBle t", TerreBle.class).getResultList();
    }
}
