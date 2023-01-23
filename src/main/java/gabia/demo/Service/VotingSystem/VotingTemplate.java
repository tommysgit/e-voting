package gabia.demo.Service.VotingSystem;

import gabia.demo.Common.CustomException;
import gabia.demo.Common.ErrorCode;
import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.AgendaVoting;
import gabia.demo.Domain.User;
import gabia.demo.Dto.VotingDto;
import gabia.demo.Repository.AgendaRepository;
import gabia.demo.Repository.VotingRepository;
import gabia.demo.Repository.VotingResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Slf4j
@Component
@RequiredArgsConstructor
public class VotingTemplate implements VotingSystem {

    protected final VotingRepository votingRepository;
    protected final VotingResultRepository votingResultRepository;

    protected final AgendaRepository agendaRepository;

     public void vote(VotingDto.VoteData voteData){
         validateVoting(voteData);
     }

    protected void validateVoting(VotingDto.VoteData voteData){
        validateVotingTime(voteData.getAgenda().getAgendaVoting());
        validateVotingDuplication(voteData.getUser(), voteData.getAgenda());
        validateVotingCount(voteData.getUser(), voteData.getVotingRightsCount());
    }

    private void validateVotingTime(AgendaVoting agendaVoting){
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (currentDateTime.isAfter(agendaVoting.getEndTime()) && currentDateTime.isBefore(agendaVoting.getStartTime())){
            throw new CustomException(ErrorCode.VOTING_IMPOSSIBLE);
        }
    }

    public void validateVotingDuplication(User user, Agenda agenda){
        if (votingRepository.findByAgendaAndUser(agenda, user).isPresent()){
            throw new CustomException(ErrorCode.USER_ALREADY_VOTE);
        }
    }

    private void validateVotingCount(User user, int votingCount){
        if (user.getVotingRightsCount() < votingCount){
            throw new CustomException(ErrorCode.VOTING_RIGHTS_EXCEED);
        }
    }
}
