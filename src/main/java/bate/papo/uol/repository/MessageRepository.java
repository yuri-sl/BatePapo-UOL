package bate.papo.uol.repository;

import bate.papo.uol.DTO.Request.LoggedInMessageParticipantDTO;
import bate.papo.uol.DTO.Request.SendMessagePeersWithFromDTO;
import bate.papo.uol.entidade.Message;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;


@AllArgsConstructor
@ApplicationScoped
public class MessageRepository implements PanacheRepository<Message> {

    public void saveMessageDTOtoDB(LoggedInMessageParticipantDTO loggedInMessageParticipantDTO){
        Message message = Message.builder()
                .from(loggedInMessageParticipantDTO.getFrom())
                .to(loggedInMessageParticipantDTO.getTo())
                .text(loggedInMessageParticipantDTO.getText())
                .type(loggedInMessageParticipantDTO.getType())
                .time(loggedInMessageParticipantDTO.getTime())
                .build();
        this.persist(message);

    }
}
