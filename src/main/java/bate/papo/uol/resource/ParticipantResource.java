package bate.papo.uol.resource;

import bate.papo.uol.DTO.CreateNewParticipantDTORequest;
import bate.papo.uol.DTO.CreateNewParticipantDTOResponse;
import bate.papo.uol.service.ParticipantService;
import io.smallrye.config.ConfigValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@AllArgsConstructor
@ApplicationScoped
@Path("/participants")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ParticipantResource {
    final ParticipantService participantService;



    @POST
    public RestResponse<?> createNewUser(CreateNewParticipantDTORequest createNewParticipantDTORequest){
        try {
            CreateNewParticipantDTOResponse dados = participantService.criarNovoParticipante(createNewParticipantDTORequest);
            return RestResponse.status(RestResponse.Status.OK, dados);
        } catch (IllegalStateException e){
            return RestResponse.status(RestResponse.Status.fromStatusCode(409),"Usuário já existe no sistema");
        } catch (BadRequestException e){
            return RestResponse.status(RestResponse.Status.BAD_REQUEST,"Todos os campos precisam estar preenchidos");
        } catch (RuntimeException e) {
            return RestResponse.status(RestResponse.Status.BAD_GATEWAY,"Erro Inesperado");
        }

    }

}
