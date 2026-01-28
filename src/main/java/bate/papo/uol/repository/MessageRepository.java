package bate.papo.uol.repository;

import bate.papo.uol.DTO.Request.LoggedInMessageParticipantDTO;
import bate.papo.uol.DTO.Request.SendMessagePeersWithFromDTO;
import bate.papo.uol.entidade.Message;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


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

    public List<Message> fetchAllMesagesToUserLimit(String username,String limit){
        int limitInt = Integer.parseInt(limit);
        return find(
                "WHERE (to = 'Todos'" +
                        " OR from =?1 " +
                        " OR (to =?1 AND type = 'private_message'))" +
                        " AND type <> 'status'"+
                        "LIMIT =?2"
                ,username,limitInt).stream().toList();
    }
    public List<Message> fetchAllMesagesToUser(String username){
        return find(
                "WHERE (to = 'Todos'" +
                " OR from =?1 " +
                " OR (to =?1 AND type = 'private_message'))" +
                " AND type <> 'status'",

                username).stream().toList();
    }
    public List<Message> fetchAllMessagesToUserLimit(String username,long limit_number){
        return find(
                "WHERE (to = 'Todos'"+
                        "OR from =?1 "+
                        "OR (to =?1 AND type = 'private_message'))"+
                        "AND type <> 'status'"+
                        "LIMIT =?2",
                username,limit_number).stream().toList();
    }
}
