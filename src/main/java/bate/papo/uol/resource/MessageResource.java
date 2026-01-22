package bate.papo.uol.resource;


import bate.papo.uol.repository.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class MessageResource {
    final MessageRepository messageRepository;


}
