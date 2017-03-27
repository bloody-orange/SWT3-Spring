package swt6.spring.bl;

import swt6.spring.domain.Issue;
import swt6.spring.domain.IssueState;
import swt6.spring.domain.Project;

import java.util.List;

public interface IssueManager extends BaseManager<Issue, Long>  {
    List<Issue> findByProject(Project project);
    List<Issue> findByState(IssueState state);
    List<Issue> findByProjectAndState(Project project, IssueState state);
}
