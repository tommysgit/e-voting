package gabia.demo.Service.VotingSystem;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.AgendaVoting;
import gabia.demo.Domain.User;
import gabia.demo.Dto.VotingDto;
import gabia.demo.Repository.VotingRepository;
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

    @Transactional
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
            log.info("투표가능한 시간이 아닙니다.");
            throw new RuntimeException();
        }
    }

    @Transactional
    public void validateVotingDuplication(User user, Agenda agenda){
        if (votingRepository.findByAgendaAndUser(agenda, user).isPresent()){
            log.info("해당 유저는 이미 투표를 하였습니다.");
            throw new RuntimeException();
        }
    }

    private void validateVotingCount(User user, int votingCount){
        if (user.getVotingRightsCount() < votingCount){
            log.info("투표가능한 의결권을 초과하였습니다.");
            throw new RuntimeException();
        }
    }
}
