package gabia.demo.Controller;

import gabia.demo.Common.BaseResponse;
import gabia.demo.Dto.AgendaDto;
import gabia.demo.Service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Agendas")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;
    @Operation(description = "안건 목록 조회", summary = "안건 목록 조회")
    @GetMapping()
    public BaseResponse<List<AgendaDto.AgendaListReq>> getAgendaList(){
        List<AgendaDto.AgendaListReq> agendaListReqList = agendaService.selectAgenda();
        return BaseResponse.Success(agendaListReqList);
    }


}
