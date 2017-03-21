package swt6.spring.dao;

import swt6.orm.domain.Employee;

import java.util.List;

public interface EmployeeDao extends BaseDao<Employee, Long> {
    List<Employee> findByName(String name);
    List<Employee> findByProject(Long projectId);
}
