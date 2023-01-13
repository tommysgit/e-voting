package gabia.demo.Domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicUpdate
public class Agenda extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agendaIdx;

    @Column
    private boolean isDelete;


    @OneToOne(mappedBy = "agenda", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private AgendaVoting agendaVoting;

    @Column
    private String content;

    public void deleteAgenda(){
        this.isDelete = true;
    }

    public void setAgendaVoting(AgendaVoting agendaVoting){
        this.agendaVoting = agendaVoting;
    }
}
