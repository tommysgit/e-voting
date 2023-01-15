package gabia.demo.Service.VotingSystem;

import gabia.demo.Dto.VotingDto;
import org.springframework.transaction.annotation.Transactional;

public interface VotingSystem {
     void vote(VotingDto.VoteData voteData);
}
