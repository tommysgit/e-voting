package gabia.demo.Service.VotingSystem;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.User;
import gabia.demo.Domain.Voting;
import gabia.demo.Dto.Jpql.VoteSumDataDto;
import gabia.demo.Dto.VotingDto;
import gabia.demo.Repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LimitVoting extends VotingTemplate {


    public LimitVoting(VotingRepository votingRepository) {
        super(votingRepository);
    }

    @Override
    public void vote(VotingDto.VoteData voteData) {
        super.vote(voteData);
        int countSum = this.getVotingSum(voteData.getAgenda());
        VoteSumDataDto voteSumDataDto = VoteSumDataDto.builder().voteSum(countSum)
                .agendaIdx(voteData.getAgenda().getAgendaIdx()).build();


        votingRepository.save(voteData.limitToEntity(voteSumDataDto));
    }

    @Transactional
    public int getVotingSum(Agenda agenda){
        return votingRepository.findByAgenda(agenda).parallelStream().
                map(Voting::getVotingRightsCount).reduce(Integer::sum).orElse(0);
    }
}
