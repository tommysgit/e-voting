package gabia.demo.Dto.VotingResult;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.Enums.VotingSort;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Getter
public class UserVotingResultDto extends NormalVotingResultDto{

    int agreeSum;
    int disagreeSum;
    int abstentionSum;

    UserVotingResultData userVotingResultData;
    @Builder
    public UserVotingResultDto(Agenda agenda, UserVotingResultData userVotingResultData) {
        super(agenda);
        this.userVotingResultData = userVotingResultData;

    }
}
