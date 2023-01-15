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
import java.util.stream.Collectors;

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
        List<AgendaDto.AgendaListReq> agendaListReqList = agendaList.stream().filter(v->v.isDelete()==false)
                .map(this::toAgendaListReq).collect(Collectors.toList());

        return agendaListReqList;
    }

    private AgendaDto.AgendaListReq toAgendaListReq(Agenda agenda){
        return AgendaDto.AgendaListReq.builder().agenda(agenda).build();
    }
}
