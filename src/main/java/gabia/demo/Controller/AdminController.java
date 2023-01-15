package gabia.demo.Controller;

import gabia.demo.Common.BaseResponse;
import gabia.demo.Dto.AgendaDto;
import gabia.demo.Dto.AgendaVotingDto;
import gabia.demo.Service.AgendaService;
import gabia.demo.Service.AgendaVotingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "관리자 관련 API")
public class AdminController {
    private final AgendaService agendaService;

    private final AgendaVotingService agendaVotingService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "안건 등록", summary = "안건 등록")
    @PostMapping("agendas")
    public BaseResponse postAgenda(@RequestBody AgendaDto.createAgendaReq createAgendaReq){
        agendaService.createAgenda(createAgendaReq.getContent());

        return BaseResponse.Success();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "안건 삭제", summary = "안건 삭제")
    @DeleteMapping("agendas/{agendaIdx}")
    public BaseResponse deleteAgenda(@PathVariable("agendaIdx") Long agendaIdx){
        agendaService.deleteAgenda(agendaIdx);

        return BaseResponse.Success();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "안건 투표 등록", summary = "안건 투표 등록")
    @PostMapping("agendas/{agendaIdx}/votes")
    public BaseResponse postAgendaVoting(@PathVariable("agendaIdx") Long agendaIdx, @RequestBody AgendaVotingDto.CreateAgendaVotingReq createAgendaVotingReq){
        agendaVotingService.createAgendaVoting(agendaIdx, createAgendaVotingReq);
        return BaseResponse.Success();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "안건 투표 종료", summary = "안건 투표 종료")
    @PostMapping("agendas/{agendaIdx}/votes/end")
    public BaseResponse deleteAgendaVoting(@PathVariable("agendaIdx") Long agendaIdx){
        agendaVotingService.endVoting(agendaIdx);

        return BaseResponse.Success();
    }
}
