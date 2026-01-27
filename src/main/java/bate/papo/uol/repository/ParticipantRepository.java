package bate.papo.uol.repository;

import bate.papo.uol.DTO.Request.CreateNewParticipantDTORequest;
import bate.papo.uol.DTO.Request.UpdateParticipantDTO;
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
    public void addParticipant(Participant participant){
        this.persist(participant);
        this.flush();
    }
    public List<Participant> findAllParticipants(){
        return findAll().stream().toList();
    }

    public void updateParticipant(UpdateParticipantDTO dto){
        update("name =?1, lastStatus =?2, where id=?3",dto.getName(),dto.getStatus(),dto.getId());
    }

}
