package gabia.demo.Service;

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

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(signInReqData.getId(),
                signInReqData.getPassword());
        System.out.println(3);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        System.out.println(4);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(5);
        String token = jwtTokenProvider.createToken(authentication);
        // 삭제된 유저 추후 예외처리 구현 시 추가


        System.out.println(6);

        User loginUser = userRepository.findByIdAndIsDelete(signInReqData.getId(), false)
                .orElseThrow(()->new RuntimeException());
        loginUser.checkPassword(passwordEncoder, passwordEncoder.encode(signInReqData.getPassword()));

        return UserDto.SignInRes.builder().userIdx(loginUser.getUserIdx()).name(loginUser.getName())
                .id(loginUser.getId()).token(token).build();
    }

}
