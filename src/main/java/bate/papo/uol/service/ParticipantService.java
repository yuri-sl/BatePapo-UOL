package bate.papo.uol.service;


import bate.papo.uol.DTO.CreateNewParticipantDTORequest;
import bate.papo.uol.DTO.CreateNewParticipantDTOResponse;
import bate.papo.uol.entidade.Participant;
import bate.papo.uol.repository.ParticipantRepository;
import io.smallrye.config.ConfigValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.ConcurrentModificationException;
import java.util.List;

@AllArgsConstructor
@ApplicationScoped
public class ParticipantService {
    final ParticipantRepository participantRepository;
    private final CreateNewParticipantDTOResponse createNewParticipantDTOResponse;

    public void validateFieldsCreateNewParticipant(CreateNewParticipantDTORequest createNewParticipantDTORequest){
       if(createNewParticipantDTORequest.getName() == null)
           throw new BadRequestException("Todos os campos precisam estar preenchidos");
       final String newUserName = createNewParticipantDTORequest.getName();
        List<Participant> listaParticipantesEncontrados = participantRepository.returnParticipantsWithSameName(newUserName);
        if(!listaParticipantesEncontrados.isEmpty())
            throw new IllegalStateException("usuário já existe");
    }
    public CreateNewParticipantDTOResponse criarNovoParticipante(CreateNewParticipantDTORequest createNewParticipantDTORequest){
        validateFieldsCreateNewParticipant(createNewParticipantDTORequest);

        String participantName = createNewParticipantDTORequest.getName();
        long participantLastStatus = System.currentTimeMillis();

        Participant participant1 = Participant.builder()
                .name(participantName)
                .lastStatus(participantLastStatus)
                .build();
        participantRepository.persist(participant1);
        participantRepository.flush();
        return CreateNewParticipantDTOResponse.builder()
                .name(participant1.getName())
                .lastStatus(participant1.getLastStatus())
                .build();

    }


}
