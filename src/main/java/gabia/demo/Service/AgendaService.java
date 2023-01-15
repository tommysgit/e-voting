package gabia.demo.Service;

import gabia.demo.Common.CustomException;
import gabia.demo.Common.ErrorCode;
import gabia.demo.Domain.Agenda;
import gabia.demo.Dto.AgendaDto;
import gabia.demo.Repository.AgendaRepository;
import gabia.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class AgendaService {
    private final AgendaRepository agendaRepository;
    private final UserRepository userRepository;

    public void createAgenda(String content){

        Agenda agenda = Agenda.builder().isDelete(false).content(content).build();
        agendaRepository.save(agenda);
    }

    @Transactional
    public void deleteAgenda(Long agendaIdx){
        Agenda agenda = agendaRepository.findById(agendaIdx).orElseThrow(()->new CustomException(ErrorCode.AGENDA_NOT_EXISTS));
        agenda.deleteAgenda();
    }

    @Transactional(readOnly = true)
    public List<AgendaDto.AgendaListReq> selectAgenda(){
        List<Agenda> agendaList = agendaRepository.findFetchAgendaList();
        List<AgendaDto.AgendaListReq> agendaListReqList = new LinkedList<>();
        agendaList.forEach(agenda -> {
            agendaListReqList.add(AgendaDto.AgendaListReq.builder().agenda(agenda).build());
        });

        return agendaListReqList;
    }

}
