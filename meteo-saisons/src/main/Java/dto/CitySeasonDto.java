package dto;

public record CitySeasonDto(
        String cityName,
        String seasonStart,
        String seasonEnd,
        String seasonName,
        int minTemperature,
        int maxTemperature,
        double rainProbability,
        double snowProbability
) {}
