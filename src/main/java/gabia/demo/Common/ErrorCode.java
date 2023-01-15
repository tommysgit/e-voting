package gabia.demo.Common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_EXISTS(false, "해당 유저가 존재하지 않습니다.", 400),
    AGENDA_NOT_EXISTS(false, "해당 안건이 존재하지 않습니다.", 400),

    VOTING_NOT_END(false, "아직 투표가 끝나지 않았습니다.", 400),
    VOTING_IMPOSSIBLE(false, "투표 가능한 시간이 아닙니다.", 400),
    USER_ALREADY_VOTE(false, "해당 유저는 이미 투표를 하였습니다.", 400),
    VOTING_RIGHTS_EXCEED(false, "투표 가능한 의결권을 초과하였습니다.", 400);


    ErrorCode(boolean isSuccess, String message, int code) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.code = code;
    }

    private final boolean isSuccess;
    private final String message;
    private final int code;
}
