package bate.papo.uol.resource;


import bate.papo.uol.DTO.Request.SendMessagePeersDTO;
import bate.papo.uol.entidade.Participant;
import bate.papo.uol.repository.MessageRepository;
import bate.papo.uol.service.MessageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import static io.smallrye.config._private.ConfigLogging.log;

@AllArgsConstructor
@ApplicationScoped
@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
    final MessageService messageService;

    @POST
    @Path("{User}")
    public RestResponse<?> changeMessagesBetweenUsers(@PathParam("User") String username, SendMessagePeersDTO sendMessagePeersDTO){
        try{
            messageService.addMessageToDB(sendMessagePeersDTO, username);
            return RestResponse.status(RestResponse.Status.ACCEPTED,"Mensagem enviada com sucesso");
        } catch (IllegalArgumentException e){
            log.infof(e.getMessage());
            return RestResponse.status(422,e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
