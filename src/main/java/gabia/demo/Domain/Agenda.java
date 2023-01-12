package gabia.demo.Domain;

import lombok.*;

import javax.persistence.*;


@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Agenda extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agendaIdx;

    @Column
    private boolean isDelete;


    @OneToOne(mappedBy = "agenda")
    @PrimaryKeyJoinColumn
    private AgendaVoting agendaVoting;

    @Column
    private String content;
}
