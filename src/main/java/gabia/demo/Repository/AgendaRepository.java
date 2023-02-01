package gabia.demo.Repository;

import gabia.demo.Domain.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    @Query("select a from Agenda a join fetch a.agendaVoting")
    List<Agenda> findFetchAgendaList();

    @Query("select a from Agenda a join fetch a.agendaVoting where a.agendaIdx =:agendaIdx")
    Optional<Agenda> findFetchAgendaVotingByIdx(@Param("agendaIdx") Long agendaIdx);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Agenda a join fetch a.agendaVoting join fetch  a.votingResult where a.agendaIdx =:agendaIdx")
    Optional<Agenda> findAgendaInfoByIdx(@Param("agendaIdx") Long agendaIdx);
}
