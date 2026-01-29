package bate.papo.uol.entidade;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.utils.Time;

import java.time.LocalTime;


@ApplicationScoped
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private long message_id;
    @Column(name = "\"from\"", nullable = false)
    private String enviou;
    @Column(name = "\"to\"",nullable = false)
    private String recebeu;
    @Column(name = "\"text\"",nullable = false)
    private String texto;
    @Column(name = "\"type\"",nullable = false)
    private String tipo;
    @Column(name = "\"time\"",nullable = false)
    private LocalTime tempo;
}
