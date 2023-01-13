package gabia.demo.Service.VotingSystem;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.User;
import gabia.demo.Dto.VotingDto;
import gabia.demo.Repository.VotingRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NonLimitVoting extends VotingTemplate{
    public NonLimitVoting(VotingRepository votingRepository) {
        super(votingRepository);
    }

    @Override
    public void vote(VotingDto.VoteData voteData) {
        super.vote(voteData);
        votingRepository.save(voteData.toEntity());
    }
}
