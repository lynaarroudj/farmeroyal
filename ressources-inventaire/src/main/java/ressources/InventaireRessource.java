package ressources;

import dto.InventaireDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.InventaireService;

import java.util.List;

@Path("/inventaire")
public class InventaireRessource {
    @Inject
    InventaireService inventoryService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInventoryById(@PathParam("id") Long id) {
        InventaireDTO inventory = inventoryService.getInventoryById(id);
        if (inventory == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(inventory).build();
    }

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInventoryByUserId(@PathParam("userId") Long userId) {
        List<InventaireDTO> inventory = inventoryService.getInventoryByUserId(userId);
        return Response.ok(inventory).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOrUpdateInventory(InventaireDTO inventoryDTO) {
        inventoryService.addOrUpdateInventory(inventoryDTO);
        return Response.ok("Inventory updated successfully!").build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeInventory(@PathParam("id") Long id) {
        inventoryService.removeInventory(id);
        return Response.noContent().build();
    }

}
