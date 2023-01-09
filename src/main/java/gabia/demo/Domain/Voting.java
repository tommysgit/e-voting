package gabia.demo.Domain;

import javax.persistence.*;

@Entity
public class Voting extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long voting_id;

    @Column
    private char status;

    @Column
    private int voting_rights_count;


    @ManyToOne
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
