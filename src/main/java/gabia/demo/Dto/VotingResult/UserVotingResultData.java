package gabia.demo.Dto.VotingResult;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserVotingResultData {
    int agreeSum;
    int disagreeSum;
    int abstentionSum;
}
