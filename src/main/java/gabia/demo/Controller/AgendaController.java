package gabia.demo.Controller;

import gabia.demo.Common.BaseResponse;
import gabia.demo.Dto.AgendaDto;
import gabia.demo.Dto.AgendaVotingDto;
import gabia.demo.Service.AgendaService;
import gabia.demo.Service.AgendaVotingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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

    private final AgendaVotingService agendaVotingService;
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

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "안건 삭제", summary = "안건 삭제")
    @DeleteMapping("/{agendaIdx}")
    public ResponseEntity<BaseResponse> deleteAgenda(@PathVariable("agendaIdx") Long agendaIdx){
        agendaService.deleteAgenda(agendaIdx);

        return ResponseEntity.ok(BaseResponse.Success());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "안건 투표 등록", summary = "안건 투표 등록")
    @PostMapping("/voting/{agendaIdx}")
    public ResponseEntity<BaseResponse> postAgendaVoting(@PathVariable("agendaIdx") Long agendaIdx, @RequestBody AgendaVotingDto.CreateAgendaVotingReq createAgendaVotingReq){
        agendaVotingService.createAgendaVoting(agendaIdx, createAgendaVotingReq);
        return ResponseEntity.ok(BaseResponse.Success());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "안건 투표 종료", summary = "안건 투표 종료")
    @PostMapping("/voting/end/{agendaIdx}")
    public ResponseEntity<BaseResponse> deleteAgendaVoting(@PathVariable("agendaIdx") Long agendaIdx){
        agendaVotingService.endVoting(agendaIdx);

        return ResponseEntity.ok(BaseResponse.Success());
    }
}
