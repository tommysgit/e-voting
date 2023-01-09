package gabia.demo.Domain;

import gabia.demo.Domain.enums.Role;

import javax.persistence.*;

@Entity
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @Column
    private char status;

    @Column
    private int voting_rights_count;

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
