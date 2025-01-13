package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class CitySeason {

    @Id
    @GeneratedValue
    private Long id;

    private String cityName;

    private LocalDate seasonStart;

    private LocalDate seasonEnd;

    private String seasonName;

    private int minTemperature;

    private int maxTemperature;

    private double rainProbability;

    private double snowProbability;

    private double fertilityRate = 0; // Par défaut

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public LocalDate getSeasonStart() {
        return seasonStart;
    }

    public void setSeasonStart(LocalDate seasonStart) {
        this.seasonStart = seasonStart;
    }

    public LocalDate getSeasonEnd() {
        return seasonEnd;
    }

    public void setSeasonEnd(LocalDate seasonEnd) {
        this.seasonEnd = seasonEnd;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(double rainProbability) {
        this.rainProbability = rainProbability;
    }

    public double getSnowProbability() {
        return snowProbability;
    }

    public void setSnowProbability(double snowProbability) {
        this.snowProbability = snowProbability;
    }

    public double getFertilityRate() {
        return fertilityRate;
    }

    public void setFertilityRate(double fertilityRate) {
        this.fertilityRate = fertilityRate;
    }
}
