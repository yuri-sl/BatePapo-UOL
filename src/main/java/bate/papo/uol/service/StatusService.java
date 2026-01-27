package bate.papo.uol.service;

import bate.papo.uol.DTO.Request.UpdateParticipantDTO;
import bate.papo.uol.entidade.Participant;
import bate.papo.uol.repository.MessageRepository;
import bate.papo.uol.repository.ParticipantRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@ApplicationScoped
public class StatusService {
    final MessageRepository messageRepository;
    final ParticipantRepository participantRepository;

    public void verifyValidUser(String username){
        if(username.isBlank())
            throw new IllegalArgumentException("Username deve estar preenchido");

        List<Participant> listaUsuarios = participantRepository.returnParticipantsWithSameName(username);
        if(listaUsuarios.isEmpty())
            throw new IllegalArgumentException("Não encontrado usuário com este nome");
    }


    @Transactional
    public void updateParticipant(String username){
        verifyValidUser(username);

        Participant userChosen = participantRepository.returnParticipantsWithSameName(username).getFirst();


        userChosen.setLastStatus(System.currentTimeMillis());

        UpdateParticipantDTO updateParticipantDTO = UpdateParticipantDTO.builder()
                .id(userChosen.getId())
                .name(userChosen.getName())
                .status(userChosen.getLastStatus())
                .build();
        participantRepository.updateParticipant(updateParticipantDTO);
    }
}
