package bate.papo.uol.repository;

import bate.papo.uol.DTO.Request.SendMessageParticipantDTO;
import bate.papo.uol.entidade.Message;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@ApplicationScoped
public class MessageRepository implements PanacheRepository<Message> {

    public void saveMessageDTOtoDB(SendMessageParticipantDTO sendMessageParticipantDTO){
        Message message = Message.builder()
                .from(sendMessageParticipantDTO.getFrom())
                .to(sendMessageParticipantDTO.getTo())
                .text(sendMessageParticipantDTO.getText())
                .type(sendMessageParticipantDTO.getType())
                .time(sendMessageParticipantDTO.getTime())
                .build();
        this.persist(message);

    }
}
