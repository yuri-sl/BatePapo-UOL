package bate.papo.uol.repository;

import bate.papo.uol.entidade.Participant;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
@ApplicationScoped
public class ParticipantRepository implements PanacheRepository<Participant> {
    public List<Participant> returnParticipantsWithSameName(String nameRequested){
        return find("where name = ?1",nameRequested).stream().toList();

    }

}
