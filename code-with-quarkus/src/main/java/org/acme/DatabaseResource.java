
package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/database")
public class DatabaseResource {
    @Inject
    DatabaseService databaseService;

    @GET
    @Path("/consulta")
    public Response consulta(@QueryParam("sql") String sql) {
        if (sql == null || sql.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("String vazia").build();
        }
        String result = databaseService.consulta(sql);
        return Response.ok(result).build();
    }

    @POST
    @Path("/executar")
    public Response executar(@QueryParam("sql") String sql) {
        if (sql == null || sql.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("String vazia").build();
        }
        String result = databaseService.executarComando(sql);
        return Response.ok(result).build();
    }
}
