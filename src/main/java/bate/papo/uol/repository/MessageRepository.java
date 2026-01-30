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



/*
Preciso adicionar para que ele busque também todas as mensaens q tenam status diferente de private messaeg
'todos'
message
AND

A => Mensagens privadas (recebi ou enviei)
B => Mensagens minhas ()
C => Mensagens que enviei
A AND (B OR C)
 */
@AllArgsConstructor
@ApplicationScoped
public class MessageRepository implements PanacheRepository<Message> {

    public void saveMessageDTOtoDB(LoggedInMessageParticipantDTO loggedInMessageParticipantDTO){
        Message message = Message.builder()
                .enviou(loggedInMessageParticipantDTO.getFrom())
                .recebeu(loggedInMessageParticipantDTO.getTo())
                .texto(loggedInMessageParticipantDTO.getText())
                .tipo(loggedInMessageParticipantDTO.getType())
                .tempo(loggedInMessageParticipantDTO.getTime())
                .build();
        this.persist(message);

    }

    public List<Message> fetchAllMesagesToUserLimit(String username,String limit){
        try{
            int limitInt = Integer.parseInt(limit);

            if(limitInt <= 0)
                throw new IllegalArgumentException("Valor não suportado para busca");
            return find(
                    "WHERE tipo <> 'private_message' OR (tipo = 'private_message' AND (recebeu = ?1 or enviou = ?1)) LIMIT ?2 "
                    ,username,limitInt).stream().toList();
        }catch (NumberFormatException e){
            throw new NumberFormatException();
        }
    }
    public List<Message> fetchAllMesagesToUser(String username){
        return find(
                "WHERE tipo <> 'private_message' OR (tipo = 'private_message' AND (recebeu = ?1 or enviou = ?1))",
                username).stream().toList();
    }
    /*
    public List<Message> fetchAllMessagesToUserLimit(String username,long limit_number){
        return find(
                "WHERE (to = 'Todos'"+
                        "OR from =?1 "+
                        "OR (to =?1 AND type = 'private_message'))"+
                        "AND type <> 'status'"+
                        "LIMIT =?2",
                username,limit_number).stream().toList();
    }

     */
}
