package gabia.demo.Service;

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
        User loadUser = userRepository.findByIdAndIsDelete(id, false)
                .orElseThrow(()->new RuntimeException());

        return org.springframework.security.core.userdetails.User.builder().username(loadUser.getName())
                .roles(loadUser.getRole().toString())
                .build();
    }
}
