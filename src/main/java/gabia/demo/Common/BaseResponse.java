package gabia.demo.Common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Builder
public class BaseResponse<T> {
    public BaseResponse(boolean isSuccess, String message, int status) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.status = status;
    }

    private boolean isSuccess;
    private String message;
    private  int status;
    private T data;

    public static BaseResponse Success(){
        return BaseResponse.builder().isSuccess(true).status(HttpStatus.OK.value()).message("성공").build();
    }

    public static <T>BaseResponse Success(T data){
        return BaseResponse.builder().isSuccess(true).status(HttpStatus.OK.value()).message("성공").data(data).build();
    }

    public static BaseResponse Fail(ErrorCode errorCode){
        return BaseResponse.builder().isSuccess(false).status(errorCode.getHttpStatus().value()).message(errorCode.getMessage()).build();
    }
}