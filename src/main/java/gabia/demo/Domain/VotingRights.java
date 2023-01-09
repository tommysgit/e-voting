package gabia.demo.Domain;

import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;

@Entity
public class VotingRights {
    @Id
    private Long agenda_id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;

    @Column
    private int count;
}
