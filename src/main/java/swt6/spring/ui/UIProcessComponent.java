package swt6.spring.ui;


import swt6.spring.bl.*;
import swt6.spring.domain.*;
import swt6.util.PrintUtil;
import swt6.util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for (Employee e: employeeManager.findAllByProject(projId)) {
            System.out.println(e);
        }
    }

    @Override
    public void displayWorktimesByProject(Long projId) {
        Project project = projectManager.findById(projId);
        Map<Employee, Tuple<Long, Long>> timeTable = projectManager.getTimetable(projId);
        System.out.println("<< Project '" + project.getName() + "' >>");
        System.out.printf("Total work done: %d/%d minutes.%n",
                timeTable.entrySet()
                        .stream()
                        .mapToLong(entry -> entry.getValue().first)
                        .sum(),
                timeTable.entrySet()
                        .stream()
                        .mapToLong(entry -> entry.getValue().second)
                        .sum()
        );
        System.out.println();

        printColumn("<Angestellter>", 20);
        printColumn("<bisher>", 10);
        printColumn("<insg.>", 10);
        System.out.println();
        System.out.println("----------------------------------------");

        for (Map.Entry<Employee, Tuple<Long, Long>> entry: timeTable.entrySet()) {
            printColumn(entry.getKey().getLastName() + ", " +
                    entry.getKey().getFirstName().substring(0, 1) + ".", 20);
            printColumn(entry.getValue().first.toString(), 10);
            printColumn(entry.getValue().second.toString(), 10);
            System.out.println();
        }
        System.out.println();
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

    private void printColumn(String s, int width) {
        System.out.print(s);
        for(int i = 0; i < width - s.length() - 1; ++i) {
            System.out.print(" ");
        }
        System.out.print(" ");
    }
}
