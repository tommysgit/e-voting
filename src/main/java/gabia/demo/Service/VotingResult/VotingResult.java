package gabia.demo.Service.VotingResult;

import gabia.demo.Domain.Agenda;
import gabia.demo.Dto.VotingResult.NormalVotingResultDto;

public interface VotingResult {
    public NormalVotingResultDto checkResult(Agenda agenda);
}
