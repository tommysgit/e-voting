package gabia.demo.Common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_EXISTS("해당 유저가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    AGENDA_NOT_EXISTS("해당 안건이 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    VOTING_NOT_END( "아직 투표가 끝나지 않았습니다.", HttpStatus.BAD_REQUEST),
    VOTING_IMPOSSIBLE( "투표 가능한 시간이 아닙니다.", HttpStatus.BAD_REQUEST),
    USER_ALREADY_VOTE("해당 유저는 이미 투표를 하였습니다.", HttpStatus.BAD_REQUEST),
    VOTING_RIGHTS_EXCEED("투표 가능한 의결권을 초과하였습니다.", HttpStatus.BAD_REQUEST);




    private final String message;
    private final HttpStatus httpStatus;
}
