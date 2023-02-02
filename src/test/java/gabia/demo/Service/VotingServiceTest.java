package gabia.demo.Service;

import gabia.demo.Domain.*;
import gabia.demo.Domain.Enums.Role;
import gabia.demo.Domain.Enums.Vote;
import gabia.demo.Domain.Enums.VotingSort;
import gabia.demo.Dto.VotingDto;
import gabia.demo.Repository.*;
import gabia.demo.Service.VotingResult.UserVotingResult;
import gabia.demo.Service.VotingSystem.LimitVoting;
import gabia.demo.Service.VotingSystem.NonLimitVoting;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
@SpringBootTest
public class VotingServiceTest {

    private final int VOTING_LIMIT = 10;

    @Autowired
    private VotingService votingService;

    @Autowired
    private VotingRepository votingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private AgendaVotingRepository agendaVotingRepository;

    @Autowired
    private UserVotingResult userVotingResult;

    @Autowired
    private VotingResultRepository votingResultRepository;





    @Test()
    @DisplayName("선착순 투표 동시성 테스트")
    void testLimitVoting(){


        // IntStream 멀티 스레드 병렬실행
        // executorService 스레드 생성 및 작업할당 포크조인 방식
        // countDownLatch 메인 스레드가 먼저 실행되는 것을 방지

        // given
        LocalDateTime now = LocalDateTime.now();
        int userCount = 10;
        Agenda agenda = Agenda.builder().content("졸업식을 갈지말지에 대해...").build();
        AgendaVoting agendaVoting = AgendaVoting.builder().agenda(agenda).votingSort(VotingSort.LIMIT)
                .startTime(now).endTime(now.plusMinutes(1)).build();
        VotingResult votingResult = VotingResult.builder().agenda(agenda).build();
        agenda.setAgendaVoting(agendaVoting);
        agenda.setVotingResult(votingResult);

        agendaRepository.save(agenda);


        // when
        IntStream.range(0,userCount).parallel().forEach(e->{
            try{
                User user = User.builder().role(Role.ROLE_MEMBER).id(UUID.randomUUID().toString().substring(8)).
                        votingRightsCount(userCount).name("홍길동 분신술").build();
                userRepository.save(user);

                VotingDto.VoteReq voteReq = VotingDto.VoteReq.builder().votingRightsCount(1).vote(Vote.AGREE).build();

                votingService.vote(user.getId(), agenda.getAgendaIdx(), voteReq);
            }catch (RuntimeException ex){
                System.out.println(ex);
            }
        });

        // then
        System.out.println("then 시작!!!!");
        Assertions.assertThat(votingResultRepository.findById(agenda.getAgendaIdx()).get().getVotingSum()).isEqualTo(VOTING_LIMIT);
    }
    @Test()
    @DisplayName("비 선착순 의결권 투표")
    void testNonLimitVoting(){
        // given
        int agreeCount = 100;
        int disagreeCount = 50;
        int abstentionCount = 10;
        int loopCount = 10;
        LocalDateTime now = LocalDateTime.now();
        Agenda agenda = Agenda.builder().content("졸업식을 갈지말지에 대해...").build();
        AgendaVoting agendaVoting = AgendaVoting.builder().agenda(agenda).votingSort(VotingSort.NON_LIMIT)
                .startTime(now).endTime(now.plusMinutes(1)).build();
        VotingResult votingResult = VotingResult.builder().agenda(agenda).build();
        agenda.setAgendaVoting(agendaVoting);
        agenda.setVotingResult(votingResult);

        agendaRepository.save(agenda);

        // when
        for (int i = 0; i < loopCount; i++) {
            User user1 = User.builder().role(Role.ROLE_MEMBER).id(UUID.randomUUID().toString().substring(8)).
                    votingRightsCount(100).name("홍길동 분신술").build();
            User user2 = User.builder().role(Role.ROLE_MEMBER).id(UUID.randomUUID().toString().substring(8)).
                    votingRightsCount(100).name("홍길동 분신술").build();
            User user3 = User.builder().role(Role.ROLE_MEMBER).id(UUID.randomUUID().toString().substring(8)).
                    votingRightsCount(100).name("홍길동 분신술").build();

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            VotingDto.VoteReq voteReq1 = VotingDto.VoteReq.builder().votingRightsCount(agreeCount).vote(Vote.AGREE).build();
            VotingDto.VoteReq voteReq2 = VotingDto.VoteReq.builder().votingRightsCount(disagreeCount).vote(Vote.DISAGREE).build();
            VotingDto.VoteReq voteReq3 = VotingDto.VoteReq.builder().votingRightsCount(abstentionCount).vote(Vote.ABSTENTION).build();

            votingService.vote(user1.getId(), agenda.getAgendaIdx(), voteReq1);
            votingService.vote(user2.getId(), agenda.getAgendaIdx(), voteReq2);
            votingService.vote(user3.getId(), agenda.getAgendaIdx(), voteReq3);
        }

        // then 찬성, 반대, 기권 순으로 표가 잘 들어갔는지 확인
        List<Voting> votingList = votingRepository.findByAgenda(agenda);

        Assertions.assertThat(userVotingResult.countVotingSum(votingList, Vote.AGREE)).isEqualTo(loopCount*agreeCount);
        Assertions.assertThat(userVotingResult.countVotingSum(votingList, Vote.DISAGREE)).isEqualTo(loopCount*disagreeCount);
        Assertions.assertThat(userVotingResult.countVotingSum(votingList, Vote.ABSTENTION)).isEqualTo(loopCount*abstentionCount);
    }
}
