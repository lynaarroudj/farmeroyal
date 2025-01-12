package dao;

import entity.CitySeason;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class CitySeasonDao {

    @Inject
    private EntityManager em;

    public void saveCitySeason(CitySeason citySeason) {
        em.persist(citySeason);
    }

    public CitySeason getById(Long id) {
        return em.find(CitySeason.class, id);
    }

    public List<CitySeason> getByCityName(String cityName) {
        return em.createQuery("SELECT c FROM CitySeason c WHERE c.cityName = :cityName", CitySeason.class)
                .setParameter("cityName", cityName)
                .getResultList();
    }

    public List<CitySeason> getBySeasonName(String seasonName) {
        return em.createQuery("SELECT c FROM CitySeason c WHERE c.seasonName = :seasonName", CitySeason.class)
                .setParameter("seasonName", seasonName)
                .getResultList();
    }
}
