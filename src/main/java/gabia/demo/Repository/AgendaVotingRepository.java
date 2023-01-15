package gabia.demo.Repository;

import gabia.demo.Domain.AgendaVoting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaVotingRepository extends JpaRepository<AgendaVoting, Long> {
}
