package gabia.demo.Domain;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Agenda extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long agenda_id;

    @Column
    private char status;

    @Column
    private char is_delete;

    @Column
    private char sort;

    @Column
    private LocalDateTime start_time;

    @Column
    private LocalDateTime end_time;

    @OneToOne(mappedBy = "agenda")
    @PrimaryKeyJoinColumn
    private VotingRights votingRights;
}
