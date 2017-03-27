package swt6.spring.client;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.domain.*;
import swt6.test.DataOperations;
import swt6.spring.ui.UIProcessFacade;
import swt6.util.DbSetupUtil;

import static swt6.util.PrintUtil.*;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.*;

public class ConsoleApplication {
    private static UIProcessFacade ui;

    public static void main(String[] args) {

        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/client/applicationContext*.xml")) {
            EntityManagerFactory emFactory = factory.getBean(EntityManagerFactory.class);
            emFactory.createEntityManager();

            try {
                DbSetupUtil.getDbSetup(DataOperations.DELETE_INSERT_ALL).launch();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Can't insert test data, exiting...");
                return;
            }

            ui = factory.getBean("uiComponent", UIProcessFacade.class);

            mainMenu();
        }
    }

    private static void mainMenu() {
        printLargeTitle("WorkLog");
        printTitle("Submenus");
        int option = promptOptions("Employee", "Project", "Issue", "Logbook entry", "Exit");
        switch (option) {
            case (1):
                employeeMenu();
                break;
            case (2):
                projectMenu();
                break;
            case (3):
                issueMenu();
                break;
            case (4):
                entryMenu();
                break;
            default:
                return;
        }
    }

    private static void entryMenu() {
        printTitle("Logbook entry menu");
        int option = promptOptions("Display all", "Create new", "Assign to project", "Main menu");
        switch (option) {
            case (1):
                ui.displayAllEntries();
                break;
            case (2):
                createEntry();
                break;
            case (3):
                addProjectEntry();
                break;
            default:
                mainMenu();
                return;
        }
        entryMenu();
    }

    private static void addProjectEntry() {
        ui.displayAllEntries();
        Long entryId = getLongInput("Enter id of logbook entry to assign", 0);
        while (!ui.entryExists(entryId)) {
            entryId = getLongInput("Enter valid id of entry to assign", 0);
        }
        ui.displayAllProjects();
        Long projId = getLongInput("Enter id of project to assign to", 0);
        while (!ui.projectExists(projId)) {
            projId = getLongInput("Enter valid id of project", 0);
        }
        ui.addProjectEntry(projId, entryId);
    }

    private static void createEntry() {
        System.out.println("New entry:");
        LogbookEntry entry = new LogbookEntry();
        entry.setStartTime(new Date());
        entry.setActivity(getStringInput("Activity"));

        System.out.println("What employee do you want to assign this entry to?");
        ui.displayAllEmployees();
        Long emplId = getLongInput("Enter id of employee", 0);
        while (!ui.employeeExists(emplId)) {
            emplId = getLongInput("Enter valid id of employee", 0);
        }

        System.out.println("What phase do you want to assign this entry to?");
        ui.displayAllPhases();
        Long phaseId = getLongInput("Enter id of phase", 0);
        while (!ui.phaseExists(phaseId)) {
            phaseId = getLongInput("Enter valid id of phase", 0);
        }

        System.out.println("What issue do you want to assign this entry to? Enter -1 to skip.");
        ui.displayAllIssues();
        Long issueId = getLongInput("Enter id of issue", -10);
        while (!ui.issueExists(issueId) && issueId >= 0) {
            issueId = getLongInput("Enter valid id of issue", -10);
        }

        Long projId = null;
        if (issueId < 0 || !ui.issueIsAssignedToProject(issueId)) {
            System.out.println("Entry needs to be assigned to project.");
            ui.displayAllProjects();

            projId = getLongInput("Enter id of project", 0);
            while (!ui.projectExists(projId)) {
                projId = getLongInput("Enter valid id of project", 0);
            }
        }

        if (issueId < 0) issueId = null;
        ui.createEntry(entry, phaseId, emplId, issueId, projId);

        System.out.println("Entry " + entry.getId() + " created.");
    }

    private static void issueMenu() {
        printTitle("Issue menu");
        int option = promptOptions("Create new", "Update", "List issues", "Main menu");
        switch (option) {
            case (1):
                createIssue();
                break;
            case (2):
                issueUpdateMenu();
                break;
            case (3):
                displayIssues();
                break;
            default:
                mainMenu();
                return;
        }

        issueMenu();
    }

    private static void displayIssues() {
        ui.displayAllProjects();
        Long projId = getLongInput("What projects' issues do you want to display", 0);
        while (!ui.projectExists(projId)) {
            projId = getLongInput("Enter valid id of project", 0);
        }

        System.out.println("Filter by state?");
        IssueState state = null;
        int option = promptOptions("Yes", "No");
        if (option == 1) {
            state = getStateInput();
        }

        System.out.println("Group output?");
        int listType = promptOptions("Grouped by Employees", "No");

        switch (listType) {
            case (1):
                if (state != null) {
                    ui.displayIssuesByProjectAndStateGroupedByEmployee(projId, state);
                } else {
                    ui.displayIssuesByProjectGroupedByEmployee(projId);
                }
                break;
            case (2):
                if (state != null) {
                    ui.displayIssuesByProjectAndState(projId, state);
                } else {
                    ui.displayIssuesByProject(projId);
                }
                break;
        }
    }

    private static void issueUpdateMenu() {
        printTitle("Issue update menu");
        int option = promptOptions("Update priority", "Update state", "Assign project", "Assign employee", "Go back");
        switch (option) {
            case (1):
                setIssuePriority();
                break;
            case (2):
                setIssueState();
                break;
            case (3):
                addProjectIssue();
                break;
            case (4):
                addEmployeeIssue();
                break;
            default:
                issueMenu();
                return;
        }
        issueUpdateMenu();
    }

    private static void setIssueState() {
        ui.displayAllIssues();
        Long issueId = getLongInput("Enter id of issue to update", 0);
        while (!ui.issueExists(issueId)) {
            issueId = getLongInput("Enter valid id of issue to update", 0);
        }

        System.out.println("Select new issue state");
        IssueState state = getStateInput();
        ui.updateIssue(issueId, null, null, state, null);
    }

    private static void setIssuePriority() {
        ui.displayAllIssues();
        Long issueId = getLongInput("Enter id of issue to update", 0);
        while (!ui.issueExists(issueId)) {
            issueId = getLongInput("Enter valid id of issue to update", 0);
        }

        System.out.println("Select new issue priority");
        IssuePriority priority = null;
        int prioId = promptOptions(Arrays.stream(IssuePriority.values()).map(Enum::name).toArray(String[]::new));
        switch (prioId) {
            case (1):
                priority = IssuePriority.LOW;
                break;
            case (2):
                priority = IssuePriority.NORMAL;
                break;
            case (3):
                priority = IssuePriority.HIGH;
                break;
        }
        ui.updateIssue(issueId, null, null, null, priority);
    }

    private static void addEmployeeIssue() {
        ui.displayAllIssues();
        Long issueId = getLongInput("Enter id of issue", 0);
        while (!ui.issueExists(issueId)) {
            issueId = getLongInput("Enter valid id of issue", 0);
        }
        ui.displayAllEmployees();
        Long emplId = getLongInput("Enter id of employee to assign to that issue", 0);
        while (!ui.employeeExists(emplId)) {
            emplId = getLongInput("Enter valid id of employee", 0);
        }
        ui.updateIssue(issueId, emplId, null, null, null);
    }

    private static void addProjectIssue() {
        ui.displayAllIssues();
        Long issueId = getLongInput("Enter id of issue to assign", 0);
        while (!ui.issueExists(issueId)) {
            issueId = getLongInput("Enter valid id of issue to assign", 0);
        }
        ui.displayAllProjects();
        Long projId = getLongInput("Enter id of project to assign to", 0);
        while (!ui.projectExists(projId)) {
            projId = getLongInput("Enter valid id of project", 0);
        }
        ui.addProjectIssue(projId, issueId);
    }

    private static void createIssue() {
        System.out.println("New Issue:");
        Issue issue = new Issue();
        issue.setDescription(getStringInput("Description"));

        ui.displayAllProjects();
        Long projId = getLongInput("ID of project to assign this issue to", 0);
        while (!ui.projectExists(projId)) {
            projId = getLongInput("Enter a valid project id", 0);
        }

        Integer est = getLongInput("Estimated time in minutes (-1 to skip)", -10).intValue();
        issue.setEstimatedMinutes(est >= 0 ? est : null);
        Long emplId = null;
        if (issue.getEstimatedMinutes() != null) {
            ui.displayAllEmployees();
            emplId = getLongInput("ID of employee to assign this issue to (-1 to skip)", -10);
            while (emplId > 0 && !ui.employeeExists(emplId)) {
                emplId = getLongInput("ID of employee to assign this issue to (-1 to skip)", -10);
            }
            emplId = emplId >= 0 ? emplId : null;
        }
        issue.setPercentageDone(0);
        issue.setState(IssueState.NEW);
        System.out.println("What priority does this issue have (-1 to skip): ");
        int priority = promptOptions(Arrays.stream(IssuePriority.values()).map(Enum::name).toArray(String[]::new));
        switch (priority) {
            case (1):
                issue.setPriority(IssuePriority.LOW);
                break;
            case (2):
                issue.setPriority(IssuePriority.NORMAL);
                break;
            case (3):
                issue.setPriority(IssuePriority.HIGH);
                break;
        }
        ui.createIssue(issue, projId, emplId);
    }

    private static void projectMenu() {
        printTitle("Project menu");
        int option = promptOptions("Display", "Create new", "Add issue", "Add employee", "Main menu");
        switch (option) {
            case (1):
                projectDisplayMenu();
                break;
            case (2):
                createProject();
                break;
            case (3):
                addProjectEntry();
                break;
            case (4):
                addProjectMember();
                break;
            default:
                mainMenu();
                return;
        }
        projectMenu();
    }

    private static void createProject() {
        System.out.println("New project:");
        Project project = new Project();
        project.setName(getStringInput("Project name"));

        System.out.println("What employee should lead this project (-1 to skip)?");
        ui.displayAllEmployees();
        Long emplId = getLongInput("Enter id of employee", -10);
        while (emplId >= 0 && !ui.employeeExists(emplId)) {
            emplId = getLongInput("Enter valid id of employee (-1 to skip)", -10);
        }

        ui.createProject(project, emplId);

        System.out.println("Project " + project.getId() + " created.");
    }

    private static void employeeMenu() {
        printTitle("Employee menu");
        int option = promptOptions("Display all", "Display from project", "Assign to project", "Remove from project", "Main menu");
        switch (option) {
            case (1):
                ui.displayAllEmployees();
                break;
            case(2):
                displayEmployeesByProject();
                break;
            case (3):
                addProjectMember();
                break;
            case (4):
                removeProjectMember();
                break;
            default:
                mainMenu();
                return;
        }
        employeeMenu();
    }

    private static void projectDisplayMenu() {
        int option = promptOptions("Display projects", "Display times worked", "Display issues", "Back");
        switch(option) {
            case(1):
                ui.displayAllProjects();
                break;
            case(2):
                displayWorktimes();
                break;
            case(3):
                displayIssues();
                break;
            default:
                projectMenu();
                return;
        }
        projectDisplayMenu();
    }

    private static void displayWorktimes() {
        ui.displayAllProjects();
        Long projId = getLongInput("What projects' worktimes do you want to display?", 0);
        while (!ui.projectExists(projId)) {
            projId = getLongInput("Enter valid id of project", 0);
        }
        ui.displayWorktimesByProject(projId);
    }

    private static void displayEmployeesByProject() {
        ui.displayAllProjects();
        Long projId = getLongInput("Enter id of project", 0);
        while (!ui.projectExists(projId)) {
            projId = getLongInput("Enter valid id of project", 0);
        }
        ui.displayEmployeesByProject(projId);
    }

    private static void removeProjectMember() {
        ui.displayAllProjects();
        Long projId = getLongInput("Enter id of project", 0);
        while (!ui.projectExists(projId)) {
            projId = getLongInput("Enter valid id of project", 0);
        }
        ui.displayEmployeesByProject(projId);
        Long emplId = getLongInput("Enter id of employee to assign", 0);
        while (!ui.employeeExists(emplId)) {
            emplId = getLongInput("Enter valid id of employee to assign", 0);
        }
        ui.removeProjectMember(projId, emplId);
    }

    private static void addProjectMember() {
        ui.displayAllEmployees();
        Long emplId = getLongInput("Enter id of employee to assign", 0);
        while (!ui.employeeExists(emplId)) {
            emplId = getLongInput("Enter valid id of employee to assign", 0);
        }
        ui.displayAllProjects();
        Long projId = getLongInput("Enter id of project to assign to", 0);
        while (!ui.projectExists(projId)) {
            projId = getLongInput("Enter valid id of project", 0);
        }
        ui.addProjectMember(projId, emplId);
    }

    private static int promptOptions(String... options) {
        int nr = 1;
        for (String option : options) {
            System.out.print(nr + ": " + option + ". ");
            ++nr;
        }
        System.out.println();
        return getLongInput("Select option", 1, options.length).intValue();
    }

    private static Long getLongInput(String hint, int min) {
        Scanner reader = new Scanner(System.in);
        System.out.print(hint + ": ");
        Long n;
        try {
            n = reader.nextLong();
            while (n < min) {
                System.out.print("Enter the number of an option: ");
                n = reader.nextLong();
            }
        } catch (InputMismatchException e) {
            return getLongInput(hint, min);
        }
        return n;
    }

    private static Long getLongInput(String hint, int min, int max) {
        Scanner reader = new Scanner(System.in);
        System.out.print(hint + ": ");
        Long n;
        try {
            n = reader.nextLong();
            while (n < min || n > max) {
                System.out.print("Enter the number of an option: ");
                n = reader.nextLong();
            }
        } catch (InputMismatchException e) {
            return getLongInput(hint, min, max);
        }
        return n;
    }

    private static IssueState getStateInput() {
        System.out.println("Select state");
        IssueState state = null;
        int stateId = promptOptions(Arrays.stream(IssueState.values()).map(Enum::name).toArray(String[]::new));
        switch (stateId) {
            case (1):
                return IssueState.NEW;
            case (2):
                return IssueState.OPEN;
            case (3):
                return IssueState.RESOLVED;
            case (4):
                return IssueState.CLOSED;
            case (5):
                return IssueState.REJECTED;
        }
        return null;
    }

    private static String getStringInput(String hint) {
        Scanner reader = new Scanner(System.in);
        System.out.print(hint + ": ");
        String in = reader.next();
        return in.equals("") ? null : in;
    }
}
