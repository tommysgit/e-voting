package gabia.demo.Dto.VotingResult;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.Enums.VotingSort;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Getter
public class NormalVotingResultDto {
    Long agendaIdx;
    String content;
    VotingSort votingSort;
    LocalDateTime startTime;
    LocalDateTime endTime;
    public NormalVotingResultDto(Agenda agenda) {
        this.agendaIdx = agenda.getAgendaIdx();
        this.content = agenda.getContent();
        this.votingSort = agenda.getAgendaVoting().getVotingSort();
        this.startTime = agenda.getAgendaVoting().getStartTime();
        this.endTime = agenda.getAgendaVoting().getEndTime();
    }
}
