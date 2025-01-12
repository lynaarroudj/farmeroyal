package Ressources;

import dto.CitySeasonDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import Services.CitySeasonService;

import java.util.List;

@Path("city-seasons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CitySeasonRessources {

    @Inject
    private CitySeasonService citySeasonService;

    @GET
    @Path("{cityName}")
    public Response getCitySeasonsByCityName(@PathParam("cityName") String cityName) {
        List<CitySeasonDto> citySeasons = citySeasonService.getCitySeasonsByCityName(cityName);
        if (citySeasons.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No data found for city: " + cityName).build();
        }
        return Response.ok(citySeasons).build();
    }

    @POST
    public Response addCitySeason(CitySeasonDto citySeasonDTO) {
        Long id = citySeasonService.saveCitySeason(citySeasonDTO);
        return Response.status(Response.Status.CREATED).entity("City season created with ID: " + id).build();
    }
}
