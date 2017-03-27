package swt6.spring.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.spring.domain.Employee;
import swt6.spring.domain.Issue;
import swt6.spring.domain.IssueState;
import swt6.spring.domain.Project;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findAllByAssignee(Employee assignee);
    List<Issue> findAllByProject(Project project);
    List<Issue> findAllByProjectAndState(Project project, IssueState state);
    List<Issue> findAllByState(IssueState state);
}
