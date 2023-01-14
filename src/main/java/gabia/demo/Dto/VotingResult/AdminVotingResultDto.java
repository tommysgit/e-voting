package gabia.demo.Dto.VotingResult;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.Enums.VotingSort;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class AdminVotingResultDto extends NormalVotingResultDto {


    AdminVotingResultData adminVotingResultData;

    @Builder
    AdminVotingResultDto(Agenda agenda, AdminVotingResultData adminVotingResultData) {
        super(agenda);
        this.adminVotingResultData = adminVotingResultData;
    }
}
