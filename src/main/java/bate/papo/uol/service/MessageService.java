package bate.papo.uol.service;


import bate.papo.uol.DTO.Request.SendMessagePeersDTO;
import bate.papo.uol.DTO.Request.SendMessagePeersWithFromDTO;
import bate.papo.uol.entidade.Message;
import bate.papo.uol.repository.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalTime;

@AllArgsConstructor
@Builder
@Path("/messages")
@ApplicationScoped
public class MessageService {
    final MessageRepository messageRepository;

    public void addMessageToDB(SendMessagePeersDTO sendMessagePeersDTO,String fromUser){

        SendMessagePeersWithFromDTO dataToDB = SendMessagePeersWithFromDTO.builder()
                .from(fromUser)
                .to(sendMessagePeersDTO.getTo())
                .text(sendMessagePeersDTO.getText())
                .type(sendMessagePeersDTO.getType())
                .build();


        LocalTime horaAtual = LocalTime.now();


        Message message = Message.builder()
                .from(dataToDB.getFrom())
                .to(dataToDB.getTo())
                .text(dataToDB.getText())
                .type(dataToDB.getType())
                .time(horaAtual)
                .build();
        messageRepository.persist(message);

    }

}
