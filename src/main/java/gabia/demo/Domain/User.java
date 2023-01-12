package gabia.demo.Domain;

import gabia.demo.Domain.Enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;

    @Column
    private boolean isDelete;

    @Column
    private int votingRightsCount;

    @Column
    private String name;

    @Column
    private String id;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public boolean checkAdmin(){
        return this.role.equals(Role.ROLE_ADMIN);
    }

    public void checkPassword(PasswordEncoder passwordEncoder, String encodedPassword){
        if(passwordEncoder.matches(encodedPassword, this.password)){
            throw new RuntimeException();
        }
    }

    public String getRole(){
        return this.role.getDescription();
    }
}
