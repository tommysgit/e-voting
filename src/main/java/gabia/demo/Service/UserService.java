package gabia.demo.Service;

import gabia.demo.Common.CustomException;
import gabia.demo.Common.ErrorCode;
import gabia.demo.Config.Jwt.JwtTokenProvider;
import gabia.demo.Domain.User;
import gabia.demo.Dto.UserDto;
import gabia.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    public void singUp(UserDto.SignUpReq signUpReqData){
        String hashPassword = passwordEncoder.encode(signUpReqData.getPassword());
        userRepository.save(User.builder().id(signUpReqData.getId()).name(signUpReqData.getName())
                .role(signUpReqData.getRole()).password(hashPassword).isDelete(false).build());

    }
    // 로그인
    @Transactional(readOnly = true)
    public UserDto.SignInRes signIn(UserDto.SignInReq signInReqData){

        User loginUser = userRepository.findByIdAndIsDelete(signInReqData.getId(), false)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_EXISTS));
        loginUser.checkPassword(passwordEncoder, passwordEncoder.encode(signInReqData.getPassword()));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(signInReqData.getId(),
                signInReqData.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.createToken(authentication);

        return UserDto.SignInRes.builder().userIdx(loginUser.getUserIdx()).name(loginUser.getName())
                .id(loginUser.getId()).token(token).build();
    }

}
