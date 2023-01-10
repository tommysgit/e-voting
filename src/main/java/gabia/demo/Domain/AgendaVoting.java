package gabia.demo.Domain;

import gabia.demo.Domain.Enums.VotingSort;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class AgendaVoting {
    @Id
    private Long agendaId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;

    @Column
    @Enumerated(EnumType.STRING)
    private VotingSort votingSort;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;
}
