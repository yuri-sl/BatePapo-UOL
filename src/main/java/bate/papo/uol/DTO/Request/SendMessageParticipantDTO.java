package bate.papo.uol.DTO.Request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SendMessageParticipantDTO {
    private String from;
    private String to;
    private String text;
    private String type;
    private LocalTime time;
}
