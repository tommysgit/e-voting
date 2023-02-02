package gabia.demo.Service.VotingResult;

import gabia.demo.Common.CustomException;
import gabia.demo.Common.ErrorCode;
import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.Enums.Vote;
import gabia.demo.Domain.Voting;
import gabia.demo.Dto.VotingResult.NormalVotingResultDto;
import gabia.demo.Dto.VotingResult.UserVotingResultData;
import gabia.demo.Dto.VotingResult.UserVotingResultDto;
import gabia.demo.Repository.AgendaRepository;
import gabia.demo.Repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserVotingResult implements VotingResult{
    private final VotingRepository votingRepository;
    @Override
    @Transactional(readOnly = true)
    public NormalVotingResultDto checkResult(Agenda agenda) {
        validateResultTime(agenda.getAgendaVoting().getEndTime());
        List<Voting> votingList = votingRepository.findByAgenda(agenda);
        UserVotingResultData userVotingResultData = calculationVotingResult(votingList);

        return UserVotingResultDto.builder().userVotingResultData(userVotingResultData)
        .agenda(agenda).build();
    }

    private UserVotingResultData calculationVotingResult(List<Voting> votingList){
        int agreeSum = 0;
        int disagreeSum = 0;
        int abstentionSum = 0;
        for(Voting voting : votingList){
            switch (voting.getVote()){
                case AGREE:
                    agreeSum += voting.getVotingRightsCount();
                    break;
                case DISAGREE:
                    disagreeSum += voting.getVotingRightsCount();
                    break;
                case ABSTENTION:
                    abstentionSum += voting.getVotingRightsCount();
                    break;
            }
        }
        return UserVotingResultData.builder().agreeSum(agreeSum)
                .disagreeSum(disagreeSum).abstentionSum(abstentionSum).build();
    }

    public int countVotingSum(List<Voting> votingList, Vote vote){
        return  votingList.stream().parallel().filter(voting -> voting.getVote() == vote)
                .map(Voting::getVotingRightsCount).reduce(Integer::sum).orElse(0);
    }
    private void validateResultTime(LocalDateTime endTime){
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(endTime)){
            throw new CustomException(ErrorCode.VOTING_NOT_END);
        }
    }
}
