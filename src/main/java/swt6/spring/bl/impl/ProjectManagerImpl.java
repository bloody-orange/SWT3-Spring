package swt6.spring.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.spring.bl.ProjectManager;
import swt6.spring.dal.EmployeeRepository;
import swt6.spring.dal.ProjectRepository;
import swt6.spring.domain.Employee;
import swt6.spring.domain.Project;
import swt6.util.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProjectManagerImpl extends AbstractBaseManager<Project, Long, ProjectRepository> implements ProjectManager{
    @Autowired
    public ProjectManagerImpl(ProjectRepository repo) {
        super(Project.class, repo);
    }

    @Override
    public Map<Employee, Tuple<Long, Long>> getTimetable(Long projId) {

        Project project = repo.findOne(projId);
        Map<Employee, Tuple<Long, Long>> timeTable = new HashMap<>();
        project.getMembers().forEach(member -> timeTable.put(member, new Tuple<>(0L, 0L)));
        project.getIssues().forEach(issue -> {
            Integer minutes = issue.getEstimatedMinutes() == null ? 0 : issue.getEstimatedMinutes();
            if (issue.getAssignee() == null) return;
            if (timeTable.containsKey(issue.getAssignee())) {
                timeTable.get(issue.getAssignee()).second += minutes;
            } else {
                timeTable.put(issue.getAssignee(), new Tuple<>(0L, minutes.longValue()));
            }
            issue.getEntries().forEach(entry -> {
                if (timeTable.containsKey(entry.getEmployee())) {
                    timeTable.get(entry.getEmployee()).first += entry.getSpentMinutes();
                } else {
                    timeTable.put(entry.getEmployee(), new Tuple<>(entry.getSpentMinutes(), 0L));
                }
            });
        });
        return timeTable;
    }
}
