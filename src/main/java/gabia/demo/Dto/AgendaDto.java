package gabia.demo.Dto;

import gabia.demo.Domain.Enums.VotingSort;
import lombok.Builder;

import java.time.LocalDateTime;

public class AgendaDto {
    @Builder
    public static class SelectAgendaData{
        private Long agendaId;

        private String content;

        private LocalDateTime startTime;

        private LocalDateTime endTime;

        private VotingSort votingSort;

        private char proceeding;

        public void setProceeding(){
            if (LocalDateTime.now().isAfter(this.startTime) && LocalDateTime.now().isBefore(endTime)){
                this.proceeding = 'N';
            }
            else{
                this.proceeding = 'Y';
            }
        }
    }
}
