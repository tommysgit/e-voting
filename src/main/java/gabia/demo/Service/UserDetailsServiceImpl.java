package gabia.demo.Service;

import gabia.demo.Common.CustomException;
import gabia.demo.Common.ErrorCode;
import gabia.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;

import org.springframework.stereotype.Service;
import gabia.demo.Domain.User;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findByIdAndIsDelete(id, false)
                .map(this::createSpringSecurityUser)
                .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_EXISTS));
    }

    private UserDetails createSpringSecurityUser(User user){
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getId()).password(user.getPassword()).authorities(user.getRole().toString()).build();
    }
}
