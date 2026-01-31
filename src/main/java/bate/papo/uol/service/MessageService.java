package bate.papo.uol.service;


import bate.papo.uol.DTO.Request.PutEditarMensagemDTO;
import bate.papo.uol.DTO.Request.SendMessagePeersDTO;
import bate.papo.uol.DTO.Request.SendMessagePeersWithFromDTO;
import bate.papo.uol.entidade.Message;
import bate.papo.uol.entidade.Participant;
import bate.papo.uol.repository.MessageRepository;
import bate.papo.uol.repository.ParticipantRepository;
import bate.papo.uol.util.enums.MessageTypes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.jboss.resteasy.reactive.RestResponse;

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
                    .enviou(dataToDB.getFrom())
                    .recebeu(dataToDB.getTo())
                    .texto(dataToDB.getText())
                    .tipo(dataToDB.getType())
                    .tempo(horaAtual)
                    .build();
            messageRepository.persist(message);


    }
    /*
    public List<Message> getALlMessagesFromUser(String username, String limit){
        try{
            List<Message> listaMensagens = messageRepository.fetchAllMesagesToUserLimit(username, limit);
            return listaMensagens;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    };
    */
    public List<Message> getAllMessagesFromUser(String username, String limit){
        try{
            List<Participant> verificarUsuarioExiste = participantRepository.returnParticipantsWithSameName(username);
            if(verificarUsuarioExiste.isEmpty())
                throw new IllegalArgumentException("O usuário não existe no sistema");

            if (limit == null){
                List<Message> listaMensagens = messageRepository.fetchAllMesagesToUser(username);
                for(Message m : listaMensagens){
                    log.info(m);
                }
                return listaMensagens;
            }else{
                List<Message> listaMensagens = messageRepository.fetchAllMesagesToUserLimit(username,limit);
                for(Message m : listaMensagens){
                    log.info(m);
                }
                return listaMensagens;
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("O limite passado não é numérico");
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    };
    public void verificarMensagemExisteEDonoLegitimo(Message mensagem,String username){
        if(mensagem == null){
            throw new RuntimeException("Mensagem não existe no sistema");
        }
        final String donoMensagem = mensagem.getEnviou();
        if(!donoMensagem.equals(username)){
            throw new RuntimeException("Você não é o dono da mensagem!");
        }
    }

    @Transactional
    public void deletarMensagemDadoIDUsuario(long id_mensagem, String username){
        Message mensagemBuscada = messageRepository.listarMensagemComID(id_mensagem);
        verificarMensagemExisteEDonoLegitimo(mensagemBuscada,username);
        messageRepository.deleteById(id_mensagem);
    }

    @Transactional
    public void editarMensagemComID(long idMensagem, PutEditarMensagemDTO putEditarMensagemDTO,String username){
        Message mensagemBuscada = messageRepository.listarMensagemComID(idMensagem);
        verificarMensagemExisteEDonoLegitimo(mensagemBuscada,username);
        mensagemBuscada.setTexto(putEditarMensagemDTO.getText());
        mensagemBuscada.setTipo(putEditarMensagemDTO.getType());
        mensagemBuscada.setRecebeu(putEditarMensagemDTO.getTo());
    }

}
