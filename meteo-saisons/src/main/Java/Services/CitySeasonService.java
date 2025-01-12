package Services;

import dao.CitySeasonDao;
import dto.CitySeasonDto;
import entity.CitySeason;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import Utils.DateUtils;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CitySeasonService {

    @Inject
    private CitySeasonDao citySeasonDAO;

    @Transactional
    public Long saveCitySeason(CitySeasonDto citySeasonDTO) {
        CitySeason citySeason = new CitySeason();
        citySeason.setCityName(citySeasonDTO.cityName());

        // Utilisation de DateUtils pour les dates
        citySeason.setSeasonStart(DateUtils.parseWithDefaultYear(citySeasonDTO.seasonStart()));
        citySeason.setSeasonEnd(DateUtils.parseWithDefaultYear(citySeasonDTO.seasonEnd()));

        citySeason.setSeasonName(citySeasonDTO.seasonName());

        citySeasonDAO.saveCitySeason(citySeason);
        return citySeason.getId();
    }

    public CitySeasonDto getCitySeasonById(Long id) {
        CitySeason citySeason = citySeasonDAO.getById(id);
        if (citySeason == null) {
            return null;
        }
        return new CitySeasonDto(
                citySeason.getCityName(),
                citySeason.getSeasonStart().toString(),
                citySeason.getSeasonEnd().toString(),
                citySeason.getSeasonName()
        );
    }

    public List<CitySeasonDto> getCitySeasonsByCityName(String cityName) {
        List<CitySeason> citySeasons = citySeasonDAO.getByCityName(cityName);
        return citySeasons.stream()
                .map(citySeason -> new CitySeasonDto(
                        citySeason.getCityName(),
                        citySeason.getSeasonStart().toString(),
                        citySeason.getSeasonEnd().toString(),
                        citySeason.getSeasonName()
                ))
                .collect(Collectors.toList());
    }

    public List<CitySeasonDto> getCitySeasonsBySeasonName(String seasonName) {
        List<CitySeason> citySeasons = citySeasonDAO.getBySeasonName(seasonName);
        return citySeasons.stream()
                .map(citySeason -> new CitySeasonDto(
                        citySeason.getCityName(),
                        citySeason.getSeasonStart().toString(),
                        citySeason.getSeasonEnd().toString(),
                        citySeason.getSeasonName()
                ))
                .collect(Collectors.toList());
    }
}
