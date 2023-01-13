package gabia.demo.Service;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.AgendaVoting;
import gabia.demo.Domain.Enums.Role;
import gabia.demo.Domain.Enums.Vote;
import gabia.demo.Domain.Enums.VotingSort;
import gabia.demo.Domain.User;
import gabia.demo.Dto.VotingDto;
import gabia.demo.Repository.AgendaRepository;
import gabia.demo.Repository.AgendaVotingRepository;
import gabia.demo.Repository.UserRepository;
import gabia.demo.Repository.VotingRepository;
import gabia.demo.Service.VotingSystem.LimitVoting;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
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
    private LimitVoting limitVoting;

    private Agenda agenda;

    private AgendaVoting agendaVoting;
    @BeforeEach
    void setUp(){
        LocalDateTime now = LocalDateTime.now();

        agenda = Agenda.builder().content("졸업식을 갈지말지에 대해...").build();
        agendaVoting = AgendaVoting.builder().votingSort(VotingSort.LIMIT).agenda(agenda)
                .startTime(now).endTime(now.plusMinutes(1)).build();
        agenda.setAgendaVoting(agendaVoting);
        agendaVotingRepository.save(agendaVoting);

    }
    @AfterEach
    void deleteTestDate(){
        votingRepository.deleteAll();
        userRepository.deleteAll();
        agendaRepository.deleteAll();
    }

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

}
