package gabia.demo.Service;

import gabia.demo.Domain.Agenda;
import gabia.demo.Dto.AgendaDto;
import gabia.demo.Repository.AgendaRepository;
import gabia.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaService {
    private final AgendaRepository agendaRepository;
    private final UserRepository userRepository;

    // 안건등록
    public void createAgenda(String content){

        Agenda agenda = Agenda.builder().isDelete(false).content(content).build();
        agendaRepository.save(agenda);
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
