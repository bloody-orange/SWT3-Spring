package swt6.spring.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.spring.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
