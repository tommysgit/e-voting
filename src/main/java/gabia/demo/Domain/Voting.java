package gabia.demo.Domain;

import gabia.demo.Domain.Enums.Vote;
import gabia.demo.Domain.Enums.VotingSort;
import gabia.demo.Dto.Jpql.VoteSumDataDto;
import gabia.demo.Dto.VotingDto;
import lombok.*;

import javax.persistence.*;
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Voting extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long votingIdx;

    @Enumerated(EnumType.STRING)
    private Vote vote;

    @Column
    private int votingRightsCount;


    @ManyToOne
    @JoinColumn(name = "agendaIdx")
    private Agenda agenda;
    @ManyToOne
    @JoinColumn(name = "userIdx")
    private User user;

//    public VoteSumDataDto toVoteSumDataDto(){
//
//    }
}
