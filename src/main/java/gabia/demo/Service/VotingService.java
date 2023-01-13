package gabia.demo.Service;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.Enums.VotingSort;
import gabia.demo.Domain.Voting;
import gabia.demo.Dto.Jpql.VoteSumDataDto;
import gabia.demo.Dto.VotingDto;
import gabia.demo.Repository.AgendaRepository;
import gabia.demo.Repository.UserRepository;
import gabia.demo.Repository.VotingRepository;
import gabia.demo.Service.VotingSystem.LimitVoting;
import gabia.demo.Service.VotingSystem.VotingSystem;
import gabia.demo.Service.VotingSystem.VotingSystemFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class VotingService {
    private final VotingRepository votingRepository;
    private final UserRepository userRepository;

    private final AgendaRepository agendaRepository;

    private final VotingSystemFactory votingSystemFactory;

    private final int VOTE_LIMIT = 10;

    // 투표하기  안건 투표 관련 정보 조회 -> 투표 가능한 시간인지 확인 후 불가능한 시간이면 예외처리
    // 투표 방식에 따른 로직처리
    @Transactional
    public void vote(User userInfo, Long agendaIdx, VotingDto.VoteReq voteReq){
        gabia.demo.Domain.User user = userRepository.findByIdAndIsDelete(userInfo.getUsername(), false).get();

        Agenda agenda = agendaRepository.findFetchAgendaVotingByIdx(agendaIdx).get();

        VotingDto.VoteData voteData = VotingDto.VoteData.builder().user(user).voteReq(voteReq).agenda(agenda).build();

        VotingSystem votingSystem = votingSystemFactory.getVotingSystem(agenda.getAgendaVoting().getVotingSort());

        votingSystem.vote(voteData);

    }


}