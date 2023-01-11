package gabia.demo.Repository;

import gabia.demo.Domain.Agenda;
import gabia.demo.Dto.AgendaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    @Query("select a from Agenda a join fetch a.agendaVoting")
    List<Agenda> findAllAgendas();
}
