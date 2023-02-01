package gabia.demo.Domain;

import gabia.demo.Common.CustomException;
import gabia.demo.Common.ErrorCode;
import gabia.demo.Domain.Enums.Vote;
import gabia.demo.Domain.Enums.VotingSort;
import gabia.demo.Dto.Jpql.VoteSumDataDto;
import gabia.demo.Dto.VotingDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

import static gabia.demo.Dto.VotingDto.VOTE_LIMIT;
@Slf4j
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


    public void setLimitVoting(int countSum){
        if (VOTE_LIMIT <= countSum){
            log.info("선착순 투표가 마감되었습니다.");
            throw new CustomException(ErrorCode.VOTING_RIGHTS_EXCEED);
        }else if (this.votingRightsCount > (VOTE_LIMIT - countSum)){
            log.info("사용 가능한 의결권이 제한되어 " + this.votingRightsCount + " 만큼만 반영됩니다.");
            this.votingRightsCount = (VOTE_LIMIT - countSum);
        }
    }
}
