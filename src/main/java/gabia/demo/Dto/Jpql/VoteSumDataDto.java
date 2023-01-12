package gabia.demo.Dto.Jpql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static gabia.demo.Dto.VotingDto.VOTE_LIMIT;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteSumDataDto {

        private Long agendaIdx;

        private int voteSum;

        public boolean isFull(){
                return VOTE_LIMIT <= voteSum;
        }
}
