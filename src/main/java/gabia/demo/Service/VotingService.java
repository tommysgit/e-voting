package gabia.demo.Service;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.Enums.VotingSort;
import gabia.demo.Domain.Voting;
import gabia.demo.Dto.Jpql.VoteSumDataDto;
import gabia.demo.Dto.VotingDto;
import gabia.demo.Repository.AgendaRepository;
import gabia.demo.Repository.UserRepository;
import gabia.demo.Repository.VotingRepository;
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

    private final int VOTE_LIMIT = 10;

    // 투표하기  안건 투표 관련 정보 조회 -> 투표 가능한 시간인지 확인 후 불가능한 시간이면 예외처리
    // 투표 방식에 따른 로직처리
    @Transactional
    public void vote(User userInfo, Long agendaIdx, VotingDto.VoteReq voteReq){
        LocalDateTime currentDateTime = LocalDateTime.now();
        gabia.demo.Domain.User user = userRepository.findByIdAndIsDelete(userInfo.getUsername(), false).get();

        Agenda agenda = agendaRepository.findFetchAgendaVotingByIdx(agendaIdx).get();

        // 투표 가능한 시간인지 확인
        if ((currentDateTime.isAfter(agenda.getAgendaVoting().getEndTime()) && currentDateTime.isBefore(agenda.getAgendaVoting().getStartTime()))){
            throw new RuntimeException();
        }

        // 이미 투표한 사람인지 확인
        if (votingRepository.findByAgendaAndUser(agenda, user).isPresent()){
            throw new RuntimeException();
        }

        VotingDto.VoteData voteData = VotingDto.VoteData.builder().user(user).voteReq(voteReq).agenda(agenda).build();

        if (agenda.getAgendaVoting().getVotingSort().equals(VotingSort.LIMIT)){
            limitVote(voteData);
        }else {
            nonLimitVote(voteData);
        }

    }

    // 선착순
    // 여분의 의결권 조회 -> 조회 결과가 null인 경우 처리 -> 참여 가능한 의결권 개수보다 작으면 write, 크다면 개수만큼 write
    @Transactional
    public void limitVote(VotingDto.VoteData voteData){

        int countSum = this.voteSum(voteData.getAgenda());
        VoteSumDataDto voteSumDataDto = VoteSumDataDto.builder().voteSum(countSum)
                        .agendaIdx(voteData.getAgenda().getAgendaIdx()).build();


        votingRepository.save(voteData.limitToEntity(voteSumDataDto));
    }

    // 의결권
    @Transactional
    public void nonLimitVote(VotingDto.VoteData voteData){
        votingRepository.save(voteData.nonLimitToEntity());
    }

    @Transactional
    public int voteSum(Agenda agenda){
        return votingRepository.findByAgenda(agenda).parallelStream().
                map(Voting::getVotingRightsCount).reduce(Integer::sum).orElse(0);
    }
}
