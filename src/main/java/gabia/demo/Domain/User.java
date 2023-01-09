package gabia.demo.Domain;

import gabia.demo.Domain.Enums.Role;

import javax.persistence.*;

@Entity
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column
    private char status;

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

}
