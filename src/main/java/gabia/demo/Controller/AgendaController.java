package gabia.demo.Controller;

import gabia.demo.Common.BaseResponse;
import gabia.demo.Dto.AgendaDto;
import gabia.demo.Service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Agendas")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;
    @Operation(description = "안건 목록 조회", summary = "안건 목록 조회")
    @GetMapping()
    public ResponseEntity<BaseResponse<List<AgendaDto.AgendaListReq>>> getAgendaList(){
        List<AgendaDto.AgendaListReq> agendaListReqList = agendaService.selectAgenda();
        return ResponseEntity.ok(BaseResponse.Success(agendaListReqList));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "안건 등록", summary = "안건 등록")
    @PostMapping()
    public ResponseEntity<BaseResponse> postAgenda(@AuthenticationPrincipal User userInfo, @RequestBody Map<String, String> content){
        agendaService.createAgenda(content.get("content"));

        return ResponseEntity.ok(BaseResponse.Success());
    }
}
