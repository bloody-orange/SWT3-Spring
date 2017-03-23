package swt6.spring.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swt6.spring.domain.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select distinct e from Employee e inner join e.projects p where p.id = :projId")
    List<Employee> findAllByProjectId(@Param("projId") Long projId);
}