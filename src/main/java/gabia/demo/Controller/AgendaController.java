package gabia.demo.Controller;

import gabia.demo.Common.BaseResponse;
import gabia.demo.Dto.AgendaDto;
import gabia.demo.Service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Agenda")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;
    @Operation(description = "안건 목록 조회", summary = "안건 목록 조회")
    @GetMapping()
    public BaseResponse<List<AgendaDto.SelectAgendaData>> getAgendaList(){
        List<AgendaDto.SelectAgendaData> selectAgendaDataList = agendaService.selectAgenda();
        return BaseResponse.Success(selectAgendaDataList);
    }
}
