package gabia.demo.Service;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.Enums.DeleteStatus;
import gabia.demo.Dto.AgendaDto;
import gabia.demo.Repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaService {
    private final AgendaRepository agendaRepository;



    @Transactional
    public List<AgendaDto.SelectAgendaData> selectAgenda(){
        List<Agenda> agendaList = agendaRepository.findAllAgendas();
        List<AgendaDto.SelectAgendaData> selectAgendaDataList = new LinkedList<>();
        agendaList.forEach(agenda -> {
            AgendaDto.SelectAgendaData selectAgendaData = AgendaDto.SelectAgendaData.builder()
                    .content(agenda.getContent()).agendaId(agenda.getAgendaId())
                    .startTime(agenda.getAgendaVoting().getStartTime()).endTime(agenda.getAgendaVoting().getEndTime())
                    .votingSort(agenda.getAgendaVoting().getVotingSort()).build();
            selectAgendaData.setProceeding();
            selectAgendaDataList.add(selectAgendaData);
        });

        return selectAgendaDataList;
    }

}
