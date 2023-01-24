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
        if (currentDateTime.isAfter(agendaVoting.getEndTime()) || currentDateTime.isBefore(agendaVoting.getStartTime())){
            log.info(" 투표 가능한 시간이 아닙니다. ");
            throw new CustomException(ErrorCode.VOTING_IMPOSSIBLE);
        }
    }

    public void validateVotingDuplication(User user, Agenda agenda){
        if (votingRepository.findByAgendaAndUser(agenda, user).isPresent()){
            log.info(" 이미 해당 안건에 투표하였습니다. ");
            throw new CustomException(ErrorCode.USER_ALREADY_VOTE);
        }
    }

    private void validateVotingCount(User user, int votingCount){
        if (user.getVotingRightsCount() < votingCount){
            log.info(" 사용 가능한 의결권을 초과하였습니다. ");
            throw new CustomException(ErrorCode.VOTING_RIGHTS_EXCEED);
        }
    }
}
