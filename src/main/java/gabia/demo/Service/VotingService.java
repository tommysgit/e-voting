package gabia.demo.Service;

import gabia.demo.Common.CustomException;
import gabia.demo.Common.ErrorCode;
import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.Enums.VotingSort;
import gabia.demo.Domain.Voting;
import gabia.demo.Dto.Jpql.VoteSumDataDto;
import gabia.demo.Dto.VotingDto;
import gabia.demo.Dto.VotingResult.NormalVotingResultDto;
import gabia.demo.Repository.AgendaRepository;
import gabia.demo.Repository.UserRepository;
import gabia.demo.Repository.VotingRepository;
import gabia.demo.Service.VotingResult.VotingResult;
import gabia.demo.Service.VotingResult.VotingResultFactory;
import gabia.demo.Service.VotingSystem.LimitVoting;
import gabia.demo.Service.VotingSystem.VotingSystem;
import gabia.demo.Service.VotingSystem.VotingSystemFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class VotingService {
    private final UserRepository userRepository;

    private final AgendaRepository agendaRepository;

    private final VotingSystemFactory votingSystemFactory;

    private final VotingResultFactory votingResultFactory;

    private final int VOTE_LIMIT = 10;

    // 투표하기  안건 투표 관련 정보 조회 -> 투표 가능한 시간인지 확인 후 불가능한 시간이면 예외처리
    // 투표 방식에 따른 로직처리
    @Transactional
    public void vote(String userId, Long agendaIdx, VotingDto.VoteReq voteReq){
        gabia.demo.Domain.User user = userRepository.findByIdAndIsDelete(userId, false)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_EXISTS));

        Agenda agenda = agendaRepository.findAgendaInfoByIdx(agendaIdx).orElseThrow(()->new CustomException(ErrorCode.AGENDA_NOT_EXISTS));

        VotingDto.VoteData voteData = VotingDto.VoteData.builder().user(user).voteReq(voteReq).agenda(agenda).build();

        VotingSystem votingSystem = votingSystemFactory.getVotingSystem(agenda.getAgendaVoting().getVotingSort());

        votingSystem.vote(voteData);

    }


    @Transactional(readOnly = true)
    public NormalVotingResultDto getVotingResult(User userInfo, Long agendaIdx){
        gabia.demo.Domain.User user = userRepository.findByIdAndIsDelete(userInfo.getUsername(), false).get();
        Agenda agenda = agendaRepository.findFetchAgendaVotingByIdx(agendaIdx).orElseThrow(()->new CustomException(ErrorCode.AGENDA_NOT_EXISTS));
        VotingResult votingResult = votingResultFactory.getVotingResult(user.getRole());
        NormalVotingResultDto normalVotingResultDto = votingResult.checkResult(agenda);
        return normalVotingResultDto;
    }
}
