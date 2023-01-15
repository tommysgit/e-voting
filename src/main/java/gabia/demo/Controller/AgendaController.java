package gabia.demo.Controller;

import gabia.demo.Common.BaseResponse;
import gabia.demo.Dto.AgendaDto;
import gabia.demo.Dto.AgendaVotingDto;
import gabia.demo.Dto.VotingDto;
import gabia.demo.Dto.VotingResult.NormalVotingResultDto;
import gabia.demo.Service.AgendaService;
import gabia.demo.Service.AgendaVotingService;
import gabia.demo.Service.VotingService;
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

    private final VotingService votingService;

    private final AgendaVotingService agendaVotingService;
    @Operation(description = "안건 목록 조회", summary = "안건 목록 조회")
    @GetMapping()
    public BaseResponse<List<AgendaDto.AgendaListReq>> getAgendaList(){
        List<AgendaDto.AgendaListReq> agendaListReqList = agendaService.selectAgenda();
        return BaseResponse.Success(agendaListReqList);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "안건 등록", summary = "안건 등록")
    @PostMapping()
    public BaseResponse postAgenda(@AuthenticationPrincipal User userInfo, @RequestBody Map<String, String> content){
        agendaService.createAgenda(content.get("content"));

        return BaseResponse.Success();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "안건 삭제", summary = "안건 삭제")
    @DeleteMapping("/{agendaIdx}")
    public BaseResponse deleteAgenda(@PathVariable("agendaIdx") Long agendaIdx){
        agendaService.deleteAgenda(agendaIdx);

        return BaseResponse.Success();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "안건 투표 등록", summary = "안건 투표 등록")
    @PostMapping("/{agendaIdx}/agendaVoting")
    public BaseResponse postAgendaVoting(@PathVariable("agendaIdx") Long agendaIdx, @RequestBody AgendaVotingDto.CreateAgendaVotingReq createAgendaVotingReq){
        agendaVotingService.createAgendaVoting(agendaIdx, createAgendaVotingReq);
        return BaseResponse.Success();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "안건 투표 종료", summary = "안건 투표 종료")
    @PostMapping("/{agendaIdx}/voting/end")
    public BaseResponse deleteAgendaVoting(@PathVariable("agendaIdx") Long agendaIdx){
        agendaVotingService.endVoting(agendaIdx);

        return BaseResponse.Success();
    }
    @PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    @Operation(description = "안건 투표 결과 조회", summary = "안건 투표 결과 조회")
    @GetMapping("{agendaIdx}/voting")
    public BaseResponse<NormalVotingResultDto> getVotingResult(@AuthenticationPrincipal User userInfo, @PathVariable("agendaIdx") Long agendaIdx){
        NormalVotingResultDto normalVotingResultDto = votingService.getVotingResult(userInfo, agendaIdx);
        return BaseResponse.Success(normalVotingResultDto);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @Operation(description = "안건 투표하기", summary = "안건 투표하기")
    @PostMapping("{agendaIdx}/voting")
    public BaseResponse postVote(@AuthenticationPrincipal User userInfo, @PathVariable("agendaIdx") Long agendaIdx,
                                                 @RequestBody VotingDto.VoteReq voteReq){
        votingService.vote(userInfo, agendaIdx, voteReq);
        return BaseResponse.Success();
    }

}
