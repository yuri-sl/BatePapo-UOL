package bate.papo.uol.resource;


import bate.papo.uol.service.StatusService;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/status")
@ApplicationScoped
@AllArgsConstructor
public class StatusResource {
    final StatusService statusService;

    @POST
    public RestResponse<?> updateHealthTime(@HeaderParam("User") String username){
        try{
            statusService.updateParticipant(username);
            return RestResponse.status(RestResponse.Status.OK,"Usuário editado com sucesso");
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.NOT_FOUND,e.getMessage());
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    };
}
