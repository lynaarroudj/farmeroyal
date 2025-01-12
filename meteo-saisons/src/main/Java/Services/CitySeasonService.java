package Services;

import dao.CitySeasonDao;
import dto.CitySeasonDto;
import entity.CitySeason;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import Utils.DateUtils;
import exception.SeasonValidException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CitySeasonService {

    @Inject
    private CitySeasonDao citySeasonDao;

    @Transactional
    public Long saveCitySeason(CitySeasonDto citySeasonDto) {
        // Valider le nom de la saison
        SeasonValidException.validateSeasonName(citySeasonDto.seasonName());

        CitySeason citySeason = new CitySeason();
        citySeason.setCityName(citySeasonDto.cityName());

        // Utilisation de DateUtils pour les dates
        citySeason.setSeasonStart(DateUtils.parseWithDefaultYear(citySeasonDto.seasonStart()));
        citySeason.setSeasonEnd(DateUtils.parseWithDefaultYear(citySeasonDto.seasonEnd()));

        citySeason.setSeasonName(citySeasonDto.seasonName());

        citySeasonDao.saveCitySeason(citySeason);
        return citySeason.getId();
    }

    public CitySeasonDto getCitySeasonById(Long id) {
        CitySeason citySeason = citySeasonDao.getById(id);
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
        List<CitySeason> citySeasons = citySeasonDao.getByCityName(cityName);
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
        List<CitySeason> citySeasons = citySeasonDao.getBySeasonName(seasonName);
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
