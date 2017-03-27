package swt6.spring.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.spring.bl.IssueManager;
import swt6.spring.dal.AddressRepository;
import swt6.spring.dal.IssueRepository;
import swt6.spring.dal.LogbookEntryRepository;
import swt6.spring.domain.Address;
import swt6.spring.domain.Issue;
import swt6.spring.domain.IssueState;
import swt6.spring.domain.Project;

import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Objects;

@Component
public class IssueManagerImpl extends AbstractBaseManager<Issue, Long, IssueRepository> implements IssueManager {
    private LogbookEntryRepository entryRepo;

    @Autowired
    public IssueManagerImpl(IssueRepository repo, LogbookEntryRepository entryRepo) {
        super(Issue.class, repo);
        this.entryRepo = entryRepo;
    }

    @Override
    public List<Issue> findByProject(Project project) {
        return repo.findAllByProject(project);
    }

    @Override
    public List<Issue> findByState(IssueState state) {
        return repo.findAllByState(state);
    }

    @Override
    public List<Issue> findByProjectAndState(Project project, IssueState state) {
        return repo.findAllByProjectAndState(project, state);
    }

    @Override
    public Issue save(Issue issue) {
        if (issue == null) {
            throw new IllegalArgumentException("Issue is null");
        }
        if (issue.getId() != null && issue.getAssignee() != null) {
            Issue old = findById(issue.getId());
            if (old.getAssignee() != null && !old.getAssignee().getId().equals(issue.getAssignee().getId())) {
                if (entryRepo.findAllByEmployeeAndIssue(old.getAssignee(), issue).size() > 0) {
                    throw new IllegalStateException("Assignee can't change anymore");
                }
            }
            if (issue.getEstimatedMinutes() == null) {
                throw new IllegalStateException("Issue needs a time estimate when an employee is assigned to it");
            }
        }
        return super.save(issue);
    }
}
