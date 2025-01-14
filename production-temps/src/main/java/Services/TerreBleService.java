package Services;

import DAO.TerrebleDAO;
import DTO.TerreBleDto;
import entity.TerreBle;
import exception.TerreNotFound;
import exception.TypeProductionInvalidException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TerreBleService {

    @Inject
    TerrebleDAO terreBleDAO;

    public TerreBleDto getById(Long id) throws TerreNotFound {
        TerreBle terre = terreBleDAO.getById(id);
        if (terre == null) {
            throw new TerreNotFound("La terre de blé avec l'ID " + id + " n'existe pas.");
        }
        return new TerreBleDto(terre.getId(), terre.getNom(), terre.getTypeProduction());
    }

    public List<TerreBleDto> getAll() {
        return terreBleDAO.getAll().stream()
                .map(t -> new TerreBleDto(t.getId(), t.getNom(), t.getTypeProduction()))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createTerreBle(TerreBleDto terreBleDTO) throws TypeProductionInvalidException {
        TerreBle terre = new TerreBle();
        terre.setNom(terreBleDTO.nom());
        terre.setTypeProduction(terreBleDTO.typeProduction());
        terre.setTempsProduction(Productiontemps.getTempsProduction(terreBleDTO.typeProduction()));
        terreBleDAO.save(terre);
        return terre.getId();
    }

    @Transactional
    public void startProduction(Long id) throws TerreNotFound{
        TerreBle terre = terreBleDAO.getById(id);
        if (terre == null) {
            throw new TerreNotFound("La terre de blé avec l'ID " + id + " n'existe pas.");
        }
        terre.setProductionStartTime(LocalDateTime.now());
    }

    public int getTempsRestant(Long id) throws TerreNotFound{
        TerreBle terre = terreBleDAO.getById(id);
        if (terre == null) {
            throw new TerreNotFound("La terre de blé avec l'ID " + id + " n'existe pas.");
        }
        return terre.getTempsRestant();
    }
}
