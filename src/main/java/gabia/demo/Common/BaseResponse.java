package gabia.demo.Common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
public class BaseResponse<T> {
    public BaseResponse(boolean isSuccess, String message, int code) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.code = code;
    }

    private boolean isSuccess;
    private String message;
    private  int code;
    private T data;

    public static BaseResponse Success(){
        return new BaseResponse(true, "성공", 200);
    }

    public static <T>BaseResponse Success(T data){
        return new BaseResponse(true, "성공", 200, data);
    }

    public static BaseResponse Fail(ErrorCode errorCode){
        return BaseResponse.builder().code(errorCode.getCode()).isSuccess(errorCode.isSuccess()).message(errorCode.getMessage()).build();
    }
}