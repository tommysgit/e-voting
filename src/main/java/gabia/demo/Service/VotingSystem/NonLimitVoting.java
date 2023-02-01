package gabia.demo.Service.VotingSystem;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.User;
import gabia.demo.Dto.VotingDto;
import gabia.demo.Repository.AgendaRepository;
import gabia.demo.Repository.VotingRepository;
import gabia.demo.Repository.VotingResultRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NonLimitVoting extends VotingTemplate{


    public NonLimitVoting(VotingRepository votingRepository, VotingResultRepository votingResultRepository, AgendaRepository agendaRepository) {
        super(votingRepository, votingResultRepository, agendaRepository);
    }

    @Override
    @Transactional
    public void vote(VotingDto.VoteData voteData) {
        super.vote(voteData);
        votingRepository.save(voteData.toEntity());
    }
}
