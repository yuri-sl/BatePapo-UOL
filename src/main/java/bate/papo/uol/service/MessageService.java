package bate.papo.uol.service;


import bate.papo.uol.DTO.Request.SendMessagePeersDTO;
import bate.papo.uol.DTO.Request.SendMessagePeersWithFromDTO;
import bate.papo.uol.entidade.Message;
import bate.papo.uol.entidade.Participant;
import bate.papo.uol.repository.MessageRepository;
import bate.papo.uol.repository.ParticipantRepository;
import bate.papo.uol.util.enums.MessageTypes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;

import static io.smallrye.config._private.ConfigLogging.log;

@AllArgsConstructor
@Builder
@ApplicationScoped
public class MessageService {
    final MessageRepository messageRepository;
    final ParticipantRepository participantRepository;

    public void validarCamposMensagemDTO(SendMessagePeersWithFromDTO dados){
        if(dados.getText().isBlank() || dados.getTo().isBlank())
            throw new IllegalArgumentException("Os campos de texto e destinatário não pode ser nulo");
        String tipoMensagem = dados.getType();

        MessageTypes tipo;
        try{
            tipo = MessageTypes.from(tipoMensagem);
            log.infof(tipo.toString());
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Tipo inválido de mensagem");
        }

        List<Participant> verificarUsuarioExiste = participantRepository.returnParticipantsWithSameName(dados.getFrom());
        if(verificarUsuarioExiste.isEmpty())
            throw new IllegalArgumentException("O usuário não existe no sistema");
    }
    @Transactional
    public void addMessageToDB(SendMessagePeersDTO sendMessagePeersDTO,String fromUser){
            SendMessagePeersWithFromDTO dataToDB = SendMessagePeersWithFromDTO.builder()
                    .from(fromUser)
                    .to(sendMessagePeersDTO.getTo())
                    .text(sendMessagePeersDTO.getText())
                    .type(sendMessagePeersDTO.getType())
                    .build();
            validarCamposMensagemDTO(dataToDB);

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
