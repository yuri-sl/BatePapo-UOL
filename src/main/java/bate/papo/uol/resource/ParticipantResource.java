package bate.papo.uol.resource;

import bate.papo.uol.DTO.Request.CreateNewParticipantDTORequest;
import bate.papo.uol.DTO.Response.CreateNewParticipantDTOResponse;
import bate.papo.uol.entidade.Participant;
import bate.papo.uol.repository.ParticipantRepository;
import bate.papo.uol.service.ParticipantService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@AllArgsConstructor
@ApplicationScoped
@Path("/participants")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ParticipantResource {
    final ParticipantService participantService;
    private final ParticipantRepository participantRepository;


    @POST
    public RestResponse<?> createNewUser(CreateNewParticipantDTORequest createNewParticipantDTORequest){
        try {
            CreateNewParticipantDTOResponse dados = participantService.criarNovoParticipante(createNewParticipantDTORequest);
            return RestResponse.status(RestResponse.Status.OK);
        } catch (IllegalStateException e){
            return RestResponse.status(RestResponse.Status.fromStatusCode(409),"Usuário já existe no sistema");
        } catch (BadRequestException e){
            return RestResponse.status(RestResponse.Status.BAD_REQUEST,"Todos os campos precisam estar preenchidos");
        } catch (RuntimeException e) {
            return RestResponse.status(RestResponse.Status.BAD_GATEWAY,e);
        }
    }

    @GET
    public RestResponse<?> getAllParticipants(){
        try {
            List<Participant> listaParticipantes = participantRepository.findAllParticipants();
            return RestResponse.status(Response.Status.FOUND,listaParticipantes);
        } catch (RuntimeException e) {
            return RestResponse.status(RestResponse.Status.BAD_REQUEST,e);
        }

    }

    @GET
    @Path("/health")
    public RestResponse<?> health(){
            return RestResponse.status(RestResponse.Status.OK);
    }
}
