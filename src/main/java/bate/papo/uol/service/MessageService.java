package bate.papo.uol.service;


import bate.papo.uol.repository.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Path("/messages")
@ApplicationScoped
public class MessageService {
    final MessageRepository messageRepository;

}
