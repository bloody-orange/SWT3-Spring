package swt6.spring.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.spring.domain.Employee;
import swt6.spring.domain.Issue;
import swt6.spring.domain.LogbookEntry;
import swt6.spring.domain.Project;

import java.util.List;

public interface LogbookEntryRepository extends JpaRepository<LogbookEntry, Long> {
    List<LogbookEntry> findAllByEmployeeAndIssue(Employee employee, Issue issue);
    List<LogbookEntry> findAllByEmployeeAndProject(Employee employee, Project project);
}
