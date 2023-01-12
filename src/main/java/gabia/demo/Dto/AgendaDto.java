package gabia.demo.Dto;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.Enums.VotingSort;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

public class AgendaDto {
    @Getter
    public static class AgendaListReq {
        @Builder
        public AgendaListReq(Agenda agenda) {
            this.agendaIdx = agenda.getAgendaIdx();
            this.content = agenda.getContent();
            this.startTime = Objects.isNull(agenda.getAgendaVoting()) ? null : agenda.getAgendaVoting().getStartTime();
            this.endTime = Objects.isNull(agenda.getAgendaVoting()) ? null : agenda.getAgendaVoting().getEndTime();
            this.votingSort = Objects.isNull(agenda.getAgendaVoting()) ? null : agenda.getAgendaVoting().getVotingSort();

            setProceeding();
        }

        private Long agendaIdx;

        private String content;

        private LocalDateTime startTime;

        private LocalDateTime endTime;

        private VotingSort votingSort;

        private char proceeding;

        private void setProceeding(){
            LocalDateTime currentDateTime = LocalDateTime.now();
            this.proceeding = (currentDateTime.isAfter(this.startTime) && currentDateTime.isBefore(this.endTime)) ?
                    'Y' : 'N';
        }
    }
}
