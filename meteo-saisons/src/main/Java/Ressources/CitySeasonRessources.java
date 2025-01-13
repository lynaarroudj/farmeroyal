package Ressources;

import dto.CitySeasonDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import Services.CitySeasonService;
import Utils.DateUtils;


import java.time.LocalDate;

@Path("city-seasons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CitySeasonRessources {

    @Inject
    private CitySeasonService citySeasonService;

    @POST
    public Response addCitySeason(CitySeasonDto citySeasonDto) {
        Long id = citySeasonService.saveCitySeason(citySeasonDto);
        return Response.status(Response.Status.CREATED).entity("Saison créée avec l'ID : " + id).build();
    }

    @GET
    @Path("{cityName}/weather")
    public Response getWeatherForCity(@PathParam("cityName") String cityName, @QueryParam("date") String date) {
        try {
            // Validation du format de la date
            if (date == null || !date.matches("\\d{2}/\\d{2}")) {
                throw new IllegalArgumentException("Le format de la date est invalide. Utilisez le format : dd/MM");
            }

            LocalDate parsedDate = DateUtils.parseWithDefaultYear(date);

            // Génération des conditions météo
            String weather = citySeasonService.getWeatherForCity(cityName, parsedDate);

            return Response.ok(weather).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
