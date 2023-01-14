package gabia.demo.Dto.VotingResult;

import gabia.demo.Domain.Enums.Vote;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VotingData {
    Long userIdx;
    int userVotingRights;
    String id;
    String name;
    Vote vote;
    int votingRightsCount;
}
