package gabia.demo.Service;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.Enums.Role;
import gabia.demo.Domain.Enums.Vote;
import gabia.demo.Domain.User;
import gabia.demo.Dto.VotingDto;
import gabia.demo.Repository.AgendaRepository;
import gabia.demo.Repository.UserRepository;
import gabia.demo.Repository.VotingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

@SpringBootTest
@ActiveProfiles("test")
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


    @AfterEach
    void deleteTestDate(){
        votingRepository.deleteAll();
        userRepository.deleteAll();
        agendaRepository.deleteAll();
    }

    @Test()
    @DisplayName("선착순 투표 동시성 테스트")
    void testLimitVoting(){

        // given
        int userCount = 20;

        User user = User.builder().role(Role.ROLE_MEMBER).
                votingRightsCount(userCount).name("홍길동1").build();
        Agenda agenda = Agenda.builder().content("졸업식을 갈지말지에 대해...").build();

        userRepository.save(user);
        agendaRepository.save(agenda);
        VotingDto.VoteReq voteReq = VotingDto.VoteReq.builder().votingRightsCount(1).vote(Vote.AGREE).build();
        VotingDto.VoteData voteData = VotingDto.VoteData.builder().voteReq(voteReq).user(user).agenda(agenda).build();

        // IntStream 멀티 스레드 병렬실행
        // executorService 스레드 생성 및 작업할당 포크조인 방식
        // countDownLatch 메인 스레드가 먼저 실행되는 것을 방지

        // when
        IntStream.range(0,userCount).parallel().forEach(e->{
            try{
                votingService.limitVote(voteData);
            }catch (RuntimeException ex){
                System.out.println(ex);
            }
        });

        // then
        Assertions.assertThat(votingService.voteSum(agenda)).isEqualTo(VOTING_LIMIT);
    }
}
