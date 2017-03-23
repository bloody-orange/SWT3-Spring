package swt6.spring.bl;

import swt6.spring.domain.Employee;
import swt6.spring.domain.Project;

import java.util.List;

public interface EmployeeManager extends BaseManager<Employee, Long> {
    List<Employee> findAllByProject(Long projId);
}
