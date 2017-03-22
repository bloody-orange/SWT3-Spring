package swt6.spring.ui;


import swt6.spring.domain.Issue;
import swt6.spring.domain.IssueState;
import swt6.spring.domain.LogbookEntry;
import swt6.spring.domain.Project;

// Interface that should be used by rich clients. Implementations
// of this interface can hold the state of the user interface.
// All event handlers should invoke methods of UIProcessFacade 
// if they access business logic.
public interface UIProcessFacade {
    void displayAllEmployees();

    void createProject(Project project);
    void displayAllProjects();
    void displayEmployeesByProject(Long projId);
    void displayWorktimesByProject(Long projId);
    void addProjectMember(Long projId, Long emplId);
    void removeProjectMember(Long projId, Long emplId);
    void addProjectEntry(Long projId, Long entryId);
    void removeProjectEntry(Long projId, Long entryId);

    void createIssue(Issue issue);
    void displayIssuesByProject(Long projId);
    void displayIssuesByProjectAndState(Long projId, IssueState state);
    void displayIssuesByProjectGroupedByEmployee(Long projId);
    void displayIssuesByProjectAndStateGroupedByEmployee(Long projId, IssueState state);
    void updateIssue(Long issueId);

    void createEntry(LogbookEntry entry);
    void displayAllEntries();
}
