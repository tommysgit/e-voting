package gabia.demo.Repository;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.User;
import gabia.demo.Domain.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface VotingRepository extends JpaRepository<Voting, Long> {



    Optional<Voting> findByAgendaAndUser(Agenda agenda, User user);

    List<Voting> findByAgenda(Agenda agenda);
}
