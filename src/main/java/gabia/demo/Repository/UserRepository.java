package gabia.demo.Repository;

import gabia.demo.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndIsDelete(String id, boolean isDelete);

}
