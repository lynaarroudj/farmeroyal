package ressources;

import dto.UserDTO;
import exception.InvalidException;
import exception.UserAlreadyExistWithTheSameEmail;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.UserService;

import java.net.URI;

//les commentaire servent a ce que toute l'equipe se mette d'accord sur la methode, ce n'est pas du chat gpt
@Path("user")
public class UserRessources { //ressources jakarta rest

    @Inject
    UserService userService;

    @GET
    @Path("{id}") //retourne en json
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long id) { //retourner un utilisateurs
        UserDTO user = userService.getUserById(id);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return Response.ok(user).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON) //post en json
    public Response postUser(UserDTO userDTO){ //creer un utilisateur
        try {
            Long userid = userService.CheckAndSaveUser(userDTO);
            return Response.created(URI.create("/user/" + userid)).build();
        } catch (InvalidException e) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
        } catch (UserAlreadyExistWithTheSameEmail e) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT).build());
        }
    }

}
