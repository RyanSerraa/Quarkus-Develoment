import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;


@Path("/users")
public class UserResource {
    @POST
    public Response createUser(CreateUserRequest userRequest) {
        // Example logic: Return a 201 Created response
        return Response.ok().build();
    }
}
