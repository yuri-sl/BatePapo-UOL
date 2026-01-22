package bate.papo.uol.DTO.Response;


import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApplicationScoped
public class CreateNewParticipantDTOResponse {
    private String name;
    private long lastStatus;
}
