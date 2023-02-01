package gabia.demo.Service.VotingSystem;

import gabia.demo.Common.CustomException;
import gabia.demo.Common.ErrorCode;
import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.Voting;
import gabia.demo.Domain.VotingResult;
import gabia.demo.Dto.Jpql.VoteSumDataDto;
import gabia.demo.Dto.VotingDto;
import gabia.demo.Repository.AgendaRepository;
import gabia.demo.Repository.VotingRepository;
import gabia.demo.Repository.VotingResultRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static gabia.demo.Dto.VotingDto.VOTE_LIMIT;

@Component
public class LimitVoting extends VotingTemplate {


    public LimitVoting(VotingRepository votingRepository, VotingResultRepository votingResultRepository, AgendaRepository agendaRepository) {
        super(votingRepository, votingResultRepository, agendaRepository);
    }

    @Override
    @Transactional
    public void vote(VotingDto.VoteData voteData) {
        super.vote(voteData);

        int countSum = voteData.getAgenda().getVotingResult().getVotingSum();

        System.out.println("현재 투표 합계는 " + countSum);


        Voting voting = Voting.builder().vote(voteData.getVote()).agenda(voteData.getAgenda()).user(voteData.getUser())
                        .votingRightsCount(voteData.getVotingRightsCount()).build();

        voting.setLimitVoting(countSum);
        voteData.getAgenda().getVotingResult().plusVotingCount(voteData.getVote(), voting.getVotingRightsCount());
        votingRepository.save(voting);
        if (voteData.getAgenda().getVotingResult().getVotingSum() >= VOTE_LIMIT){
            voteData.getAgenda().getAgendaVoting().endVoting();
        }
    }


}
