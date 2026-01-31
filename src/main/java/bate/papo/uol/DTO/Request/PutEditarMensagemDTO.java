package bate.papo.uol.DTO.Request;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ApplicationScoped
public class PutEditarMensagemDTO {
    private String to;
    private String text;
    private String type;
}
