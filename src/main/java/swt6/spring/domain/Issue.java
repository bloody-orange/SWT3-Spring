package swt6.spring.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Issue implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private IssueState state;

    @Column(nullable = false)
    private IssuePriority priority;

    @Column
    private Integer estimatedMinutes;

    @Column
    private Integer percentageDone;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Employee assignee;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Fetch(FetchMode.JOIN)
    private Project project;

    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<LogbookEntry> entries;

    public Issue() {
    }

    public Issue(String description, Project project, IssueState state, IssuePriority priority, Integer estimatedMinutes, Integer percentageDone) {
        this.description = description;
        this.project = project;
        this.percentageDone = percentageDone;
        this.state = state;
        this.priority = priority;
        this.estimatedMinutes = estimatedMinutes;
    }

    public Set<LogbookEntry> getEntries() {
        return entries;
    }

    public void setEntries(Set<LogbookEntry> entries) {
        this.entries = entries;
    }

    public Long getId() {
        return id;
    }

    @Override
    public void removeDependencies() {
        while (this.getEntries().size() > 0) {
            this.removeLogbookEntry(this.getEntries().iterator().next());
        }
    }

    public void addLogbookEntry(LogbookEntry entry) {
        if (entry == null) {
            throw new IllegalStateException("LogbookEntry was null");
        }

        if (entry.getIssue() != null) {
            entry.getIssue().getEntries().remove(entry);
        }

        this.entries.add(entry);
        entry.setIssue(this);
    }

    public void removeLogbookEntry(LogbookEntry entry) {
        if (entry == null) {
            throw new IllegalStateException("LogbookEntry was null");
        }
        if (entry.getIssue() == this) {
            entry.setIssue(null);
            this.entries.remove(entry);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getAssignee() {
        return assignee;
    }

    public void setAssignee(Employee assignee) {
        this.assignee = assignee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IssueState getState() {
        return state;
    }

    public void setState(IssueState state) {
        this.state = state;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public Integer getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public void setEstimatedMinutes(Integer estimatedMinutes) {
        this.estimatedMinutes = estimatedMinutes;
    }

    public Integer getPercentageDone() {
        return percentageDone;
    }

    public void setPercentageDone(Integer percentageDone) {
        if (percentageDone > 100 || percentageDone < 0) {
            throw new IllegalArgumentException("percentage out of bounds");
        }
        this.percentageDone = percentageDone;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Issue #" + id + ": '" + description + "'. ";
        if (assignee != null) {
            s += "Assignee: " + assignee.getFirstName() + " " + assignee.getLastName() + ". ";
        }
        s += "Estimated time left: " + estimatedMinutes + " minutes. " +
                "Percentage done: " + percentageDone + "%. " +
                "State: " + state + ". Priority: " + priority;

        return s;
    }
}
