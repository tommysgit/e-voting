package gabia.demo.Service.VotingResult;

import gabia.demo.Domain.Enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VotingResultFactory {
    private final AdminVotingResult adminVotingResult;
    private final UserVotingResult userVotingResult;

    public VotingResult getVotingResult(Role role){
        switch (role){
            case ROLE_ADMIN:
                return adminVotingResult;
            default:
                return userVotingResult;
        }
    }
}
