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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agendas")
@RequiredArgsConstructor
@Tag(name = "Agenda", description = "안건 관련 API")
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

    @PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    @Operation(description = "안건 투표 결과 조회", summary = "안건 투표 결과 조회")
    @GetMapping("{agendaIdx}/votes")
    public BaseResponse<NormalVotingResultDto> getVotingResult(@AuthenticationPrincipal User userInfo, @PathVariable("agendaIdx") Long agendaIdx){
        NormalVotingResultDto normalVotingResultDto = votingService.getVotingResult(userInfo, agendaIdx);
        return BaseResponse.Success(normalVotingResultDto);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @Operation(description = "안건 투표하기", summary = "안건 투표하기")
    @PostMapping("{agendaIdx}/votes")
    public BaseResponse postVote(@AuthenticationPrincipal UserDetails userInfo, @PathVariable("agendaIdx") Long agendaIdx,
                                 @RequestBody VotingDto.VoteReq voteReq){
        votingService.vote(userInfo.getUsername(), agendaIdx, voteReq);
        return BaseResponse.Success();
    }

}
