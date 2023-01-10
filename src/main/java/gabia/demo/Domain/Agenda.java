package gabia.demo.Domain;

import gabia.demo.Domain.Enums.DeleteStatus;
import lombok.*;

import javax.persistence.*;
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Agenda extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long agendaId;

    @Column
    private char status;

    @Column
    @Enumerated(EnumType.STRING)
    private DeleteStatus isDelete;


    @OneToOne(mappedBy = "agenda")
    @PrimaryKeyJoinColumn
    private AgendaVoting agendaVoting;

    @Column
    private String content;
}
