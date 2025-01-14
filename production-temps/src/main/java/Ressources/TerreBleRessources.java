package Ressources;

import DTO.TerreBleDto;
import exception.TerreNotFound;
import exception.TypeProductionInvalidException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import Services.TerreBleService;

import java.net.URI;
import java.util.List;

@Path("terreBle")
public class TerreBleRessources {

    @Inject
    TerreBleService terreBleService;

    @GET
    @Path("{id}/tempsRestant")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTempsRestant(@PathParam("id") Long id) {
        try {
            int tempsRestant = terreBleService.getTempsRestant(id);
            return Response.ok(tempsRestant).build();
        } catch (TerreNotFound e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Terrain avec ID " + id + " introuvable.")
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TerreBleDto> getAllTerres() {
        return terreBleService.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTerreBle(TerreBleDto terreBleDTO) {
        try {
            Long id = terreBleService.createTerreBle(terreBleDTO);
            return Response.created(URI.create("/terreBle/" + id)).build();
        } catch (TypeProductionInvalidException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("{id}/startProduction")
    public Response startProduction(@PathParam("id") Long id) {
        try {
            terreBleService.startProduction(id);
            return Response.noContent().build();
        } catch (TerreNotFound e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Terrain avec ID " + id + " introuvable.")
                    .build();
        }
    }
}
