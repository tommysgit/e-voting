package gabia.demo.Domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicUpdate
public class Agenda extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agendaIdx;

    @Column
    private boolean isDelete;


    @OneToOne(mappedBy = "agenda", cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private AgendaVoting agendaVoting;

    @OneToOne(mappedBy = "agenda", cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private VotingResult votingResult;

    @Column
    private String content;

    public void deleteAgenda(){
        this.isDelete = true;
    }

    public void setAgendaVoting(AgendaVoting agendaVoting){
        this.agendaVoting = agendaVoting;
    }

    public void setVotingResult(VotingResult votingResult){this.votingResult = votingResult;}
}
