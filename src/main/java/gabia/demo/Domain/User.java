package gabia.demo.Domain;

import gabia.demo.Domain.Enums.Role;
import lombok.*;

import javax.persistence.*;
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

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    public boolean checkAdmin(){
        return this.role.equals(Role.ROLE_ADMIN);
    }
}
