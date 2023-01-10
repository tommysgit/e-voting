package gabia.demo.Domain;

import gabia.demo.Domain.Enums.VotingSort;

import javax.persistence.*;

@Entity
public class Voting extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long votingId;

    @Column
    @Enumerated(EnumType.STRING)
    private VotingSort votingSort;

    @Column
    private int votingRightsCount;


    @ManyToOne
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
