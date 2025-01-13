package Services;

import dao.CitySeasonDao;
import dto.CitySeasonDto;
import entity.CitySeason;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import Utils.DateUtils;
import exception.SeasonValidException;
import java.time.LocalDate;
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
        citySeason.setSeasonStart(DateUtils.parseWithDefaultYear(citySeasonDto.seasonStart()));
        citySeason.setSeasonEnd(DateUtils.parseWithDefaultYear(citySeasonDto.seasonEnd()));
        citySeason.setSeasonName(citySeasonDto.seasonName());
        citySeason.setMinTemperature(citySeasonDto.minTemperature());
        citySeason.setMaxTemperature(citySeasonDto.maxTemperature());
        citySeason.setRainProbability(citySeasonDto.rainProbability());
        citySeason.setSnowProbability(citySeasonDto.snowProbability());

        citySeasonDao.saveCitySeason(citySeason);
        return citySeason.getId();
    }

    public String getWeatherForCity(String cityName, LocalDate date) {
        List<CitySeason> citySeasons = citySeasonDao.getByCityName(cityName);

        CitySeason currentSeason = citySeasons.stream()
                .filter(season -> !date.isBefore(season.getSeasonStart()) && !date.isAfter(season.getSeasonEnd()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Aucune saison trouvée pour la date donnée."));

        int temperature = currentSeason.getMinTemperature() +
                (int) (Math.random() * (currentSeason.getMaxTemperature() - currentSeason.getMinTemperature() + 1));

        String precipitation;
        double randomValue = Math.random();
        if (randomValue < currentSeason.getRainProbability()) {
            precipitation = "pluie";
        } else if (randomValue < currentSeason.getRainProbability() + currentSeason.getSnowProbability()) {
            precipitation = "neige";
        } else {
            precipitation = "ciel dégagé";
        }

        return String.format("Météo pour %s le %s : %d°C, %s",
                cityName, date, temperature, precipitation);
    }
}
