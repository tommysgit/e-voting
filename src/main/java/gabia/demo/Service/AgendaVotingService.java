package gabia.demo.Service;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.AgendaVoting;
import gabia.demo.Dto.AgendaVotingDto;
import gabia.demo.Repository.AgendaRepository;
import gabia.demo.Repository.AgendaVotingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
@RequiredArgsConstructor
public class AgendaVotingService {
    private final AgendaVotingRepository agendaVotingRepository;

    private final AgendaRepository agendaRepository;

    // 투표 등록
    @Transactional
    public void createAgendaVoting(Long agendaIdx, AgendaVotingDto.CreateAgendaVotingReq createAgendaVotingReq){

        Agenda agenda = agendaRepository.findById(agendaIdx).orElseThrow();
        agendaVotingRepository.save(createAgendaVotingReq.toEntity(agenda));
    }
    // 투표 종료
    @Transactional
    public void endVoting(Long agendaIdx){
        AgendaVoting agendaVoting = agendaVotingRepository.findById(agendaIdx).orElseThrow();
        agendaVoting.endVoting();
    }
}
