package bate.papo.uol.entidade;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.utils.Time;

import java.time.LocalTime;


@ApplicationScoped
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private long message_id;
    @Column(name = "\"from\"", nullable = false)
    private String from;
    @Column(name = "\"to\"",nullable = false)
    private String to;
    @Column(name = "\"text\"",nullable = false)
    private String text;
    @Column(name = "\"type\"",nullable = false)
    private String type;
    @Column(name = "\"time\"",nullable = false)
    private LocalTime time;
}
