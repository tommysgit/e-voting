package gabia.demo.Service;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.AgendaVoting;
import gabia.demo.Domain.Enums.Role;
import gabia.demo.Domain.Enums.Vote;
import gabia.demo.Domain.Enums.VotingSort;
import gabia.demo.Domain.User;
import gabia.demo.Domain.Voting;
import gabia.demo.Dto.VotingDto;
import gabia.demo.Repository.AgendaRepository;
import gabia.demo.Repository.AgendaVotingRepository;
import gabia.demo.Repository.UserRepository;
import gabia.demo.Repository.VotingRepository;
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
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
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
    private LimitVoting limitVoting;

    @Autowired
    private NonLimitVoting nonLimitVoting;

    private Agenda agenda;

    private AgendaVoting agendaVoting;
    @BeforeEach
    void setUp(){
        LocalDateTime now = LocalDateTime.now();

        agenda = Agenda.builder().content("졸업식을 갈지말지에 대해...").build();
        agendaVoting = AgendaVoting.builder().agenda(agenda)
                .startTime(now).endTime(now.plusMinutes(1)).build();
        agenda.setAgendaVoting(agendaVoting);
        agendaVotingRepository.save(agendaVoting);

    }
//    @AfterEach
//    void deleteTestDate(){
//        votingRepository.deleteAll();
//        userRepository.deleteAll();
//        agendaRepository.deleteAll();
//    }

    @Test()
    @DisplayName("선착순 투표 동시성 테스트")
    void testLimitVoting(){


        // IntStream 멀티 스레드 병렬실행
        // executorService 스레드 생성 및 작업할당 포크조인 방식
        // countDownLatch 메인 스레드가 먼저 실행되는 것을 방지

        // given, when
        int userCount = 20;
        IntStream.range(0,userCount).parallel().forEach(e->{
            try{
                User user = User.builder().role(Role.ROLE_MEMBER).
                        votingRightsCount(userCount).name("홍길동 분신술").build();
                userRepository.save(user);

                VotingDto.VoteReq voteReq = VotingDto.VoteReq.builder().votingRightsCount(1).vote(Vote.AGREE).build();
                VotingDto.VoteData voteData = VotingDto.VoteData.builder().voteReq(voteReq).user(user).agenda(agenda).build();

                limitVoting.vote(voteData);
            }catch (RuntimeException ex){
                System.out.println(ex);
            }
        });

        // then
        Assertions.assertThat(limitVoting.getVotingSum(agenda)).isEqualTo(VOTING_LIMIT);
    }
    @Test()
    @DisplayName("비 선착순 의결권 투표")
    void testNonLimitVoting(){
        // given
        int agreeCount = 100;
        int disagreeCount = 50;
        int abstentionCount = 10;
        int loopCount = 10;

        // when
        for (int i = 0; i < loopCount; i++) {
            User user1 = User.builder().role(Role.ROLE_MEMBER).
                    votingRightsCount(100).name("홍길동 분신술").build();
            User user2 = User.builder().role(Role.ROLE_MEMBER).
                    votingRightsCount(100).name("홍길동 분신술").build();
            User user3 = User.builder().role(Role.ROLE_MEMBER).
                    votingRightsCount(100).name("홍길동 분신술").build();

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            VotingDto.VoteReq voteReq1 = VotingDto.VoteReq.builder().votingRightsCount(agreeCount).vote(Vote.AGREE).build();
            VotingDto.VoteReq voteReq2 = VotingDto.VoteReq.builder().votingRightsCount(disagreeCount).vote(Vote.DISAGREE).build();
            VotingDto.VoteReq voteReq3 = VotingDto.VoteReq.builder().votingRightsCount(abstentionCount).vote(Vote.ABSTENTION).build();

            VotingDto.VoteData voteData1 = VotingDto.VoteData.builder().voteReq(voteReq1).user(user1).agenda(agenda).build();
            VotingDto.VoteData voteData2 = VotingDto.VoteData.builder().voteReq(voteReq2).user(user2).agenda(agenda).build();
            VotingDto.VoteData voteData3 = VotingDto.VoteData.builder().voteReq(voteReq3).user(user3).agenda(agenda).build();

            nonLimitVoting.vote(voteData1);
            nonLimitVoting.vote(voteData2);
            nonLimitVoting.vote(voteData3);
        }

        // then 찬성, 반대, 기권 순으로 표가 잘 들어갔는지 확인
        List<Voting> votingList = votingRepository.findFetchUserByAgenda(agenda);

        Assertions.assertThat(userVotingResult.countVotingSum(votingList, Vote.AGREE)).isEqualTo(loopCount*agreeCount);
        Assertions.assertThat(userVotingResult.countVotingSum(votingList, Vote.DISAGREE)).isEqualTo(loopCount*disagreeCount);
        Assertions.assertThat(userVotingResult.countVotingSum(votingList, Vote.ABSTENTION)).isEqualTo(loopCount*abstentionCount);
    }
}
