package gabia.demo.Service;

import gabia.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;

import org.springframework.stereotype.Service;
import gabia.demo.Domain.User;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findByIdAndIsDelete(id, false)
                .map(this::createSpringSecurityUser)
                .orElseThrow(()-> new RuntimeException());
    }

    private UserDetails createSpringSecurityUser(User user){
        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getDescription()));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getId()).password(user.getPassword()).authorities(grantedAuthorities).build();
    }
}
