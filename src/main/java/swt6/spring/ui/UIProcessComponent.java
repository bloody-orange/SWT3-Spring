package swt6.spring.ui;

import org.springframework.beans.factory.annotation.Autowired;
import swt6.spring.domain.Employee;
import swt6.spring.bl.*;
import swt6.spring.domain.Issue;
import swt6.spring.domain.IssueState;
import swt6.spring.domain.LogbookEntry;
import swt6.spring.domain.Project;

import javax.inject.Inject;
import javax.inject.Named;

public class UIProcessComponent implements UIProcessFacade {

    private AddressManager addressManager;
    private EmployeeManager employeeManager;
    private IssueManager issueManager;
    private LogbookEntryManager entryManager;
    private PhaseManager phaseManager;
    private ProjectManager projectManager;

    public UIProcessComponent(AddressManager addressManager, EmployeeManager employeeManager,
                              IssueManager issueManager, LogbookEntryManager entryManager,
                              PhaseManager phaseManager, ProjectManager projectManager) {
        this.addressManager = addressManager;
        this.employeeManager = employeeManager;
        this.issueManager = issueManager;
        this.entryManager = entryManager;
        this.phaseManager = phaseManager;
        this.projectManager = projectManager;
    }

    @Override
    public void displayAllEmployees() {
        for (Employee empl: employeeManager.findAll()) {
            System.out.println(empl.toString());
        }
    }

    @Override
    public void createProject(Project project) {
        projectManager.save(project);
    }

    @Override
    public void displayAllProjects() {
        for (Project proj: projectManager.findAll()) {
            System.out.println(proj);
        }
    }

    @Override
    public void displayEmployeesByProject(Long projId) {

    }

    @Override
    public void displayWorktimesByProject(Long projId) {

    }

    @Override
    public void addProjectMember(Long projId, Long emplId) {

    }

    @Override
    public void removeProjectMember(Long projId, Long emplId) {

    }

    @Override
    public void addProjectEntry(Long projId, Long entryId) {

    }

    @Override
    public void removeProjectEntry(Long projId, Long entryId) {

    }

    @Override
    public void createIssue(Issue issue) {

    }

    @Override
    public void displayIssuesByProject(Long projId) {

    }

    @Override
    public void displayIssuesByProjectAndState(Long projId, IssueState state) {

    }

    @Override
    public void displayIssuesByProjectGroupedByEmployee(Long projId) {

    }

    @Override
    public void displayIssuesByProjectAndStateGroupedByEmployee(Long projId, IssueState state) {

    }

    @Override
    public void updateIssue(Long issueId) {

    }

    @Override
    public void createEntry(LogbookEntry entry) {

    }

    @Override
    public void displayAllEntries() {

    }
}
