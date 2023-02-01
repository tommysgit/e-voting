package gabia.demo.Repository;

import gabia.demo.Domain.Agenda;
import gabia.demo.Domain.VotingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface VotingResultRepository extends JpaRepository<VotingResult, Long> {


}
