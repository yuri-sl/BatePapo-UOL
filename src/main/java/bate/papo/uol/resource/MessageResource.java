package bate.papo.uol.resource;


import bate.papo.uol.DTO.Request.PutEditarMensagemDTO;
import bate.papo.uol.DTO.Request.SendMessagePeersDTO;
import bate.papo.uol.entidade.Message;
import bate.papo.uol.entidade.Participant;
import bate.papo.uol.repository.MessageRepository;
import bate.papo.uol.service.MessageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

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
            return RestResponse.status(201,"Mensagem enviada com sucesso");
        } catch (IllegalArgumentException e){
            log.infof(e.getMessage());
            return RestResponse.status(422,e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("{User}")
    public RestResponse<?> fetchAllViewableMessagesFromUser(@PathParam("User") String username, @QueryParam("limit") String limit){
        try{
            log.info("No endpoint de pegar as mensagens!");
            log.infof(limit);
            List<Message> listaMensagens = messageService.getAllMessagesFromUser(username, limit);
            return RestResponse.status(Response.Status.OK,listaMensagens);
        } catch (NumberFormatException e) {
            return RestResponse.status(422, "O limite passado deve ser numérico");
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DELETE
    @Path("/{ID_DA_MENSAGEM}")
    public RestResponse<?> deletarMensagemComId(@PathParam("ID_DA_MENSAGEM") long idMensagem,@QueryParam("User") String username){
        try{
            messageService.deletarMensagemDadoIDUsuario(idMensagem,username);
            return RestResponse.status(RestResponse.Status.OK,"Mensagem deletada");
        } catch (RuntimeException e) {
            if(e.getMessage().equals("Você não é o dono da mensagem!"))
                return RestResponse.status(401,e.getMessage());
            return RestResponse.status(RestResponse.Status.NOT_FOUND,e.getMessage());
        }
    };
    @PUT
    @Path("/{ID_DA_MENSAGEM}")
    public RestResponse<?> editarMensagemComID(@PathParam("ID_DA_MENSAGEM") long idMensagem,
                                               @QueryParam("User") String username,
                                               PutEditarMensagemDTO putEditarMensagemDTO){
        try {
            messageService.editarMensagemComID(idMensagem,putEditarMensagemDTO,username);
            return RestResponse.status(RestResponse.Status.OK,"Mensagem no banco atualizada");
        } catch (RuntimeException e) {
            return RestResponse.status(404,e.getMessage());
        }
    }

}
