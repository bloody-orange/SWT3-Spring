package swt6.spring.bl;

import swt6.spring.domain.Employee;
import swt6.spring.domain.Project;
import swt6.util.Tuple;

import java.util.Map;

public interface ProjectManager extends BaseManager<Project, Long>  {
    Map<Employee, Tuple<Long, Long>> getTimetable(Long projId);
}
