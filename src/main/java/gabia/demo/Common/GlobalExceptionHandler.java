package gabia.demo.Common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public BaseResponse handleNotExistsException(CustomException e){
        log.error(e.getMessage(), e);
        return BaseResponse.Fail(e.getErrorCode());
    }


}
