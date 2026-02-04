package bate.papo.uol.repository;

import bate.papo.uol.DTO.Request.LoggedInMessageParticipantDTO;
import bate.papo.uol.DTO.Request.PutEditarMensagemDTO;
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

A => Mensagens privadas (recebi ou enviei) => type = private_message
B => Mensagens minhas () => m.to = "Humberto"
C => Mensagens que enviei => m.from = "Humberto"
A AND (B OR C)

D => Mensagens públicas (type <> private_message)

D OR (A AND (B OR C))
 */
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
        try{
            int limitInt = Integer.parseInt(limit);

            if(limitInt <= 0)
                throw new IllegalArgumentException("Valor não suportado para busca");
            return find(
                    "WHERE type <> 'private_message' OR (type = 'private_message' AND (to = ?1 or from = ?1)) LIMIT ?2 "
                    ,username,limitInt).stream().toList();
        }catch (NumberFormatException e){
            throw new NumberFormatException();
        }
    }
    public List<Message> fetchAllMesagesToUser(String username){
        return find(
                "WHERE type <> 'private_message' OR (type = 'private_message' AND (to = ?1 or from = ?1))",
                username).stream().toList();
    }
    public Message listarMensagemComID(long idMensagem){
        return find("WHERE message_id = ?1",idMensagem).firstResult();
    }
}
