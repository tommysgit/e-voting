package gabia.demo.Service.VotingResult;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.Enums.Vote;
import gabia.demo.Domain.Voting;
import gabia.demo.Dto.VotingResult.*;
import gabia.demo.Repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminVotingResult implements VotingResult{
    private final VotingRepository votingRepository;
    @Override
    @Transactional(readOnly = true)
    public NormalVotingResultDto checkResult(Agenda agenda) {
        List<Voting> votingList = votingRepository.findFetchUserByAgenda(agenda);
        AdminVotingResultData adminVotingResultData = calculationAdminVotingResult(votingList);
        return AdminVotingResultDto.builder().agenda(agenda).adminVotingResultData(adminVotingResultData)
                .build();
    }

    private AdminVotingResultData calculationAdminVotingResult(List<Voting> votingList){
        List<VotingData> agreeVotingDataList = new LinkedList<>();
        List<VotingData> disagreeVotingDataList = new LinkedList<>();
        List<VotingData> abstentionVotingDataList = new LinkedList<>();
        int agreeSum = 0;
        int disagreeSum = 0;
        int abstentionSum = 0;
        for (Voting voting:votingList
             ) {
            switch (voting.getVote()){
                case AGREE:
                    agreeSum += voting.getVotingRightsCount();
                    agreeVotingDataList.add(VotingData.builder()
                            .userIdx(voting.getUser().getUserIdx()).userVotingRights(voting.getUser().getVotingRightsCount())
                            .id(voting.getUser().getId()).name(voting.getUser().getName()).vote(voting.getVote()).votingRightsCount(voting.getVotingRightsCount())
                            .build());
                    break;
                case DISAGREE:
                    disagreeSum += voting.getVotingRightsCount();
                    disagreeVotingDataList.add(VotingData.builder()
                            .userIdx(voting.getUser().getUserIdx()).userVotingRights(voting.getUser().getVotingRightsCount())
                            .id(voting.getUser().getId()).name(voting.getUser().getName()).vote(voting.getVote()).votingRightsCount(voting.getVotingRightsCount())
                            .build());
                    break;
                case ABSTENTION:
                    abstentionSum += voting.getVotingRightsCount();
                    abstentionVotingDataList.add(VotingData.builder()
                            .userIdx(voting.getUser().getUserIdx()).userVotingRights(voting.getUser().getVotingRightsCount())
                            .id(voting.getUser().getId()).name(voting.getUser().getName()).vote(voting.getVote()).votingRightsCount(voting.getVotingRightsCount())
                            .build());
                    break;
            }

        }
        return AdminVotingResultData.builder()
                .agreeVotingSumData(VotingSumData.builder().votingSum(agreeSum).voteList(agreeVotingDataList).build())
                .disagreeVotingSumData(VotingSumData.builder().votingSum(disagreeSum).voteList(disagreeVotingDataList).build())
                .abstentionVotingSumData(VotingSumData.builder().votingSum(abstentionSum).voteList(abstentionVotingDataList).build())
                .build();
    }

//    private VotingSumData countVotingSumData(List<Voting> votingList, Vote vote){
//
//    }
}
