package bate.papo.uol.DTO.Request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor


public class UpdateParticipantDTO {
    private Long id;
    private String name;
    private long status;
}
