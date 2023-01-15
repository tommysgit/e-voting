package gabia.demo.Domain;

import gabia.demo.Domain.Enums.VotingSort;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@DynamicUpdate
public class AgendaVoting {
    @Id
    private Long agendaIdx;

    @OneToOne
    @MapsId
    @JoinColumn(name = "agendaIdx")
    private Agenda agenda;

    @Column
    @Enumerated(EnumType.STRING)
    private VotingSort votingSort;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    public void endVoting(){
        this.endTime = LocalDateTime.now();
    }
}
