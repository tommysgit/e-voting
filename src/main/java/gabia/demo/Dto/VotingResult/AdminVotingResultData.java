package gabia.demo.Dto.VotingResult;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AdminVotingResultData {
    VotingSumData agreeVotingSumData;

    @Builder
    public AdminVotingResultData(VotingSumData agreeVotingSumData, VotingSumData disagreeVotingSumData, VotingSumData abstentionVotingSumData) {
        this.agreeVotingSumData = agreeVotingSumData;
        this.disagreeVotingSumData = disagreeVotingSumData;
        this.abstentionVotingSumData = abstentionVotingSumData;
    }

    VotingSumData disagreeVotingSumData;
    VotingSumData abstentionVotingSumData;

}
