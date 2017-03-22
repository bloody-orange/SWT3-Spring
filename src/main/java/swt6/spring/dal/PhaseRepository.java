package swt6.spring.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.spring.domain.Phase;

public interface PhaseRepository extends JpaRepository<Phase, Long> {
}
