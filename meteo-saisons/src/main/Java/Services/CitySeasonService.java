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

        // Trouver la saison correspondant à la date
        CitySeason currentSeason = citySeasons.stream()
                .filter(season -> !date.isBefore(season.getSeasonStart()) && !date.isAfter(season.getSeasonEnd()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Aucune saison trouvée pour la date donnée."));

        // Générer une température aléatoire
        int temperature = currentSeason.getMinTemperature() +
                (int) (Math.random() * (currentSeason.getMaxTemperature() - currentSeason.getMinTemperature() + 1));

        // Déterminer les précipitations
        String precipitation;
        double randomValue = Math.random();
        if (randomValue < currentSeason.getRainProbability()) {
            precipitation = "pluie";
            currentSeason.setFertilityRate(currentSeason.getFertilityRate() + 2); // Fertilité augmentée de +2
        } else if (randomValue < currentSeason.getRainProbability() + currentSeason.getSnowProbability()) {
            precipitation = "neige";
            currentSeason.setFertilityRate(currentSeason.getFertilityRate() - 3); // Fertilité diminuée de -3
        } else if (temperature >= 35 && temperature <= 45) {
            precipitation = "sécheresse";
            currentSeason.setFertilityRate(currentSeason.getFertilityRate() - 2); // Fertilité diminuée de -2
        } else {
            precipitation = "ciel dégagé";
        }


        //currentSeason.setFertilityRate(Math.max(0, Math.min(1, currentSeason.getFertilityRate())));

        // Retourner les informations météo
        return String.format("Météo pour %s le %s : %d°C, %s. Fertilité : %.2f",
                cityName, date, temperature, precipitation, currentSeason.getFertilityRate());
    }
}
