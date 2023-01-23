package gabia.demo.Domain;

import gabia.demo.Domain.Enums.Vote;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@NoArgsConstructor
public class VotingResult {

    @Id
    private Long agendaIdx;

    @OneToOne()
    @MapsId
    @JoinColumn(name = "agendaIdx")
    private Agenda agenda;

    @Column
    private int agree;

    @Column
    private int disagree;

    @Column
    private int abstention;


    @Builder
    public VotingResult(Agenda agenda){
        this.agenda = agenda;
    }


    public int getVotingSum(){
        return this.agree + this.disagree + this.abstention;
    }

    public void plusVotingCount(Vote vote, int votingCount){
        if (vote.equals(Vote.AGREE)){
            this.agree = this.agree + votingCount;
        } else if (vote.equals(Vote.DISAGREE)) {
            this.disagree = this.disagree + votingCount;
        }else {
            this.abstention = this.abstention + votingCount;
        }
    }
}
