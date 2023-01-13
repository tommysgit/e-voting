package gabia.demo.Service.VotingSystem;

import gabia.demo.Domain.Enums.VotingSort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VotingSystemFactory {
    private final LimitVoting limitVoting;
    private final NonLimitVoting nonLimitVoting;

    public VotingSystem getVotingSystem(VotingSort votingSort){
        switch (votingSort){
            case LIMIT:
                return limitVoting;
            default:
                return nonLimitVoting;
        }
    }
}
