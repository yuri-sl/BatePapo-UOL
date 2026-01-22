package bate.papo.uol.repository;

import bate.papo.uol.entidade.Message;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class MessageRepository implements PanacheRepository<Message> {
}
