package gabia.demo.Controller;

import gabia.demo.Common.BaseResponse;
import gabia.demo.Dto.UserDto;
import gabia.demo.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Operation(description = "회원가입", summary = "회원가입")
    @PostMapping
    public BaseResponse postSignUp(@RequestBody UserDto.SignUpReq signUpReq){
        userService.singUp(signUpReq);
        return BaseResponse.Success();
    }

    public BaseResponse<UserDto.SignInRes> postSignIn(@RequestBody UserDto.SignInReq signInReq){
        UserDto.SignInRes signInRes = userService.signIn(signInReq);

        return BaseResponse.Success(signInRes);
    }
}
