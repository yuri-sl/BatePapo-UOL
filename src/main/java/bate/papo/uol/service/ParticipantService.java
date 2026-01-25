package bate.papo.uol.service;


import bate.papo.uol.DTO.Request.CreateNewParticipantDTORequest;
import bate.papo.uol.DTO.Request.LoggedInMessageParticipantDTO;
import bate.papo.uol.DTO.Response.CreateNewParticipantDTOResponse;
import bate.papo.uol.entidade.Participant;
import bate.papo.uol.repository.MessageRepository;
import bate.papo.uol.repository.ParticipantRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@ApplicationScoped
public class ParticipantService {
    final ParticipantRepository participantRepository;
    final MessageRepository messageRepository;

    private final CreateNewParticipantDTOResponse createNewParticipantDTOResponse;

    public void validateFieldsCreateNewParticipant(CreateNewParticipantDTORequest createNewParticipantDTORequest){
       if(createNewParticipantDTORequest.getName() == null)
           throw new BadRequestException("Todos os campos precisam estar preenchidos");
       final String newUserName = createNewParticipantDTORequest.getName();
        List<Participant> listaParticipantesEncontrados = participantRepository.returnParticipantsWithSameName(newUserName);
        if(!listaParticipantesEncontrados.isEmpty())
            throw new IllegalStateException("usuário já existe");
    }


    @Transactional
    public void saveRegisterParticipant(CreateNewParticipantDTORequest createNewParticipantDTORequest){
        String participantName = createNewParticipantDTORequest.getName();

        LoggedInMessageParticipantDTO loggedInMessageParticipantDTO = LoggedInMessageParticipantDTO.builder()
                .from(participantName)
                .to("Todos")
                .text("entra na sala...")
                .type("status")
                .time(LocalTime.now())
                .build();
        messageRepository.saveMessageDTOtoDB(loggedInMessageParticipantDTO);
    }

    @Transactional
    public CreateNewParticipantDTOResponse criarNovoParticipante(CreateNewParticipantDTORequest createNewParticipantDTORequest){
        validateFieldsCreateNewParticipant(createNewParticipantDTORequest);

        String participantName = createNewParticipantDTORequest.getName();
        long participantLastStatus = System.currentTimeMillis();

        Participant participant1 = Participant.builder()
                .name(participantName)
                .lastStatus(participantLastStatus)
                .build();
        participantRepository.addParticipant(participant1);

        saveRegisterParticipant(createNewParticipantDTORequest);

        return CreateNewParticipantDTOResponse.builder()
                .name(participant1.getName())
                .lastStatus(participant1.getLastStatus())
                .build();

    }

    public List<Participant> findAllParticipantEntries(){
        return this.participantRepository.findAllParticipants();
    }


}
