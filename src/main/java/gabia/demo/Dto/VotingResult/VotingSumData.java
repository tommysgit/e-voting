package gabia.demo.Dto.VotingResult;

import gabia.demo.Domain.Enums.Vote;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class VotingSumData {
    int votingSum;
    List<VotingData> voteList;

}
