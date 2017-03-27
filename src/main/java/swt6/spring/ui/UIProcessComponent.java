package swt6.spring.ui;


import swt6.spring.bl.*;
import swt6.spring.domain.*;
import swt6.util.PrintUtil;
import swt6.util.Tuple;
import static swt6.util.PrintUtil.*;

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
    public void addEmployeeIssue(Long emplId, Long issueId) {

        Employee empl = employeeManager.findById(emplId);
        if (empl == null) {
            System.out.println("Error: Employee not found.");
            return;
        }
        Issue issue = issueManager.findById(issueId);
        if (issue == null) {
            System.out.println("Error: Issue not found.");
            return;
        }
        empl.addIssue(issue);
        employeeManager.save(empl);
        issueManager.save(issue);
        System.out.println(empl.getFirstName() + " assigned to issue #" + issue.getId() + ".");
    }

    @Override
    public void createProject(Project project, Long emplId) {
        Employee empl = null;
        if (emplId != null) {
            empl = employeeManager.findById(emplId);
            if (empl == null) {
                System.out.println("Error: Employee not found.");
                return;
            }
        }

        project.setLeader(empl);
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
        PrintUtil.printTitle("<< Project '" + project.getName() + "' >>");
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
        Project proj = projectManager.findById(projId);
        if (proj == null) {
            System.out.println("Error: Project not found.");
            return;
        }
        Employee empl = employeeManager.findById(emplId);
        if (empl == null) {
            System.out.println("Error: Employee not found.");
            return;
        }
        proj.addMember(empl);
        projectManager.save(proj);
        employeeManager.save(empl);
        System.out.println(empl.getFirstName() + " added to project" + proj.getName() + ".");
    }

    @Override
    public void addProjectIssue(Long projId, Long issueId) {
        Project proj = projectManager.findById(projId);
        if (proj == null) {
            System.out.println("Error: Project not found.");
            return;
        }
        Issue issue = issueManager.findById(issueId);
        if (issue == null) {
            System.out.println("Error: Issue not found.");
            return;
        }
        proj.addIssue(issue);
        projectManager.save(proj);
        issueManager.save(issue);
        System.out.println("Issue #" + issue.getId() + " removed from project" + proj.getName() + ".");
    }

    @Override
    public void removeProjectMember(Long projId, Long emplId) {
        Project proj = projectManager.findById(projId);
        if (proj == null) {
            System.out.println("Error: Project not found.");
            return;
        }
        Employee empl = employeeManager.findById(emplId);
        if (empl == null) {
            System.out.println("Error: Employee not found.");
            return;
        }
        proj.removeMember(empl);
        System.out.println(empl.getFirstName() + " removed from project" + proj.getName() + ".");
    }

    @Override
    public void addProjectEntry(Long projId, Long entryId) {
        Project proj = projectManager.findById(projId);
        if (proj == null) {
            System.out.println("Error: Project not found.");
            return;
        }
        LogbookEntry entry = entryManager.findById(entryId);
        if (entry == null) {
            System.out.println("Error: LogbookEntry not found.");
            return;
        }
        proj.addEntry(entry);
        projectManager.save(proj);
        entryManager.save(entry);
        System.out.println("Entry #" + entry.getId() + " added to project" + proj.getName() + ".");
    }

    @Override
    public void removeProjectEntry(Long projId, Long entryId) {
        Project proj = projectManager.findById(projId);
        if (proj == null) {
            System.out.println("Error: Project not found.");
            return;
        }
        LogbookEntry entry = entryManager.findById(entryId);
        if (entry == null) {
            System.out.println("Error: LogbookEntry not found.");
            return;
        }
        proj.removeEntry(entry);
        System.out.println("Entry #" + entry.getId() + " removed from project" + proj.getName() + ".");
    }

    @Override
    public void createIssue(Issue issue, Long projId, Long emplId) {
        Project proj = projectManager.findById(projId);
        if (proj == null) {
            System.out.println("Error: Project not found.");
            return;
        }
        issue.setProject(proj);

        if (emplId != null) {
            Employee empl = employeeManager.findById(emplId);
            if (empl == null) {
                System.out.println("Error: Employee not found.");
                return;
            }
            issue.setAssignee(empl);
        }
        issueManager.save(issue);
        System.out.println("Issue #" + issue.getId() + " created.");
    }

    @Override
    public void displayAllIssues() {
        for (Issue issue: issueManager.findAll()) {
            System.out.println(issue);
        }
    }

    @Override
    public void displayIssuesByProject(Long projId) {
        Project proj = projectManager.findById(projId);
        if (proj == null) {
            System.out.println("Error: Project not found.");
            return;
        }
        System.out.println("Issues for project '" + proj.getName() + "':");
        for (Issue issue: issueManager.findByProject(proj)) {
            System.out.println(issue);
        }
    }

    @Override
    public void displayIssuesByProjectAndState(Long projId, IssueState state) {
        Project proj = projectManager.findById(projId);
        if (proj == null) {
            System.out.println("Error: Project not found.");
            return;
        }
        System.out.println(state + " issues for project '" + proj.getName() + "':");
        for (Issue issue: issueManager.findByProjectAndState(proj, state)) {
            System.out.println(issue);
        }
    }

    @Override
    public void displayIssuesByProjectGroupedByEmployee(Long projId) {
        Project proj = projectManager.findById(projId);
        if (proj == null) {
            System.out.println("Error: Project not found.");
            return;
        }
        System.out.println("Issues for project '" + proj.getName() + "':");
        displayGroupedIssues(issueManager.findByProject(proj));
    }

    @Override
    public void displayIssuesByProjectAndStateGroupedByEmployee(Long projId, IssueState state) {
        Project proj = projectManager.findById(projId);
        if (proj == null) {
            System.out.println("Error: Project not found.");
            return;
        }
        if (state == null) {
            System.out.println("Error: State was null.");
            return;
        }
        System.out.println(state + " issues for project '" + proj.getName() + "':");
        displayGroupedIssues(issueManager.findByProjectAndState(proj, state));
    }

    private void displayGroupedIssues(List<Issue> issues) {
        Map<Employee, List<Issue>> assigneeMap = new HashMap<>();
        for (Issue issue: issues) {
            if (assigneeMap.containsKey(issue.getAssignee())) {
                assigneeMap.get(issue.getAssignee()).add(issue);
            } else {
                List<Issue> issueList = new ArrayList<>();
                issueList.add(issue);
                assigneeMap.put(issue.getAssignee(), issueList);
            }
        }

        for (Map.Entry<Employee, List<Issue>> entry: assigneeMap.entrySet()) {
            if (entry.getKey() != null) {
                System.out.println("Issues for employee " + entry.getKey().getFirstName()
                        + " " + entry.getKey().getLastName() + ":");
            } else {
                System.out.println("Issues without assignee:");
            }
            for (Issue issue: entry.getValue()) {
                System.out.println(issue);
            }
        }
    }

    @Override
    public void updateIssue(Long issueId, Long emplId, String description, IssueState state, IssuePriority prio) {
        Issue issue = issueManager.findById(issueId);
        if (issue == null) {
            System.out.println("Error: Issue not found.");
            return;
        }

        if (emplId != null) {
            Employee empl = employeeManager.findById(emplId);
            if (empl == null) {
                System.out.println("Error: Employee not found.");
                return;
            }
            if (issue.getAssignee() != null) {
                if (entryManager.findAllByEmployeeAndIssue(issue.getAssignee(), issue).size() != 0) {
                    System.out.println("Error: Old assignee has already worked on this issue.");
                    return;
                }
            }
            issue.setAssignee(empl);
        }

        if (description != null) {
            issue.setDescription(description);
        }

        if (state != null) {
            issue.setState(state);
        }

        if (prio != null) {
            issue.setPriority(prio);
        }

        issueManager.save(issue);
    }


    @Override
    public void displayAllPhases() {
        for (Phase phase: phaseManager.findAll()) {
            System.out.println(phase);
        }
    }

    @Override
    public void createEntry(LogbookEntry entry, Long phaseId, Long emplId, Long issueId, Long projId) {
        Employee empl = employeeManager.findById(emplId);
        if (empl == null) {
            System.out.println("Error: Employee not found");
            return;
        }
        empl.addLogbookEntry(entry);
        employeeManager.save(empl);

        Phase phase = phaseManager.findById(phaseId);
        if (phase == null) {
            System.out.println("Error: Phase not found");
            return;
        }
        phase.addLogbookEntry(entry);
        phaseManager.save(phase);

        if (issueId != null) {
            Issue issue = issueManager.findById(issueId);
            if (issue == null) {
                System.out.println("Error: Issue not found");
                return;
            }
            issue.addLogbookEntry(entry);
            issueManager.save(issue);
        }
        else if (projId == null) {
            System.out.println("Error: No issue and no project");
            return;
        }
        if (projId != null) {
            Project project = projectManager.findById(projId);
            if (project == null) {
                System.out.println("Error: Project not found");
                return;
            }
            project.addEntry(entry);
            projectManager.save(project);
        }
        entryManager.save(entry);
    }

    @Override
    public void assignEntryToIssue(Long entryId, Long issueId) {
        LogbookEntry entry = entryManager.findById(entryId);
        if (entry == null) {
            System.out.println("Error: Entry not found");
            return;
        }
        Issue issue = issueManager.findById(issueId);
        if (issue == null) {
            System.out.println("Error: Issue not found");
            return;
        }

        issue.addLogbookEntry(entry);
        entryManager.save(entry);
        issueManager.save(issue);
    }

    @Override
    public void displayAllEntries() {
        for (LogbookEntry entry: entryManager.findAll()) {
            System.out.println(entry);
        }
    }

    @Override
    public boolean employeeExists(Long emplId) {
        return employeeManager.findById(emplId) != null;
    }

    @Override
    public boolean projectExists(Long projId) {
        return projectManager.findById(projId) != null;
    }

    @Override
    public boolean issueExists(Long issueId) {
        return issueManager.findById(issueId) != null;
    }

    @Override
    public boolean issueIsAssignedToProject(Long issueId) {
        Issue issue = issueManager.findById(issueId);
        return issue != null && issue.getProject() != null;
    }

    @Override
    public boolean entryExists(Long entryId) {
        return entryManager.findById(entryId) != null;
    }

    @Override
    public boolean phaseExists(Long phaseId) {
        return phaseManager.findById(phaseId) != null;
    }
}
