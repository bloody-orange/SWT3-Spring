package swt6.spring.ui;


import swt6.spring.domain.*;

// Interface that should be used by rich clients. Implementations
// of this interface can hold the state of the user interface.
// All event handlers should invoke methods of UIProcessFacade 
// if they access business logic.
public interface UIProcessFacade {
    void displayAllEmployees();
    void addEmployeeIssue(Long emplId, Long issueId);

    void createProject(Project project, Long emplId);
    void displayAllProjects();
    void displayEmployeesByProject(Long projId);
    void displayWorktimesByProject(Long projId);
    void addProjectMember(Long projId, Long emplId);
    void addProjectIssue(Long projId, Long issueId);
    void removeProjectMember(Long projId, Long emplId);
    void addProjectEntry(Long projId, Long entryId);
    void removeProjectEntry(Long projId, Long entryId);

    void createIssue(Issue issue, Long projId, Long emplId);
    void displayAllIssues();
    void displayIssuesByProject(Long projId);
    void displayIssuesByProjectAndState(Long projId, IssueState state);
    void displayIssuesByProjectGroupedByEmployee(Long projId);
    void displayIssuesByProjectAndStateGroupedByEmployee(Long projId, IssueState state);
    void updateIssue(Long issueId, Long emplId, String description, IssueState state, IssuePriority prio);

    void displayAllPhases();

    void createEntry(LogbookEntry entry, Long phaseId, Long emplId, Long issueId, Long projId);
    void assignEntryToIssue(Long entryId, Long issueId);
    void displayAllEntries();

    boolean employeeExists(Long emplId);
    boolean projectExists(Long projId);
    boolean issueExists(Long issueId);
    boolean issueIsAssignedToProject(Long issueId);
    boolean entryExists(Long entryId);
    boolean phaseExists(Long phaseId);
}
