package gabia.demo.Controller;

import gabia.demo.Common.BaseResponse;
import gabia.demo.Dto.UserDto;
import gabia.demo.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 API")
public class UserController {
    private final UserService userService;
    @Operation(description = "회원가입하는 기능입니다.", summary = "회원가입")
    @PostMapping("/sign-up")
    public ResponseEntity<BaseResponse> postSignUp(@RequestBody UserDto.SignUpReq signUpReq){
        userService.singUp(signUpReq);
        return ResponseEntity.ok(BaseResponse.Success());
    }
    @Operation(description = "로그인하는 기능입니다.", summary = "로그인")
    @PostMapping("/sign-in")
    public ResponseEntity<BaseResponse<UserDto.SignInRes>> postSignIn(@RequestBody UserDto.SignInReq signInReq){
        UserDto.SignInRes signInRes = userService.signIn(signInReq);
        return ResponseEntity.ok(BaseResponse.Success(signInRes));
    }
}
