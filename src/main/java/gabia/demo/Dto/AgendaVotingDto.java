package gabia.demo.Dto;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.AgendaVoting;
import gabia.demo.Domain.Enums.VotingSort;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class AgendaVotingDto {
    @Getter
    public static class CreateAgendaVotingReq {

        private LocalDateTime startTime;

        private LocalDateTime endTime;

        private VotingSort sort;

        public AgendaVoting toEntity(Agenda agenda){
            return AgendaVoting.builder().agenda(agenda).startTime(this.startTime)
                    .endTime(this.endTime).votingSort(this.sort).build();
        }
    }
}
