package bate.papo.uol.resource;


import bate.papo.uol.DTO.Request.SendMessagePeersDTO;
import bate.papo.uol.entidade.Participant;
import bate.papo.uol.repository.MessageRepository;
import bate.papo.uol.service.MessageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

@AllArgsConstructor
@ApplicationScoped
@Path("/messages")
public class MessageResource {
    final MessageService messageService;

    @POST
    @Path("{User}")
    public RestResponse<?> changeMessagesBetweenUsers(@PathParam("{User}") Participant User, SendMessagePeersDTO sendMessagePeersDTO){
        try{
            String username = User.getName();
            messageService.addMessageToDB(sendMessagePeersDTO, username);
            return RestResponse.status(RestResponse.Status.ACCEPTED,"Mensagem enviada com sucesso");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
